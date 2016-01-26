package com.dexesttp.hkxpack.data.members.reader;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.hkx.HKXConnector;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidArrayException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;

public class SerializedMemberReader extends MemberReader {
	private final long size;

	public SerializedMemberReader(String name, long offset, long size) {
		super(name, offset);
		this.size = size;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public Node read(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		return readInternal(position, document, connector);
	}

	@Override
	public Node readInternal(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		Node result = document.createComment(" " + name + " SERIALIZE_IGNORED ");
		return result;
	}

}
