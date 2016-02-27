package com.dexesttp.hkxpack.hkxwriter;

import java.io.File;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkxreader.HKXReader;

/**
 * Handles writing a {@link HKXFile} into a {@link File}, using the binary hkx notation.
 */
public class HKXWriter {
	private HKXEnumResolver enumResolver;
	private File outputFile;

	/**
	 * Creates a {@link HKXWriter}.
	 * @param outputFile the {@link File} to output data into.
	 * @param enumResolver the {@link HKXEnumResolver} to use.
	 */
	public HKXWriter(File outputFile, HKXEnumResolver enumResolver) {
		this.enumResolver = enumResolver;
		this.outputFile = outputFile;
	}
	
	/**
	 * Writes a {@link HKXFile}'s data inot this {@link HKXReader}'s {@link File}.
	 * @param file the {@link HKXFile} to take data from.
	 */
	public void write(HKXFile file) {
		// Create the header.
		HeaderData header = new HKXHeaderFactory().create(file);
		
		//
	}
}
