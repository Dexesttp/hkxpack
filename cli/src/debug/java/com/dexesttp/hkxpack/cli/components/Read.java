package com.dexesttp.hkxpack.cli.components;

import java.io.File;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReader;
import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.tagwriter.TagXMLWriter;

/**
 * Testing interface, used to perform live tests with Eclipse.
 */
public class Read {
	/**
	 * Tests a HKX file {@code <filename>.hkx} by reading it and writing it back under {@code <filename>.xml}
	 * @param rootName the root directory
	 * @param testName the file to test, without its extension
	 */
	public static void exec(String rootName, String name) {
		String inputFileName = rootName + name + ".hkx";
		String outputFileName =  rootName + name + ".xml";
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
			HKXReader reader = new HKXReader(inFile, descriptorFactory, enumResolver);
			HKXFile hkxFile = reader.read();

			// Write file
			File outFile = new File(outputFileName);
			TagXMLWriter writer = new TagXMLWriter(outFile);
			writer.write(hkxFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
