package com.dexesttp.hkxpack.cli.commands;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkxwriter.HKXWriter;
import com.dexesttp.hkxpack.tagreader.TagXMLReader;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Packs a XML file into a HKX file.
 * @see Command_IO
 */
public class Command_pack extends Command_IO {
	@Override
	protected void execution_core(String inputFileName, String outputFileName,
			HKXEnumResolver enumResolver, HKXDescriptorFactory descriptorFactory)
					throws ParserConfigurationException, SAXException, IOException,
					InvalidTagXMLException, UnsupportedVersionError {
		// Read XML file
		File inFile = new File(inputFileName);
		TagXMLReader reader = new TagXMLReader(inFile, descriptorFactory);
		HKXFile file = reader.read();
		
		// Write HKX file
		File outFile = new File(outputFileName);
		outFile.createNewFile();
		HKXWriter writer = new HKXWriter(outFile, enumResolver, bufferSize);
		writer.write(file);
	}

	@Override
	protected String extractFileName(String ogName) {
		return ogName.substring(0, ogName.lastIndexOf(".")) + ".hkx";
	}

	@Override
	protected String[] getFileExtensions() {
		return new String[] {".xml"};
	}
}
