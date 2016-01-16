package com.dexesttp.hkxpack.commons.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Reader<T> {
	/**
	 * Connect the reader to a file.
	 * @param file the file to read from
	 * @param position the position to read from the file.
	 * @param size the size of the reqdqble section of the file.
	 */
	public void connect(File file, long position, long size) throws FileNotFoundException;
	
	/**
	 * Close the connection to the file.
	 * @throws IOException 
	 */
	public void close() throws IOException;
	
	/**
	 * Read the next item
	 * @return the next read item
	 * @throws IOException 
	 */
	public T read() throws IOException;

	/**
	 * Returns the connected state of the reader.
	 * @return true if the reader is connected
	 */
	public boolean isConnected();
}
