package com.dexesttp.hkxpack.hkx.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.ByteUtils;

/**
 * Interface on the Data2 section of a HKX file.
 */
public class Data2Interface {
	private RandomAccessFile file;
	private SectionData header;
	private int lastPos = -1;

	/**
	 * Connect this {@link Data2Interface} to a {@link File}.
	 * @param file the {@link File} to connect to.
	 * @param dataHeader the {@link SectionData} relative to the Data section.
	 * @throws FileNotFoundException if the {@link File} couldn't be opened.
	 */
	public void connect(File file, SectionData data1) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.header = data1;
	}

	/**
	 * Read a given External data component from the file.
	 * @param pos the position of the wanted {@link DataExternal} component.
	 * @return the read {@link DataExternal}.
	 * @throws IOException if the file couldn't be read.
	 * @throws InvalidPositionException if the requested position was outside the Data2 section.
	 */
	public DataExternal read(int pos) throws IOException, InvalidPositionException {
		DataExternal data = new DataExternal();
		long dataPos = header.data2 + pos * 0x0C;
		if(pos < 0 || dataPos > header.data3)
			throw new InvalidPositionException("DATA_2", pos );
		file.seek(header.offset + dataPos);
		byte[] dataLine = new byte[4];
		file.read(dataLine);
		data.from = ByteUtils.getLong(dataLine);
		file.read(dataLine);
		data.section = ByteUtils.getInt(dataLine);
		if(data.section > header.offset + header.data1)
			throw new InvalidPositionException("DATA_2", pos );
		file.read(dataLine);
		data.to = ByteUtils.getLong(dataLine);
		lastPos = pos;
		return data;
	}

	/**
	 * Write a given External data to the file at the given position.
	 * @param pos the position to write the external data at.
	 * @param data the {@link DataExternal} to write.
	 * @return the position as section offset of the end of the written {@link DataExternal}.
	 * @throws IOException if there was a problem writing to the file.
	 */
	public long write(int pos, DataExternal data) throws IOException {
		long dataPos = header.data2 + pos * 0x0C;
		file.seek(header.offset + dataPos);
		file.write(ByteUtils.fromLong(data.from, 4));
		file.write(ByteUtils.fromLong(data.section, 4));
		file.write(ByteUtils.fromLong(data.to, 4));
		return dataPos + 0x0C;
	}

	/**
	 * Reads the next element from the Data2 section.
	 * @return The requested {@link DataExternal}
	 * @throws IOException if there was a problem reading the file.
	 * @throws InvalidPositionException If the next element doesn't exist.
	 */
	public DataExternal readNext() throws IOException, InvalidPositionException {
		return read(++lastPos);
	}

	/**
	 * Cancel reading the next element.
	 */
	public void backtrack() {
		lastPos--;
	}

	/**
	 * Close this {@link Data2Interface} connection with the {@link File}.
	 * @throws IOException
	 */
	public void close() throws IOException {
		file.close();
	}
}
