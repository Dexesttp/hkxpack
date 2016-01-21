package com.dexesttp.hkxpack.hkx.header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.dexesttp.hkxpack.hkx.header.internals.SectionDescriptor;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class SectionInterface {
	private RandomAccessFile file;
	private HeaderData header;
	
	public void connect(File file, HeaderData header) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.header = header;
	}
	
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
		file.writeLong(section.offset);
		file.writeLong(section.data1);
		file.writeLong(section.data2);
		file.writeLong(section.data3);
		file.writeLong(section.data4);
		file.writeLong(section.data5);
		file.writeLong(section.end);
	}

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
	
	public void close() throws IOException {
		file.close();
	}
}
