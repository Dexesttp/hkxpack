package com.dexesttp.hkxpack.hkx.data;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Interface on the Data2 section of a HKX file {@link ByteBuffer}.
 */
public class Data2Interface {
	private transient ByteBuffer file;
	private transient SectionData header;
	private transient int lastPos = -1;

	/**
	 * Connect this {@link Data2Interface} to a {@link ByteBuffer}.
	 * @param file the {@link ByteBuffer} to connect to.
	 * @param dataHeader the {@link SectionData} relative to the Data section.
	 */
	public void connect(final ByteBuffer file, final SectionData data1) {
		this.file = file;
		this.header = data1;
	}

	/**
	 * Read a given External data component from the file {@link ByteBuffer}.
	 * @param pos the position of the wanted {@link DataExternal} component.
	 * @return the read {@link DataExternal}.
	 * @throws InvalidPositionException if the requested position was outside the Data2 section.
	 */
	public DataExternal read(final int pos) throws InvalidPositionException {
		long dataPos = header.data2 + pos * 0x0C;
		if(pos < 0 || dataPos > header.data3) {
			throw new InvalidPositionException("DATA_2", pos );
		}
		DataExternal data = new DataExternal();
		((Buffer)file).position((int) (header.offset + dataPos));
		byte[] dataLine = new byte[4];
		file.get(dataLine);
		data.from = ByteUtils.getULong(dataLine);
		file.get(dataLine);
		data.section = ByteUtils.getUInt(dataLine);
		if(data.section > header.offset + header.data1) {
			throw new InvalidPositionException("DATA_2", pos );
		}
		file.get(dataLine);
		data.to = ByteUtils.getULong(dataLine);
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
	public long write(final int pos, final DataExternal data) {
		long dataPos = header.data2 + pos * 0x0C;
		((Buffer)file).position((int) (header.offset + dataPos));
		file.put(ByteUtils.fromULong(data.from, 4));
		file.put(ByteUtils.fromULong(data.section, 4));
		file.put(ByteUtils.fromULong(data.to, 4));
		return dataPos + 0x0C;
	}

	/**
	 * Reads the next element from the Data2 section.
	 * @return The requested {@link DataExternal}
	 * @throws IOException if there was a problem reading the file.
	 * @throws InvalidPositionException If the next element doesn't exist.
	 */
	public DataExternal readNext() throws InvalidPositionException {
		return read(++lastPos);
	}

	/**
	 * Cancel reading the next element.
	 */
	public void backtrack() {
		lastPos--;
	}

	/**
	 * @deprecated {@link ByteBuffer} usage no longer allows nor requires this step
	 */
	public void close() {
		// Deprecated
	}
}
