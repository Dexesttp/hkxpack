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
		Map<String, Integer> enumContents = new HashMap<String, Integer>();
		// TODO handle enum reading.
		enumResolver.add("", HashBiMap.create(enumContents));
		
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
		String arrsize = DOMUtils.getNodeAttr("arrsize", memberNode);
		String flags = DOMUtils.getNodeAttr("flags", memberNode);
		HKXMemberTemplate template = new HKXMemberTemplate(name, offset, vtype, vsubtype, ctype, etype, arrsize, flags);
		return template;
	}
}
