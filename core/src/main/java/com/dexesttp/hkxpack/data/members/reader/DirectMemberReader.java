package com.dexesttp.hkxpack.data.members.reader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.function.Function;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.hkx.HKXConnector;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidArrayException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;

/**
 * A direct member is basically a byte[] which is then decrypted differently based on the actual value.
 * A full list of all known members can be found in classxml.members.resolver.DirectMemberResolver
 */
public class DirectMemberReader extends MemberReader {
	private final long size;
	private final Function<byte[], String> action;

	public DirectMemberReader(String name, long offset, long size, Function<byte[], String> action) {
		super(name, offset);
		this.size = size;
		this.action = action;
	}

	@Override
	public long getSize() {
		return this.size;
	}

	@Override
	public Node read(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		// Read data.
		Node contents = readInternal(position, document, connector);
		res.appendChild(contents);
		return res;
	}

	@Override
	public Node readInternal(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		byte[] contents = new byte[(int) getSize()];
		RandomAccessFile file = connector.data.setup(position + offset);
		file.read(contents);
		Node res = document.createTextNode(action.apply(contents));
		return res;
	}

}
