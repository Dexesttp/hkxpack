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
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;
import com.dexesttp.hkxpack.xml.classxml.definition.EnumObj;
import com.dexesttp.hkxpack.xml.classxml.definition.ImportedClass;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ImportedMember;

public class ClassXMLReader {
	public static ImportedClass getClassFromFile(String classname, int classID) throws IOException {
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
		// Get Class List to store enums in.
		ClassXMLList classList = ClassXMLList.getInstance();
		// Java DOM parsing is kind of ugly.
		NodeList enums = document.getElementsByTagName("enum");
		for(int i = 0; i < enums.getLength(); i++) {
			Node enumNode = enums.item(i);
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
			classList.addEnum(classname, enumObj.getName(), enumObj);
		}
		ImportedClass classObj = new ImportedClass(classname, classID);
		// Handle parent members.
		Node classNode = document.getFirstChild();
		String parentName = DOMUtils.getNodeAttr("parent", classNode);
		if(parentName != "") {
			ClassXML parent = classList.get(parentName);
			if(parent == null) {
				ImportedClass impParent = getClassFromFile(parentName, 0);
				classList.addClass(impParent);
				parent = impParent;
			}
			for(ClassXMLMember parentMember : parent.getMembers())
				classObj.addContent(parentMember);
		}
		// Handle direct members.
		NodeList members = document.getElementsByTagName("member");
		for(int i = 0; i < members.getLength(); i++) {
			Node memberNode = members.item(i);
			ClassXMLMember memberObj = new ImportedMember(
					DOMUtils.getNodeAttr("name", memberNode),
					classname,
					DOMUtils.getNodeAttr("vtype", memberNode),
					DOMUtils.getNodeAttr("vsubtype", memberNode),
					DOMUtils.getNodeAttr("ctype", memberNode),
					DOMUtils.getNodeAttr("etype", memberNode),
					DOMUtils.getNodeAttr("flags", memberNode));
			classObj.addContent(memberObj);
		}
		return classObj;
	}
}
