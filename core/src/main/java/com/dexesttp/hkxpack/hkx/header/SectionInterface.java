package com.dexesttp.hkxpack.hkx.header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

import com.dexesttp.hkxpack.hkx.header.internals.SectionDescriptor;
import com.dexesttp.hkxpack.resources.ByteUtils;

/**
 * Connects to a {@link File}, and allows easy retrieval and writing of {@link SectionData}.
 */
public class SectionInterface {
	private RandomAccessFile file;
	private HeaderData header;

	/**
	 * Connect to a given {@link File}, based on the given {@link HeaderData}.
	 * @param file the {@link File} to connect to.
	 * @param header the {@link HeaderData} to base the search on.
	 * @throws FileNotFoundException if there was a problem connecting to the given {@link File}.
	 */
	public void connect(File file, HeaderData header) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
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
		file.seek(0x40 + header.padding_after + sectionsize * sectionID);
		SectionDescriptor descriptor = new SectionDescriptor();
		file.write(section.name.getBytes());
		file.skipBytes(0x10 - section.name.length());
		file.write(descriptor.constant);
		file.write(ByteUtils.fromLong(section.offset, 4));
		file.write(ByteUtils.fromLong(section.data1, 4));
		file.write(ByteUtils.fromLong(section.data2, 4));
		file.write(ByteUtils.fromLong(section.data3, 4));
		file.write(ByteUtils.fromLong(section.data4, 4));
		file.write(ByteUtils.fromLong(section.data5, 4));
		file.write(ByteUtils.fromLong(section.end, 4));
		if(header.version == 11)
			for(int i = 0; i < 0x10; i++)
				file.writeByte(0xFF);
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
		file.seek(0x40 + header.padding_after + sectionsize * sectionID);
		SectionDescriptor descriptor = new SectionDescriptor();
		file.read(descriptor.secName);
		file.read(descriptor.constant);
		file.read(descriptor.offset);
		file.read(descriptor.data1);
		file.read(descriptor.data2);
		file.read(descriptor.data3);
		file.read(descriptor.data4);
		file.read(descriptor.data5);
		file.read(descriptor.end);
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
	public void close() throws IOException {
		file.close();
	}
}
