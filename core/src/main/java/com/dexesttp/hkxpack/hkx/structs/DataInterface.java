package com.dexesttp.hkxpack.hkx.structs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;

public class DataInterface {
	private RandomAccessFile file;
	private SectionData header;

	public void connect(File file, SectionData dataHead) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.header = dataHead;
	}

	public void read(long position, Struct structure) throws IOException, InvalidPositionException {
		if(position < 0 || position > header.data1)
			throw new InvalidPositionException("DATA", position);
		structure.read(position, this);
	}
	
	public RandomAccessFile setup(long position) throws IOException, InvalidPositionException {
		if(position < 0 || position > header.data1)
			throw new InvalidPositionException("DATA", position);
		file.seek(header.offset + position);
		return file;
	}
}
