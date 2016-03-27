package com.dexesttp.hkxpack.cli.components;

import java.io.File;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.tagreader.TagXMLReader;
import com.dexesttp.hkxpack.tagwriter.TagXMLWriter;

/**
 * Testing interface, used to perform live tests with Eclipse.
 */
public class XMLTest {
	/**
	 * Tests a XML file {@code <filename>.xml} by reading it and writing it back under {@code <filename>-new.xml}
	 * @param rootName the root directory
	 * @param testName the file to test, without its extension
	 */
	public static void exec(String rootName, String testName) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
