package com.dexesttp.hkxpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReader;
import com.dexesttp.hkxpack.resources.LoggerUtil;
import com.dexesttp.hkxpack.tagxml.TagXMLWriter;

public class Main {
	/**
	 * Reading entry point
	 * @param inputFileName
	 * @param outputFileName 
	 */
	public void read(String inputFileName, String outputFileName) {
		try {
			// Get output document
			
			// Read file
			File inFile = new File(inputFileName);
			HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory();
			HKXReader reader = new HKXReader(inFile, descriptorFactory);
			HKXFile hkxFile = reader.read();
			
			// Write file
			File outFile = new File(outputFileName);
			TagXMLWriter writer = new TagXMLWriter(outFile);
			writer.write(hkxFile);
			
			// Print logs
	        LoggerUtil.output();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public void write(String fileName, String outputFile) {
		try {
			// TODO Main.write
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
