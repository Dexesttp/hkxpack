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

/**
 * Handles the TagXML file parsing (xml-wise) and retrieval of soem base elements.
 * <p>
 * {@link TagXMLFileHandler#getDocument()} retrieves the DOM document of the TagXML file.
 * {@link TagXMLFileHandler#getRootNode(Document)} retrieves the TagXML's hkxpackfile node.
 * {@link TagXMLFileHandler#getHKXFile(Node)} returns an empty HKXFile based on the TagXML version.
 * {@link TagXMLFileHandler#getSectionNode(Document, String)} returns the asked section node.
 */
class TagXMLFileHandler {
	private File tagFile;

	TagXMLFileHandler(File tagFile) {
		this.tagFile = tagFile;
	}

	/**
	 * Returns the {@link Document} from this TagXML file.
	 * @return a DOM {@link Document}.
	 * @throws ParserConfigurationException should never happen.
	 * @throws SAXException If there was a problem parsing the File.
	 * @throws IOException if there was a problem accessing the File.
	 */
	Document getDocument() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document result = builder.parse(tagFile);
		return result;
	}
	
	/**
	 * Retrieves the root node of the TagXML file.
	 * @param document the {@link Document} to retrieve the node from.
	 * @return the relevant {@link Node}.
	 * @throws InvalidTagXMLException if there is no root TagXML node in the file.
	 */
	Node getRootNode(Document document) throws InvalidTagXMLException {
		NodeList nodeList = document.getElementsByTagName("hkpackfile");
		if(nodeList.getLength() != 1)
			throw new InvalidTagXMLException(SBundle.getString("error.tag.read.hkpackfile") + nodeList.getLength());
		return nodeList.item(0);
	}

	/**
	 * Retrieves the HKXFile based on a given Root node.
	 * @param root the Root node, may be obtained with {@link TagXMLFileHandler#getRootNode(Document)}.
	 * @return the empty {@link HKXFile}, properly initialized.
	 */
	HKXFile getHKXFile(Node root) {
		int classVersion = Integer.parseInt(DOMUtils.getNodeAttr("classversion", root));
		String contentsVersion = DOMUtils.getNodeAttr("contentsversion", root);
		HKXFile result = new HKXFile(contentsVersion, classVersion);
		return result;
	}

	/**
	 * Retrieves a given Section node.
	 * @param document the {@link Document} to retrieve the node from.
	 * @param name the name of the Section node to retrieve.
	 * @return the section {@link Node}, or null if no section node could be found.
	 */
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
