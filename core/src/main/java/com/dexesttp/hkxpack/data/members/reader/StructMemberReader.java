package com.dexesttp.hkxpack.data.members.reader;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.data.structures.StructureReader;
import com.dexesttp.hkxpack.hkx.HKXConnector;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidArrayException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;

public class StructMemberReader extends MemberReader {
	private final StructureReader internals;

	public StructMemberReader(String name, long offset, StructureReader internals) {
		super(name, offset);
		this.internals = internals;
	}

	@Override
	public long getSize() {
		return internals.getSize();
	}
	
	@Override
	public Node read(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		Node content = readInternal(position, document, connector);
		res.appendChild(content);
		return res;
	}

	@Override
	public Node readInternal(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		return internals.read(position + offset, document, connector, "");
	}

}
