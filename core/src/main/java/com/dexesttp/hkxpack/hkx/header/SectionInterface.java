package com.dexesttp.hkxpack.hkx.header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.dexesttp.hkxpack.hkx.header.internals.SectionDescriptor;
import com.dexesttp.hkxpack.resources.ByteUtils;

/**
 * Connects to a {@link File}, and allows easy retrieval and writing of {@link SectionData}.
 */
public class SectionInterface {
	private ByteBuffer file;
	private HeaderData header;

	/**
	 * Connect to a given {@link File}, based on the given {@link HeaderData}.
	 * @param file the {@link File} to connect to.
	 * @param header the {@link HeaderData} to base the search on.
	 * @throws FileNotFoundException if there was a problem connecting to the given {@link File}.
	 */
	public void connect(ByteBuffer file, HeaderData header) throws FileNotFoundException {
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
	 * @throws IOException if there was a problem writing to the {@link File}
	 */
	public void compress(SectionData section, int sectionID) throws IOException {
		long sectionsize;
		if(header.version == 11)
			sectionsize = 0x40;
		else
			sectionsize = 0x30;
		file.position((int) (0x40 + header.padding_after + sectionsize * sectionID));
		SectionDescriptor descriptor = new SectionDescriptor();
		file.put(section.name.getBytes());
		file.position(file.position() + (0x10 - section.name.length()));
		file.put(descriptor.constant);
		file.put(ByteUtils.fromLong(section.offset, 4));
		file.put(ByteUtils.fromLong(section.data1, 4));
		file.put(ByteUtils.fromLong(section.data2, 4));
		file.put(ByteUtils.fromLong(section.data3, 4));
		file.put(ByteUtils.fromLong(section.data4, 4));
		file.put(ByteUtils.fromLong(section.data5, 4));
		file.put(ByteUtils.fromLong(section.end, 4));
		if(header.version == 11)
			for(int i = 0; i < 0x10; i++)
				file.put((byte) 0xFF);
	}

	/**
	 * Read the given Section ID.
	 * <p>
	 * Supported SectionIDs are 0, 1 and 2.
	 * @param sectionID the Section ID to read.
	 * @return the read {@link SectionData}
	 * @throws IOException if there was a problem while reading the {@link File}.
	 */
	public SectionData extract(int sectionID) throws IOException {
		long sectionsize;
		if(header.version == 11)
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
		data.offset = ByteUtils.getLong(descriptor.offset);
		data.data1 = ByteUtils.getLong(descriptor.data1);
		data.data2 = ByteUtils.getLong(descriptor.data2);
		data.data3 = ByteUtils.getLong(descriptor.data3);
		data.data4 = ByteUtils.getLong(descriptor.data4);
		data.data5 = ByteUtils.getLong(descriptor.data5);
		data.end = ByteUtils.getLong(descriptor.end);
		return data;
	}

	/**
	 * Close the connection with the given {@link File}
	 * @throws IOException if there was a problem while closing the {@link File}
	 */
/*	public void close() throws IOException {
		file.close();
	}*/
}
