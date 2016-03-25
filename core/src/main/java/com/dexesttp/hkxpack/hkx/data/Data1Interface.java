package com.dexesttp.hkxpack.hkx.data;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.ByteUtils;

/**
 * Interface on the Data1 section of a HKX File {@link ByteBuffer}.
 */
public class Data1Interface {
	private ByteBuffer file;
	private SectionData header;
	private int lastPos = -1;

	/**
	 * Connect this {@link Data1Interface} to a {@link ByteBuffer}.
	 * @param file the {@link ByteBuffer} to connect to.
	 * @param dataHeader the {@link SectionData} relative to the Data section.
	 */
	public void connect(ByteBuffer file, SectionData dataHeader) {
		this.file = file;
		this.header = dataHeader;
	}

	/**
	 * Read a given Internal data component from the file {@link ByteBuffer}.
	 * @param pos the position of the wanted {@link DataInternal} component.
	 * @return the read {@link DataInternal}.
	 * @throws InvalidPositionException if the requested position was outside the Data1 section.
	 */
	public DataInternal read(int pos) throws InvalidPositionException {
		DataInternal data = new DataInternal();
		long dataPos = header.data1 + pos * 0x08;
		if(pos < 0 || dataPos > header.data2)
			throw new InvalidPositionException("DATA_1", pos );
		file.position((int) (header.offset + dataPos));
		byte[] dataLine = new byte[4];
		file.get(dataLine);
		data.from = ByteUtils.getLong(dataLine);
		if(data.from > header.offset + header.data1)
			throw new InvalidPositionException("DATA_1", pos );
		file.get(dataLine);
		data.to = ByteUtils.getLong(dataLine);
		this.lastPos  = pos;
		return data;
	}

	/**
	 * Writes the given Internal data to the file {@link ByteBuffer}, at the given position.
	 * @param pos the position to write the data to.
	 * @param internal the {@link DataInternal} to write.
	 * @return the position of the end of the {@link DataInternal}.
	 */
	public long write(int pos, DataInternal internal) {
		long dataPos = header.data1 + pos * 0x08;
		file.position((int) (header.offset + dataPos));
		file.put(ByteUtils.fromLong(internal.from, 4));
		file.put(ByteUtils.fromLong(internal.to, 4));
		return dataPos + 0x08;
	}

	/**
	 * Reads the next element from the Data1 section.
	 * @return The requested {@link DataInternal}
	 * @throws InvalidPositionException If the next element doesn't exist.
	 */
	public DataInternal readNext() throws InvalidPositionException {
		return read(++lastPos);
	}

	/**
	 * Cancel reading the next element.
	 */
	public void backtrack() {
		lastPos--;
	}

	/**
	 * @deprecated {@link ByteBuffer} usage no longer allows or requires this step
	 */
	public void close() throws IOException {
	}
}
