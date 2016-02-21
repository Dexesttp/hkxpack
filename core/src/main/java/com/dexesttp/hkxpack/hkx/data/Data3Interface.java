package com.dexesttp.hkxpack.hkx.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.ByteUtils;

/**
 * Interface on the Data3 section of a HKX file.
 */
public class Data3Interface {
	private RandomAccessFile file;
	private SectionData header;

	/**
	 * Connect this {@link Data3Interface} to a {@link File}.
	 * @param file the {@link File} to connect to.
	 * @param dataHeader the {@link SectionData} relative to the Data section.
	 * @throws FileNotFoundException if the {@link File} couldn't be opened.
	 */
	public void connect(File file, SectionData data1) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.header = data1;
	}

	/**
	 * Read a specific item from the data3 section
	 * @param pos the position of the item to read
	 * @return the read DataExternal
	 * @throws IOException if there is a problem reading the file
	 * @throws InvalidPositionException if the position of the item isn't valid
	 */
	public DataExternal read(int pos) throws IOException, InvalidPositionException {
		DataExternal data = new DataExternal();
		long dataPos = header.data3 + pos * 0x0C;
		if(pos < 0 || dataPos > header.end)
			throw new InvalidPositionException("DATA_3", pos );
		file.seek(header.offset + dataPos);
		byte[] dataLine = new byte[4];
		file.read(dataLine);
		data.from = ByteUtils.getLong(dataLine);
		if(data.from > header.offset + header.data1)
			throw new InvalidPositionException("DATA_3", pos );
		file.read(dataLine);
		data.section = ByteUtils.getInt(dataLine);
		file.read(dataLine);
		data.to = ByteUtils.getLong(dataLine);
		return data;
	}

	/**
	 * Close this {@link Data3Interface} connection with the {@link File}.
	 * @throws IOException
	 */
	public void close() throws IOException {
		file.close();
	}
}
