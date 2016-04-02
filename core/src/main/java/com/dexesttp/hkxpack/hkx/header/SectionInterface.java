package com.dexesttp.hkxpack.hkx.header;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.dexesttp.hkxpack.hkx.header.internals.SectionDescriptor;
import com.dexesttp.hkxpack.hkx.header.internals.versions.HeaderDescriptor_v11;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Connects to a {@link ByteBuffer}, and allows easy retrieval and writing of {@link SectionData}.
 */
public class SectionInterface {
	private transient ByteBuffer file;
	private transient HeaderData header;

	/**
	 * Connect to a given {@link ByteBuffer}, based on the given {@link HeaderData}.
	 * @param file the {@link ByteBuffer} to connect to.
	 * @param header the {@link HeaderData} to base the search on.
	 */
	public void connect(final ByteBuffer file, final HeaderData header)  {
		this.file = file;
		this.header = header;
	}

	/**
	 * Write a {@link SectionData} in the file, as the given Section ID.
	 * <p>
	 * Supported Section IDs are 0, 1, and 2.<br >
	 * It is expected for the section ID 0 to be __classnames__,
	 * 1 to be __types__ and 2 to be __data__
	 * @param section The {@link SectionData} to write.
	 * @param sectionID The Section ID to write the {@link SectionData} at.
	 */
	public void compress(final SectionData section, final int sectionID) {
		long sectionsize;
		if(header.version == HeaderDescriptor_v11.VERSION_11)
			sectionsize = 0x40;
		else
			sectionsize = 0x30;
		file.position((int) (0x40 + header.padding_after + sectionsize * sectionID));
		SectionDescriptor descriptor = new SectionDescriptor();
		file.put(section.name.getBytes());
		file.position(file.position() + (0x10 - section.name.length()));
		file.put(descriptor.constant);
		file.put(ByteUtils.fromULong(section.offset, 4));
		file.put(ByteUtils.fromULong(section.data1, 4));
		file.put(ByteUtils.fromULong(section.data2, 4));
		file.put(ByteUtils.fromULong(section.data3, 4));
		file.put(ByteUtils.fromULong(section.data4, 4));
		file.put(ByteUtils.fromULong(section.data5, 4));
		file.put(ByteUtils.fromULong(section.end, 4));
		if(header.version == HeaderDescriptor_v11.VERSION_11)
			for(int i = 0; i < 0x10; i++)
				file.put((byte) 0xFF);
	}

	/**
	 * Read the given Section ID.
	 * <p>
	 * Supported SectionIDs are 0, 1 and 2.
	 * @param sectionID the Section ID to read.
	 * @return the read {@link SectionData}
	 */
	public SectionData extract(final int sectionID) {
		long sectionsize;
		if(header.version == HeaderDescriptor_v11.VERSION_11)
			sectionsize = 0x40;
		else
			sectionsize = 0x30;
		SectionData data = new SectionData();
		file.position((int) (0x40 + header.padding_after + sectionsize * sectionID));
		SectionDescriptor descriptor = new SectionDescriptor();
		file.get(descriptor.secName);
		file.get(descriptor.constant);
		file.get(descriptor.offset);
		file.get(descriptor.data1);
		file.get(descriptor.data2);
		file.get(descriptor.data3);
		file.get(descriptor.data4);
		file.get(descriptor.data5);
		file.get(descriptor.end);
		// Convert name
		data.name = new String(descriptor.secName, StandardCharsets.US_ASCII);
		int last0 = data.name.indexOf(0);
		data.name = last0 == -1 ? data.name : data.name.substring(0, last0);
		// Convert offsets 
		data.offset = ByteUtils.getULong(descriptor.offset);
		data.data1 = ByteUtils.getULong(descriptor.data1);
		data.data2 = ByteUtils.getULong(descriptor.data2);
		data.data3 = ByteUtils.getULong(descriptor.data3);
		data.data4 = ByteUtils.getULong(descriptor.data4);
		data.data5 = ByteUtils.getULong(descriptor.data5);
		data.end = ByteUtils.getULong(descriptor.end);
		return data;
	}

	/**
	 * Close the connection with the given {@link ByteBuffer}
	 * @deprecated {@link ByteBuffer} usage no longer allows nor requires this step
	 */
	public void close() {
		// Deprecated
	}
}
