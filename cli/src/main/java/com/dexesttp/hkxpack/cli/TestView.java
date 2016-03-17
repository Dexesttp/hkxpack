package com.dexesttp.hkxpack.cli;

import java.io.File;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReader;
import com.dexesttp.hkxpack.hkxwriter.HKXWriter;
import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.resources.LoggerUtil;
import com.dexesttp.hkxpack.tagreader.TagXMLReader;
import com.dexesttp.hkxpack.tagwriter.TagXMLWriter;

public class TestView {
	private static final String testName = "Locomotion";
	public static void main(String[] args) {
		read(args, testName);
		write(args);
		read(args, testName + "-new");
	}
	
	public static void read(String[] args, String name) {
		String inputFileName = "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\" + name + ".hkx";
		String outputFileName =  "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\" + name + ".xml";
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
	
	public static void write(String[] args) {
		String inputFileName = "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\" + testName + ".xml";
		String outputFileName = "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\" + testName + "-new.hkx";
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
		
		// Print logs
        LoggerUtil.output();
	}
	
	public static void xmlTest(String[] args) {
		String inputFileName = "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\" + testName + ".xml";
		String outputFileName = "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\" + testName + "-new.xml";
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
		
		// Print logs
        LoggerUtil.output();
	}
}
