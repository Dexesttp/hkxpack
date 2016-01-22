package com.dexesttp.hkxpack.xml.classxml;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.resources.ClassFilesUtils;
import com.dexesttp.hkxpack.resources.DOMUtils;
import com.dexesttp.hkxpack.xml.classxml.definition.classes.ImportedClass;
import com.dexesttp.hkxpack.xml.classxml.definition.enumeration.EnumObj;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ImportedMember;

public class ClassXMLReader {
	public static void getClassFromFile(String classname, int classID) throws IOException {
		// Open the file containing all data about the class.
		Document document = openFile(classname);
		
		// Read all enums
		ClassXMLEnums enumList = ClassXMLEnums.getInstance();
		NodeList enums = document.getElementsByTagName("enum");
		for(int i = 0; i < enums.getLength(); i++) {
			Node enumNode = enums.item(i);
			EnumObj enumInst = readEnum(enumNode);
			enumList.addEnum(enumInst.getName(), enumInst);
		}
		
		// Read class
		ImportedClass classObj = new ImportedClass(classname, classID);
		
		// Handle eventual parent
		Node classNode = document.getFirstChild();
		String parentName = DOMUtils.getNodeAttr("parent", classNode);
		if(parentName != "") {
			getClassFromFile(parentName, 0);
			classObj.setParent(parentName);
		}
		
		// Handle direct members.
		NodeList members = document.getElementsByTagName("member");
		for(int i = 0; i < members.getLength(); i++) {
			Node memberNode = members.item(i);
			classObj.addContent(resolveMember(memberNode, classname));
		}
		ClassXMLList.getInstance().addClass(classname, classObj);
	}

	private static Document openFile(String classname) throws IOException {
		Document document;
		try {
			String classUri = ClassFilesUtils.getFileName(classname);
			URL source = ClassXMLReader.class.getResource(classUri);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.parse(source.openStream());
		} catch (SAXException e) {
			e.printStackTrace();
			throw new IOException("Couldn't parse file for " + classname + " : invalid DOM.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Error reading file for " + classname + " : " + e.getMessage());
		}
		return document;
	}

	private static ImportedMember resolveMember(Node memberNode, String className) {
		ImportedMember memberObj = new ImportedMember(
			DOMUtils.getNodeAttr("name", memberNode),
			className,
			DOMUtils.getNodeAttr("offset", memberNode),
			DOMUtils.getNodeAttr("flags", memberNode),
			DOMUtils.getNodeAttr("vtype", memberNode),
			DOMUtils.getNodeAttr("vsubtype", memberNode),
			DOMUtils.getNodeAttr("ctype", memberNode),
			DOMUtils.getNodeAttr("etype", memberNode));
		return memberObj;
	}

	private static EnumObj readEnum(Node enumNode) {
		EnumObj enumObj = new EnumObj(
				DOMUtils.getNodeAttr("name", enumNode),
				DOMUtils.getNodeAttr("flags", enumNode));
		NodeList entries = enumNode.getChildNodes();
		for(int j = 0; j < entries.getLength(); j++) {
			Node entry = entries.item(j);
			if(entry.getNodeType() == Node.ELEMENT_NODE)
				enumObj.addEntry(
						DOMUtils.getNodeAttr("name", entry),
						Integer.parseInt(DOMUtils.getNodeAttr("value", entry)));
		}
		return enumObj;
	}
}
