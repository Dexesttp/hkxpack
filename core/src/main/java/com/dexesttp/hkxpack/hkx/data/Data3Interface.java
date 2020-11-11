package com.dexesttp.hkxpack.hkx.data;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Interface on the Data3 section of a HKX file {@link ByteBuffer}.
 */
public class Data3Interface {
	private transient ByteBuffer file;
	private transient SectionData header;

	/**
	 * Connect this {@link Data3Interface} to a {@link ByteBuffer}.
	 * 
	 * @param file       the {@link ByteBuffer} to connect to.
	 * @param dataHeader the {@link SectionData} relative to the Data section.
	 */
	public void connect(final ByteBuffer file, final SectionData data1) {
		this.file = file;
		this.header = data1;
	}

	/**
	 * Read a specific item from the data3 section
	 * 
	 * @param pos the position of the item to read
	 * @return the read DataExternal
	 * @throws InvalidPositionException if the position of the item isn't valid
	 */
	public DataExternal read(final int pos) throws InvalidPositionException {
		long dataPos = header.data3 + pos * 0x0C;
		if (pos < 0 || dataPos >= header.end) {
			throw new InvalidPositionException("DATA_3", pos);
		}
		DataExternal data = new DataExternal();
		((Buffer) file).position((int) (header.offset + dataPos));
		byte[] dataLine = new byte[4];
		file.get(dataLine);
		data.from = ByteUtils.getULong(dataLine);
		if (data.from > header.offset + header.data1) {
			throw new InvalidPositionException("DATA_3", pos);
		}
		file.get(dataLine);
		data.section = ByteUtils.getUInt(dataLine);
		file.get(dataLine);
		data.to = ByteUtils.getULong(dataLine);
		return data;
	}

	/**
	 * Write a given External data to the file {@link ByteBuffer} at the given
	 * position.
	 * 
	 * @param pos  the position to write the external data at.
	 * @param data the {@link DataExternal} to write.
	 * @return the position as section offset of the end of the written
	 *         {@link DataExternal}.
	 */
	public long write(final int pos, final DataExternal data) {
		long dataPos = header.data3 + pos * 0x0C;
		((Buffer) file).position((int) (header.offset + dataPos));
		file.put(ByteUtils.fromULong(data.from, 4));
		file.put(ByteUtils.fromULong(data.section, 4));
		file.put(ByteUtils.fromULong(data.to, 4));
		return dataPos + 0x0C;
	}

	/**
	 * @deprecated {@link ByteBuffer} usage no longer allows nor requires this step
	 */
	public void close() {
		// Deprecated
	}
}
