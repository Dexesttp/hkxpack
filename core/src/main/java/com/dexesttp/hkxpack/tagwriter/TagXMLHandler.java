package com.dexesttp.hkxpack.tagwriter;

import java.io.File;

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

/**
 * Handles a {@link File} intended to serve as the XML destination.
 */
class TagXMLHandler {
	/**
	 * Creates a {@link Document} containing the HKX's version and version name.
	 * @param verName
	 * @param version
	 * @return
	 * @throws ParserConfigurationException if there was an error while handling the Document creation.
	 */
	Document createDOM(String verName, int version) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element root = doc.createElement("hkxpackfile");
		root.setAttribute("classversion", ""+version);
		root.setAttribute("contentsversion", verName);
		doc.appendChild(root);
		return doc;
	}

	/**
	 * Create a hksection in a {@link Document}, and returns it as an {@link Element}.
	 * @param document the {@link Document} to create the section into.
	 * @param name the name of the section.
	 * @return the hksection's {@link Element}
	 */
	Element createSection(Document document, String name) {
		Element section = document.createElement("hksection");
		section.setAttribute("name", name);
		document.getChildNodes().item(0).appendChild(section);
		return section;
	}

	/**
	 * Write a {@link Document} to a {@link File}.
	 * @param document the {@link Document} to write.
	 * @param outFile the {@link File} to write the {@link Document} into.
	 * @throws TransformerException if there was an error while writing out the document.
	 */
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
