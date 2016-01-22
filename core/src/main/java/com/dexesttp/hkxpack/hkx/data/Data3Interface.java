package com.dexesttp.hkxpack.hkx.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class Data3Interface {
	private RandomAccessFile file;
	private SectionData header;
	public void connect(File file, SectionData data1) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.header = data1;
	}
	
	public DataExternal read(int pos) throws IOException, InvalidPositionException {
		DataExternal data = new DataExternal();
		long dataPos = header.data3 + pos * 0x08;
		if(pos < 0 || dataPos > header.end)
			throw new InvalidPositionException("DATA_3", pos );
		file.seek(header.offset + dataPos);
		byte[] dataLine = new byte[4];
		file.read(dataLine);
		data.from = ByteUtils.getLong(dataLine);
		file.read(dataLine);
		data.section = ByteUtils.getInt(dataLine);
		file.read(dataLine);
		data.to = ByteUtils.getLong(dataLine);
		return data;
	}
	
	public void close() throws IOException {
		file.close();
	}
}
