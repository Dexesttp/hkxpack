package com.dexesttp.hkxpack.hkx.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;

/**
 * Interface to connect to the Data section of the file.
 */
public class DataInterface {
	private RandomAccessFile file;
	private SectionData header;

	/**
	 * Connect this {@link DataInterface} to a {@link File}, using information from the file's header.
	 * @param file the {@link File} to connect to.
	 * @param dataHeader the {@link SectionData} information relative to the Data sections.
	 * @throws FileNotFoundException if the {@link File} couldn't be opened.
	 */
	public void connect(File file, SectionData dataHeader) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.header = dataHeader;
	}
	
	/**
	 * Setup the file to a specific position.
	 * @param position the position to setup the file in Data at.
	 * @return the {@link RandomAccessFile}, at the given position in Data.
	 * @throws InvalidPositionException if the position is outside the file's Data definition.
	 * @throws IOException if the {@link RandomAccessFile} positionnement created an error.
	 */
	public RandomAccessFile setup(long position) throws IOException, InvalidPositionException {
		if(position < 0 || position > header.data1)
			throw new InvalidPositionException("DATA", position);
		file.seek(header.offset + position);
		return file;
	}
}
