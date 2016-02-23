package com.dexesttp.hkxpack.tagreader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.resources.DOMUtils;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

class TagXMLFileHandler {
	private File tagFile;

	TagXMLFileHandler(File tagFile) {
		this.tagFile = tagFile;
	}

	Document getDocument() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document result = builder.parse(tagFile);
		return result;
	}
	
	Node getRootNode(Document document) throws InvalidTagXMLException {
		NodeList nodeList = document.getElementsByTagName("hkxpackfile");
		if(nodeList.getLength() != 1)
			throw new InvalidTagXMLException(SBundle.getString("error.tag.read.hkxpackfile") + nodeList.getLength());
		return nodeList.item(0);
	}

	HKXFile getHKXFile(Node root) {
		int classVersion = Integer.parseInt(DOMUtils.getNodeAttr("classversion", root));
		String contentsVersion = DOMUtils.getNodeAttr("contentsversion", root);
		HKXFile result = new HKXFile(contentsVersion, classVersion);
		return result;
	}

	Node getSectionNode(Document document, String name) {
		NodeList sectionList = document.getElementsByTagName("hksection");
		for(int i = 0; i < sectionList.getLength(); i++) {
			Node section = sectionList.item(i);
			if(DOMUtils.getNodeAttr("name", section).equals(name))
				return section;
		}
		return null;
	}
}
