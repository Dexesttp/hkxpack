package com.dexesttp.hkxpack.xml.classxml.definition.members.reader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.function.Function;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public class DirectMemberReader extends BaseMemberReader {
	private final Function<byte[], String> action;

	public DirectMemberReader(String name, long size, Function<byte[], String> action) {
		super(name, size);
		this.action = action;
	}

	@Override
	public Node readDirect(Document document, byte[] toRead, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws IOException, InvalidPositionException, UnsupportedCombinaisonException {
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		Node content = document.createTextNode(action.apply(toRead));
		res.appendChild(content);
		return res;
	}

	@Override
	public Node readIndirect(Document document, long arrPos, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws UnsupportedCombinaisonException, IOException, InvalidPositionException {
		byte[] toRead = new byte[(int) size];
		RandomAccessFile reader = data.setup(arrPos);
		reader.read(toRead);
		return document.createTextNode(action.apply(toRead));
	}
}
