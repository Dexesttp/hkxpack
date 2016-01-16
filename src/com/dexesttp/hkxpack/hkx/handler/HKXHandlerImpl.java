package com.dexesttp.hkxpack.hkx.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.dexesttp.hkxpack.hkx.definition.Header;
import com.dexesttp.hkxpack.hkx.reader.HeaderReader;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;

public class HKXHandlerImpl implements HKXHandler{
	protected File file = null;
	private HeaderReader headerReader = null;
	protected Header header = null;

	public HKXHandlerImpl() {
		this.headerReader = new HeaderReader();
		// TODO : create handler when invoked
	}
	
	@Override
	public void connect(File file) {
		this.file = file;
	}
	
	@Override
	public void init() throws UninitializedHKXException, FileNotFoundException {
		if(file == null)
			throw new UninitializedHKXException();
		headerReader.connect(file, 0, 0);
	}
	
	@Override
	public IHeader getHeader() throws UninitializedHKXException, IOException {
		if(header == null) {
			if(file == null)
				throw new UninitializedHKXException();
			header = headerReader.read();
		}
		return header;
	}
	
	@Override
	public void close() throws IOException {
		headerReader.close();
		file = null;
		header = null;
	}
}
