package com.dexesttp.hkxpack.tagxml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class TagXMLHandler {
	Document createDOM(String verName, int version) throws IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("hkxpackfile");
			root.setAttribute("classversion", ""+version);
			root.setAttribute("contentsversion", verName);
			doc.appendChild(root);
			return doc;
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		}
	}

	Element createSection(Document document, String string) {
		Element root = document.createElement("hksection");
		root.setAttribute("name", "__data__");
		document.getChildNodes().item(0).appendChild(root);
		return root;
	}

	void writeToFile(Document document, File outFile) throws TransformerException {
		// Create transformer
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		// Apply options to transformer
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING, "ascii");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		
		// Retrieve DOM
		DOMSource source = new DOMSource(document);
		StreamResult outResult;
		
		// Write DOM back to file
		outResult = new StreamResult(outFile);
		transformer.transform(source, outResult);
	}
}
