package com.dexesttp.hkxpack.cli.components;

import java.io.File;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkxwriter.HKXWriter;
import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.tagreader.TagXMLReader;

/**
 * Testing interface, used to perform live tests with Eclipse.
 */
public class Write {
	/**
	 * Tests a HKX file {@code <filename>.xml} by reading it and writing it back under {@code <filename>.hkx}
	 * @param rootName the root directory
	 * @param testName the file to test, without its extension
	 */
	public static void exec(String rootName, String testName) {
		String inputFileName = rootName + testName + ".xml";
		String outputFileName = rootName + testName + "-new.hkx";
		DisplayProperties.displayDebugInfo = true;
		DisplayProperties.displayFileDebugInfo = true;
		DisplayProperties.displayReadTypesInfo = true;
		DisplayProperties.displayClassImportsInfo = true;
		DisplayProperties.displayEmbeddedData = true;
		try {
			// Read file
			File inFile = new File(inputFileName);
			HKXEnumResolver enumResolver = new HKXEnumResolver();
			HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
			TagXMLReader reader = new TagXMLReader(inFile, descriptorFactory);
			HKXFile file = reader.read();
			
			File outFile = new File(outputFileName);
			HKXWriter writer = new HKXWriter(outFile, enumResolver);
			writer.write(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
