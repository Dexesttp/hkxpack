package com.dexesttp.hkxpack.commons.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class AbstractReader<T> implements Reader<T> {
	/**
	 * The open file descriptor. 
	 */
	protected RandomAccessFile file = null;
	/**
	 * The position where the data begns at.
	 */
	protected long position = 0;
	/**
	 * The expected size of the data
	 */
	protected long size = 0;

	@Override
	public void connect(File file, long position, long size) throws FileNotFoundException {
		this.position = position;
		this.size = size;
		this.file = new RandomAccessFile(file, "r");
	}

	@Override
	public void close() throws IOException {
		file.close();
	}
}
