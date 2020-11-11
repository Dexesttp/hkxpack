package com.dexesttp.hkxpack.tagwriter;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;

/**
 * Handles writing {@link HKXFile} data into a {@link File} using the TagXML
 * notation.
 */
public class TagXMLWriter {
	private final transient File outFile;

	/**
	 * Creates a {@link TagXMLWriter}.
	 * 
	 * @param outputFile the file to output the data into.
	 */
	public TagXMLWriter(final File outputFile) {
		this.outFile = outputFile;
	}

	/**
	 * Write an {@link HKXFile} as an XML file.
	 * 
	 * @param hkxFile the HKXFiel to write.
	 * @throws IOException                  if the XML file couldn't be written.
	 * @throws TransformerException         if there was a problem handling the
	 *                                      {@link HKXFile}'s content.
	 * @throws ParserConfigurationException if there was a problem creating the XML
	 *                                      {@link Document}.
	 */
	public void write(final HKXFile hkxFile) throws IOException, TransformerException, ParserConfigurationException {
		TagXMLHandler handler = new TagXMLHandler();
		// Create the new Document
		Document document = handler.createDOM(hkxFile.getContentsVersion(), hkxFile.getClassVersion());

		// Create the "__data__" section in the document.
		Element root = handler.createSection(document, "__data__");

		TagXMLDataCreator creator = new TagXMLDataCreator(document);

		for (HKXObject content : hkxFile.getContentCollection()) {
			Node contentXML = creator.create(content);
			root.appendChild(contentXML);
		}
		handler.writeToFile(document, outFile);
	}
}
