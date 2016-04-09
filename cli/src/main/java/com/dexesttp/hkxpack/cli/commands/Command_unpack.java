package com.dexesttp.hkxpack.cli.commands;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReader;
import com.dexesttp.hkxpack.tagwriter.TagXMLWriter;

/**
 * Unpacks a HKX file into a XML file.
 * @see Command_IO
 */
public class Command_unpack extends Command_IO {
	@Override
	protected void executionCore(final String inputFileName, final String outputFileName,
			final HKXEnumResolver enumResolver, final HKXDescriptorFactory descriptorFactory)
					throws IOException, InvalidPositionException,
					TransformerException, ParserConfigurationException {
		// Read HKX file
		File inFile = new File(inputFileName);
		HKXReader reader = new HKXReader(inFile, descriptorFactory, enumResolver);
		HKXFile hkxFile = reader.read();
		
		// Write XML file
		File outFile = new File(outputFileName);
		TagXMLWriter writer = new TagXMLWriter(outFile);
		writer.write(hkxFile);
	}

	@Override
	protected String extractFileName(final String ogName) {
		return ogName.substring(0, ogName.lastIndexOf('.')) + ".xml";
	}

	@Override
	protected String[] getFileExtensions() {
		return new String[] {".hkx"};
	}
}
