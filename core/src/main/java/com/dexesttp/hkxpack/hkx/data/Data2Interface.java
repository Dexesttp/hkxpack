package com.dexesttp.hkxpack.hkx.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.DisplayProperties;

public class Data2Interface {
	private RandomAccessFile file;
	private SectionData header;
	private int lastPos = -1;
	public void connect(File file, SectionData data1) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.header = data1;
	}
	
	public DataExternal read(int pos) throws IOException, InvalidPositionException {
		if(DisplayProperties.displayFileDebugInfo)
			System.out.println("[FILE]\t[INT]\t[DATA2]\t"+pos);
		DataExternal data = new DataExternal();
		long dataPos = header.data2 + pos * 0x0C;
		if(pos < 0 || dataPos > header.data3)
			throw new InvalidPositionException("DATA_2", pos );
		file.seek(header.offset + dataPos);
		byte[] dataLine = new byte[4];
		file.read(dataLine);
		data.from = ByteUtils.getLong(dataLine);
		file.read(dataLine);
		data.section = ByteUtils.getInt(dataLine);
		if(data.section > header.offset + header.data1)
			throw new InvalidPositionException("DATA_2", pos );
		file.read(dataLine);
		data.to = ByteUtils.getLong(dataLine);
		lastPos = pos;
		return data;
	}
	
	public void close() throws IOException {
		file.close();
	}

	public DataExternal readNext() throws IOException, InvalidPositionException {
		return read(++lastPos);
	}
}
