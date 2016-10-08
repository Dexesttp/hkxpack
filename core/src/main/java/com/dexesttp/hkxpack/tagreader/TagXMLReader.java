package com.dexesttp.hkxpack.tagreader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Reads a TagXML file into a {@link HKXFile}.
 * <p>
 * Use {@link TagXMLReader#read()} to process the file.
 */
public class TagXMLReader {
	private final transient HKXDescriptorFactory descriptorFactory;
	private final transient Document document;
	private final transient TagXMLFileHandler handler;

	/**
	 * Creates a TagXML reader.
	 * @param file the {@link File} to read from.
	 * @param descriptorFactory the {@link HKXDescriptorFactory} to use while parsing the file.
	 * @throws IOException if there was an error accessing the {@link File}.
	 * @throws SAXException if there was an error parsing the XML file.
	 * @throws ParserConfigurationException should never happen, please report this error if it happens : <br >
	 * <a href="https://github.com/Dexesttp/hkxpack/issues">The HKXPack's issue tracker</a>
	 */
	public TagXMLReader(final File file, final HKXDescriptorFactory descriptorFactory)
			throws ParserConfigurationException, SAXException, IOException {
		this.handler = new TagXMLFileHandler(file);
		this.document = handler.getDocument();
		this.descriptorFactory = descriptorFactory;
	}

	/**
	 * Creates a TagXML reader from an existing {@link Document}
	 * @param file the {@link Document} to read from.
	 * @param descriptorFactory the {@link HKXDescriptorFactory} to use while parsing the file.
	 */
	public TagXMLReader(final Document document, final HKXDescriptorFactory descriptorFactory) {
		this.handler = new TagXMLFileHandler(null);
		this.document = document;
		this.descriptorFactory = descriptorFactory;
	}

	/**
	 * Read the data from the file.
	 * @return the read {@link HKXFile}
	 * @throws IOException if there was an error accessing a descriptor.
	 * @throws InvalidTagXMLException if there was an error retrieving relevant TagXML elements.
	 */
	public HKXFile read() throws IOException, InvalidTagXMLException {
		// Retrieve the section node.
		Node section = handler.getSectionNode(document, "__data__");
		if(section == null) {
			throw new InvalidTagXMLException(SBundle.getString("error.tag.read.section") + "__data__");
		}
		
		// Get the relevant initialized HKXFile.
		Node root = handler.getRootNode(document);
		HKXFile hkxFile = handler.getHKXFile(root);
		
		// Read the object nodes
		TagXMLNodeHandler nodeHandler = new TagXMLNodeHandler(descriptorFactory);
		NodeList objectNodes = section.getChildNodes();
		for(int i = 0; i < objectNodes.getLength(); i++) {
			Node rootNode = objectNodes.item(i);
			if(rootNode.getNodeName().equals("hkobject")) {
				HKXObject object = nodeHandler.handleObject(rootNode);
				hkxFile.add(object);
			}
		}
		
		return hkxFile;
	}
}
