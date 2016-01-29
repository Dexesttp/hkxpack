package com.dexesttp.hkxpack.data.members.reader;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.hkx.HKXConnector;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidArrayException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.LoggerUtil;
import com.dexesttp.hkxpack.xml.classxml.definition.enumeration.EnumObj;

public class EnumMemberReader extends MemberReader {
	private final EnumObj internal;
	private final long size;

	public EnumMemberReader(String name, long offset, long size, EnumObj internal) {
		super(name, offset);
		this.size = size;
		this.internal = internal;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public Node read(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		// Create element
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		// Read data
		RandomAccessFile file = connector.data.setup(position + offset);
		byte[] contents = new byte[(int) getSize()];
		file.read(contents);
		int intVal = ByteUtils.getInt(contents);
		String value;
		if(internal != null)
			value = internal.getFromValue(intVal);
		else
			value = "" + intVal;
		Node in = document.createTextNode(value);
		res.appendChild(in);
		return res;
	}

	@Override
	public Node readInternal(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		LoggerUtil.error("MEMBR", "ENUM", "UNKNW", "I never encountered that before. An internal enum ?");
		return null;
	}

}
