package com.dexesttp.hkxpack.descriptor.reader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadError;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.resources.DOMUtils;
import com.google.common.collect.HashBiMap;
/**
 * Reads ClassXML and produces HKXDescriptorTemplates from it.
 */
public class ClassXMLReader {
	private final ClassXMLList classList;
	private final HKXDescriptorFactory descriptorFactory;
	private final HKXEnumResolver enumResolver;

	ClassXMLReader(HKXDescriptorFactory descriptorFactory, ClassXMLList classList, HKXEnumResolver enumResolver) throws ClassListReadError {
		this.descriptorFactory  = descriptorFactory;
		this.classList = classList;
		this.enumResolver = enumResolver;
	}
	
	/**
	 * Retrieves a HKXDescriptor from the classXML files.
	 * @param classname
	 * @return
	 * @throws ClassFileReadError
	 */
	public HKXDescriptor get(String classname) throws ClassFileReadError {
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
			memberList.add(resolveMember(memberNode, classname));
		}
		
		// Return the descriptor
		return new HKXDescriptor(classname, signature, memberList);
	}

	private void retrieveEnums(String classname, NodeList enums) {
		// TODO revisit this.
		NodeList enumsObjects = enums.item(0).getChildNodes();
		for(int i = 0; i < enumsObjects.getLength(); i++) {
			Node enumObject = enumsObjects.item(i);
			if(enumObject.getAttributes() != null) {
				Map<Integer, String> enumContents = new HashMap<>();
				String enumName = DOMUtils.getNodeAttr("name", enumObject);
				enumName = classname + "." + enumName;
				for(int j = 0; j < enumObject.getChildNodes().getLength(); j++) {
					Node enumObjectContent = enumObject.getChildNodes().item(j);
					if(enumObjectContent.getAttributes() != null) {
						String enumObjectName = DOMUtils.getNodeAttr("name", enumObjectContent);
						int enumObjectContents = Integer.parseInt(DOMUtils.getNodeAttr("value", enumObjectContent));
						enumContents.put(enumObjectContents, enumObjectName);
					}
				}
				enumResolver.add(enumName, HashBiMap.create(enumContents).inverse());
			}
		}
	}

	private Document openFile(String classname) throws ClassFileReadError {
		Document document;
		try {
			String classUri = classList.getFileName(classname);
			if(classUri == null)
				return null;
			URL source = ClassXMLReader.class.getResource(classUri);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.parse(source.openStream());
		} catch (SAXException e) {
			throw new ClassFileReadError("Couldn't parse file for " + classname + " : invalid DOM.", e);
		} catch (Exception e) {
			throw new ClassFileReadError("Error reading file for " + classname + ".", e);
		}
		return document;
	}

	private HKXMemberTemplate resolveMember(Node memberNode, String classname) {
		String name = DOMUtils.getNodeAttr("name", memberNode);
		String offset = DOMUtils.getNodeAttr("offset", memberNode);
		String vtype = DOMUtils.getNodeAttr("vtype", memberNode);
		String vsubtype = DOMUtils.getNodeAttr("vsubtype", memberNode);
		String ctype = DOMUtils.getNodeAttr("ctype", memberNode);
		String etype = DOMUtils.getNodeAttr("etype", memberNode);
		if(!etype.isEmpty())
			etype = classname + "." + etype;
		String arrsize = DOMUtils.getNodeAttr("arrsize", memberNode);
		String flags = DOMUtils.getNodeAttr("flags", memberNode);
		HKXMemberTemplate template = new HKXMemberTemplate(name, offset, vtype, vsubtype, ctype, etype, arrsize, flags);
		return template;
	}
}
