package com.dexesttp.hkxpack.data.members;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.HKXConnector;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidArrayException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;

public abstract class MemberReader {

	protected final String name;
	protected final long offset;

	protected MemberReader(String name, long offset) {
		this.name = name;
		this.offset = offset;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Reads the data in the format defined by the member, from the connected file, to the given document, as a DOM Node.
	 * @param position the position of the struct the data is embedded into.
	 * @param document the output DOM Document.
	 * @param connector the HKX Connector
	 * @return the relevant output Tag DOM node.
	 * @throws InvalidPositionException 
	 * @throws IOException 
	 * @throws InvalidArrayException 
	 */
	public abstract Node read(long position, Document document, HKXConnector connector) throws IOException, InvalidPositionException, InvalidArrayException;

	/**
	 * Reads the data in the format defined by the member, from the connected file, to the given document, as a DOM Node.
	 * @param position the position of the struct the data is embedded into.
	 * @param document the output DOM Document.
	 * @param connector the HKX Connector
	 * @return the relevant output Tag DOM node.
	 * @throws InvalidPositionException 
	 * @throws IOException 
	 * @throws InvalidArrayException 
	 */
	public abstract Node readInternal(long position, Document document, HKXConnector connector) throws IOException, InvalidPositionException, InvalidArrayException;

	/**
	 * The size of the relevant Member reader.
	 * @return
	 */
	public abstract long getSize();

	/**
	 * The offset of the current member
	 * @return
	 */
	public long getOffset() {
		return offset;
	}

}
