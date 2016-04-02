package com.dexesttp.hkxpack.descriptor.reader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkx.types.ObjectSizeResolver;
import com.dexesttp.hkxpack.resources.DOMUtils;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
/**
 * Reads ClassXML and produces HKXDescriptorTemplates from it.
 */
public class ClassXMLReader {
	private final transient ClassXMLList classList;
	private final transient HKXDescriptorFactory descriptorFactory;
	private final transient HKXEnumResolver enumResolver;

	ClassXMLReader(final HKXDescriptorFactory descriptorFactory, final ClassXMLList classList, final HKXEnumResolver enumResolver) throws ClassListReadException {
		this.descriptorFactory  = descriptorFactory;
		this.classList = classList;
		this.enumResolver = enumResolver;
	}
	
	/**
	 * Retrieves a HKXDescriptor from the classXML files.
	 * @param classname
	 * @return
	 * @throws ClassFileReadException
	 */
	public HKXDescriptor get(final String classname) throws ClassFileReadException {
		// Retrieve the document.
		Document document = openFile(classname);
		
		// Read class
		Node classNode = document.getFirstChild();
		List<HKXMemberTemplate> memberList = new ArrayList<>();
		
		// Read signature
		String signatureString = DOMUtils.getNodeAttr("signature", classNode);
		long signature = Long.parseLong(signatureString.substring(2), 16);
		
		// Read enums
		NodeList enums = document.getElementsByTagName("enums");
		if(enums.getLength() > 0) {
			retrieveEnums(classname, enums);
		}
		
		// Handle eventual parent
		String parentName = DOMUtils.getNodeAttr("parent", classNode);
		if(!parentName.isEmpty()) {
			HKXDescriptor parent = descriptorFactory.get(parentName);
			memberList.addAll(parent.getMemberTemplates());
		}
		// Handle direct members.
		NodeList members = document.getElementsByTagName("member");
		for(int i = 0; i < members.getLength(); i++) {
			Node memberNode = members.item(i);
			memberList.addAll(resolveMember(memberNode, classname));
		}
		
		// Return the descriptor
		return new HKXDescriptor(classname, signature, memberList);
	}

	/**
	 * Retrieve all the enumerations described as {@link Node} in the given {@link NodeList}, and store them in this {@link ClassXMLReader}'s {@link HKXEnumResolver}.
	 * @param classname the classname currently read.
	 * @param enums the enumeration node list.
	 */
	private void retrieveEnums(final String classname, final NodeList enums) {
		StringBuffer enumNameBuffer = new StringBuffer();
		NodeList enumsObjects = enums.item(0).getChildNodes();
		for(int i = 0; i < enumsObjects.getLength(); i++) {
			Node enumObject = enumsObjects.item(i);
			if(enumObject.getAttributes() != null) {
				Map<Integer, String> enumContents = new HashMap<>();
				enumNameBuffer.setLength(0);
				String enumName = enumNameBuffer
						.append(classname)
						.append(".")
						.append(DOMUtils.getNodeAttr("name", enumObject))
						.toString();
				for(int j = 0; j < enumObject.getChildNodes().getLength(); j++) {
					Node enumObjectContent = enumObject.getChildNodes().item(j);
					if(enumObjectContent.getAttributes() != null) {
						String enumObjectName = DOMUtils.getNodeAttr("name", enumObjectContent);
						int enumObjectContents = Integer.parseInt(DOMUtils.getNodeAttr("value", enumObjectContent));
						enumContents.put(enumObjectContents, enumObjectName);
					}
				}
				BiMap<Integer, String> test = HashBiMap.create(enumContents);
				enumResolver.add(enumName, test.inverse());
			}
		}
	}

	private Document openFile(final String classname) throws ClassFileReadException {
		Document document;
		try {
			String classUri = classList.getFileName(classname);
			if(classUri == null) {
				return null;
			}
			URL source = ClassXMLReader.class.getResource(classUri);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.parse(source.openStream());
		} catch (SAXException | ParserConfigurationException e) {
			throw new ClassFileReadException("Couldn't parse file for " + classname + " : invalid DOM.", e);
		} catch (IOException e) {
			throw new ClassFileReadException("Error reading file for " + classname + ".", e);
		}
		return document;
	}

	private List<HKXMemberTemplate> resolveMember(final Node memberNode, final String classname) throws ClassFileReadException {
		String name = DOMUtils.getNodeAttr("name", memberNode);
		String offset = DOMUtils.getNodeAttr("offset", memberNode);
		String vtype = DOMUtils.getNodeAttr("vtype", memberNode);
		String vsubtype = DOMUtils.getNodeAttr("vsubtype", memberNode);
		String ctype = DOMUtils.getNodeAttr("ctype", memberNode);
		StringBuilder etypeBuilder = new StringBuilder(DOMUtils.getNodeAttr("etype", memberNode));
		if(etypeBuilder.length() > 0) {
			etypeBuilder.insert(0, classname + ".");
		}
		String etype = etypeBuilder.toString();
		String arrsize = DOMUtils.getNodeAttr("arrsize", memberNode);
		String flags = DOMUtils.getNodeAttr("flags", memberNode);
		List<HKXMemberTemplate> res = new ArrayList<HKXMemberTemplate>();
		if(arrsize.equals("0")) {
			HKXMemberTemplate template = new HKXMemberTemplate(name, offset, vtype, vsubtype, ctype, etype, arrsize, flags);
			res.add(template);
		} else {
			int size = Integer.parseInt(arrsize);
			long memberSize = 0;
			if(vtype.equals("TYPE_STRUCT")) {
				memberSize = ObjectSizeResolver.getSize(descriptorFactory.get(ctype), descriptorFactory);
			} else {
				memberSize = MemberSizeResolver.getSize(HKXType.valueOf(vtype));
			}
			long memberOffset = Integer.parseInt(offset);
			for(int i = 0; i < size; i++) {
				res.add(
						new HKXMemberTemplate(
								name + (i+1),
								Long.toString(memberOffset + i * memberSize),
								vtype, vsubtype, ctype, etype, arrsize, flags)
						);
			}
		}
		return res;
	}
}
