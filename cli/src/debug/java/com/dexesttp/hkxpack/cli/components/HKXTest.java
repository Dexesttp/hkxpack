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
public final class HKXTest {
	private static final Logger LOGGER = Logger.getLogger(HKXTest.class.getName());
	private HKXTest() {
		// NO OP
	}
	/**
	 * Tests a HKX file {@code <filename>.hkx} by reading it and writing it back under {@code <filename>-new.hkx}
	 * @param rootName the root directory
	 * @param testName the file to test, without its extension
	 */
	public static void exec(final String rootName, final String testName) {
		String inputFileName = rootName + testName + ".hkx";
		String outputFileName = rootName + testName + "-new.hkx";
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
			LOGGER.throwing(HKXTest.class.getName(), "exec", e);
		}
	}

}
