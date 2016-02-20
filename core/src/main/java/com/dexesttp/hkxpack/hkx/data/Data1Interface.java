package com.dexesttp.hkxpack.hkx.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.ByteUtils;

/**
 * Interface on the Data1 section of a HKX File.
 */
public class Data1Interface {
	private RandomAccessFile file;
	private SectionData header;
	private int lastPos = -1;

	/**
	 * Connect this {@link Data1Interface} to a {@link File}.
	 * @param file the {@link File} to connect to.
	 * @param dataHeader the {@link SectionData} relative to the Data section.
	 * @throws FileNotFoundException if the {@link File} couldn't be opened.
	 */
	public void connect(File file, SectionData dataHeader) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.header = dataHeader;
	}

	/**
	 * Read a given Internal data component from the file.
	 * @param pos the position of the wanted {@link DataInternal} component.
	 * @return the read {@link DataInternal}.
	 * @throws IOException if the file couldn't be read.
	 * @throws InvalidPositionException if the requested position was outside the Data1 section.
	 */
	public DataInternal read(int pos) throws IOException, InvalidPositionException {
		DataInternal data = new DataInternal();
		long dataPos = header.data1 + pos * 0x08;
		if(pos < 0 || dataPos > header.data2)
			throw new InvalidPositionException("DATA_1", pos );
		file.seek(header.offset + dataPos);
		byte[] dataLine = new byte[4];
		file.read(dataLine);
		data.from = ByteUtils.getLong(dataLine);
		if(data.from > header.offset + header.data1)
			throw new InvalidPositionException("DATA_1", pos );
		file.read(dataLine);
		data.to = ByteUtils.getLong(dataLine);
		this.lastPos  = pos;
		return data;
	}

	/**
	 * Reads the next element from the Data1 section.
	 * @return The requested {@link DataInternal}
	 * @throws IOException if there was a problem reading the file.
	 * @throws InvalidPositionException If the next element doesn't exist.
	 */
	public DataInternal readNext() throws IOException, InvalidPositionException {
		return read(++lastPos);
	}

	/**
	 * Cancel reading the next element.
	 */
	public void backtrack() {
		lastPos--;
	}

	/**
	 * Close this {@link Data1Interface} connection with the {@link File}.
	 * @throws IOException
	 */
	public void close() throws IOException {
		file.close();
	}
}
