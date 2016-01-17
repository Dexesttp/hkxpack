package com.dexesttp.hkxpack.xml.tagxml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TagXMLInitializer {
	public Document initialize(int version, String verName) throws IOException {
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
}
