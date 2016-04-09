package com.dexesttp.hkxpack;

import java.io.File;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReader;
import com.dexesttp.hkxpack.hkxwriter.HKXWriter;
import com.dexesttp.hkxpack.tagreader.TagXMLReader;
import com.dexesttp.hkxpack.tagwriter.TagXMLWriter;

/**
 * Easy entry point for the HKXPack core.
 * @since 0.0.1-alpha
 * @deprecated
 * <p>
 * These methods use the following classes :<br>
 * {@link HKXReader} to read a HKX file into a {@link HKXFile} DOM-like component.<br>
 * {@link TagXMLReader} to read a XML file inot a {@link HKXFile} DOM-like component.<br>
 * {@link HKXWriter} to write a {@link HKXFile} into a hkx File.<br>
 * {@link TagXMLWriter} to write a {@link HKXFile} into a xml File.
 */
public class Main {
	/**
	 * Convert a HKXFile to a XML file.
	 * @param inputFileName
	 * @param outputFileName 
	 */
	public void read(final String inputFileName, final String outputFileName) {
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
			// NO OP
		}
	}
	
	/**
	 * Convert a XML file to an HKX file.
	 * @param inputFileName
	 * @param outputFileName
	 */
	public void write(final String inputFileName, final String outputFileName) {
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
			// NO OP
		}
	}
}
