package com.dexesttp.hkxpack.data.members.reader;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.hkx.HKXConnector;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidArrayException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class StringMemberReader extends MemberReader {
	private final long PTR_SIZE;

	public StringMemberReader(String name, long offset, long PTR_SIZE) {
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
		DataInternal posDescriptor = connector.data1.readNext();
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		RandomAccessFile file = connector.data.setup(posDescriptor.to);
		String text = ByteUtils.readString(file);
		res.setTextContent(text);
		return res;
	}

	@Override
	public Node readInternal(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		DataInternal posDescriptor = connector.data1.readNext();
		Element res = document.createElement("hkcstring");
		RandomAccessFile file = connector.data.setup(posDescriptor.to);
		String text = ByteUtils.readString(file);
		res.setTextContent(text);
		return res;
	}

}
