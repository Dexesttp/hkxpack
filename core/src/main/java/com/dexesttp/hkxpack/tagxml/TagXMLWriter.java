package com.dexesttp.hkxpack.tagxml;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;

public class TagXMLWriter {

	private final File outFile;

	public TagXMLWriter(File outputFile) {
		this.outFile = outputFile;
	}

	public void write(HKXFile hkxFile) throws IOException, TransformerException {
		TagXMLHandler handler = new TagXMLHandler();
		// Create the new Document
		Document document = handler.createDOM(hkxFile.getContentsVersion(), hkxFile.getClassVersion());
		
		// Create the "__data__" section in the document.
		Element root = handler.createSection(document, "__data__");
		
		TagXMLDataCreator creator = new TagXMLDataCreator(document);
		
		for(HKXObject content : hkxFile.content()) {
			Node contentXML = creator.create(content);
			root.appendChild(contentXML);
		}
		handler.writeToFile(document, outFile);
	}

}
