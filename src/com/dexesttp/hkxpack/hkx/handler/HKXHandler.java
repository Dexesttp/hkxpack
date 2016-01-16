package com.dexesttp.hkxpack.hkx.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;

public interface HKXHandler {

	public void connect(File file);

	public void init() throws UninitializedHKXException, FileNotFoundException;

	public IHeader getHeader() throws UninitializedHKXException, IOException;

	public void close() throws IOException;

	public void readClassNames() throws FileNotFoundException, UninitializedHKXException, IOException;

}
