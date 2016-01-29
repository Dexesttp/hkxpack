package com.dexesttp.hkxpack.data.members.reader;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.hkx.HKXConnector;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidArrayException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.resources.PointerNameGiver;

public class PtrMemberReader extends MemberReader {
	private final long PTR_SIZE;

	public PtrMemberReader(String name, long offset, long PTR_SIZE) {
		super(name, offset);
		this.PTR_SIZE = PTR_SIZE;
	}

	@Override
	public long getSize() {
		return PTR_SIZE;
	}

	@Override
	public Node read(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		Node internal = readInternal(position, document, connector);
		res.appendChild(internal);
		return res;
	}

	@Override
	public Node readInternal(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		DataExternal ptrData = connector.data2.readNext();
		String name;
		long currPos = ptrData.from;
		while(position+offset > currPos) {
			ptrData = connector.data2.readNext();
			currPos = ptrData.from;
		}
		if(position+offset == currPos) {
			name = PointerNameGiver.getInstance().getName(ptrData.to);
		} else {
			connector.data2.backtrack();
			name = "null";
		}
		Node res = document.createTextNode(name);
		return res;
	}

}
