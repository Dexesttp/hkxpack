package com.dexesttp.hkxpack.xml.classxml;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.resources.DOMUtils;
import com.dexesttp.hkxpack.resources.FileUtils;
import com.dexesttp.hkxpack.xml.classxml.definition.EnumObj;
import com.dexesttp.hkxpack.xml.classxml.definition.ImportedClass;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ImportedMember;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class ClassXMLReader {
	public static ImportedClass getClassFromFile(String classname, int classID) throws IOException {
		DOMParser parser = new DOMParser();
		try {
			parser.parse(FileUtils.getFileName(classname));
		} catch (SAXException e) {
			e.printStackTrace();
			throw new IOException("Couldn't parse file for " + classname + " : invalid DOM.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Error reading file for " + classname + " : " + e.getMessage());
		}
		// Java DOM parsing is kind of ugly.
		Document document = parser.getDocument();
		// TODO handle enums.
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
		}
		ImportedClass classObj = new ImportedClass(classname, classID);
		NodeList members = document.getElementsByTagName("member");
		for(int i = 0; i < members.getLength(); i++) {
			Node memberNode = members.item(i);
			ClassXMLMember memberObj = new ImportedMember(
					DOMUtils.getNodeAttr("name", memberNode),
					DOMUtils.getNodeAttr("vtype", memberNode),
					DOMUtils.getNodeAttr("vsubtype", memberNode),
					DOMUtils.getNodeAttr("ctype", memberNode),
					DOMUtils.getNodeAttr("etype", memberNode));
			classObj.addContent(memberObj);
		}
		return classObj;
	}
}
