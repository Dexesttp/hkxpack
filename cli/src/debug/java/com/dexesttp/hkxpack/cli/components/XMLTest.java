package com.dexesttp.hkxpack.cli.components;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.tagreader.TagXMLReader;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;
import com.dexesttp.hkxpack.tagwriter.TagXMLWriter;

/**
 * Testing interface, used to perform live tests with Eclipse.
 */
public final class XMLTest {
	private static final Logger LOGGER = Logger.getLogger(XMLTest.class.getName());
	private XMLTest() {
		// NO OP
	}
	/**
	 * Tests a XML file {@code <filename>.xml} by reading it and writing it back under {@code <filename>-new.xml}
	 * @param rootName the root directory
	 * @param testName the file to test, without its extension
	 */
	public static void exec(final String rootName, final String testName) {
		String inputFileName = rootName + testName + ".xml";
		String outputFileName = rootName + testName + "-new.xml";
		DisplayProperties.displayDebugInfo = true;
		DisplayProperties.displayFileDebugInfo = true;
		DisplayProperties.displayReadTypesInfo = true;
		DisplayProperties.displayClassImportsInfo = true;
		DisplayProperties.displayEmbeddedData = true;
		try {
			// Read XML file
			File inFile = new File(inputFileName);
			HKXEnumResolver enumResolver = new HKXEnumResolver();
			HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
			TagXMLReader reader = new TagXMLReader(inFile, descriptorFactory);
			HKXFile hkxFile = reader.read();
			
			// Write XML file
			File outFile = new File(outputFileName);
			TagXMLWriter writer = new TagXMLWriter(outFile);
			writer.write(hkxFile);
		} catch (IOException | TransformerException | ParserConfigurationException | SAXException | InvalidTagXMLException e) {
			LOGGER.throwing(XMLTest.class.getName(), "exec", e);
		}
	}
}
