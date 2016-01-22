package com.dexesttp.hkxpack.hkx.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class Data1Interface {
	private RandomAccessFile file;
	private SectionData header;
	public void connect(File file, SectionData data1) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.header = data1;
	}
	
	public DataInternal read(int pos) throws IOException, InvalidPositionException {
		DataInternal data = new DataInternal();
		long dataPos = header.data1 + pos * 0x08;
		if(pos < 0 || dataPos > header.data2)
			throw new InvalidPositionException("DATA_1", pos );
		file.seek(header.offset + dataPos);
		byte[] dataLine = new byte[4];
		file.read(dataLine);
		data.from = ByteUtils.getLong(dataLine);
		file.read(dataLine);
		data.to = ByteUtils.getLong(dataLine);
		return data;
	}
	
	public void close() throws IOException {
		file.close();
	}
}
