package com.dexesttp.hkxpack.cli.commands;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.cli.utils.FileNameCreationException;
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
	/**
	 * {@inheritDoc}
	 */
	protected void executionCore(final String inputFileName, final String outputFileName,
			final HKXEnumResolver enumResolver, final HKXDescriptorFactory descriptorFactory)
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
	/**
	 * {@inheritDoc}
	 */
	protected String extractFileName(final String ogName) throws FileNameCreationException {
		String newName = "";
		try {
			newName = ogName.substring(0, ogName.lastIndexOf('.')) + ".hkx";
		}
		catch(StringIndexOutOfBoundsException e) {
			throw new FileNameCreationException("The file : " + ogName + " has a name that can't be converted.", e);
		}
		return newName;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected String[] getFileExtensions() {
		return new String[] {".xml"};
	}
}
