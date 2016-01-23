package com.dexesttp.hkxpack.xml.classxml.definition.members.reader;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.xml.classxml.definition.enumeration.EnumObj;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public class EnumMemberReader extends BaseMemberReader {
	private final EnumObj enumType;

	public EnumMemberReader(String name, long size, EnumObj enumType) {
		super(name, size);
		this.enumType = enumType;
	}

	@Override
	public Node readDirect(Document document, byte[] toRead, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws IOException, InvalidPositionException, UnsupportedCombinaisonException {
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		int intVal = ByteUtils.getInt(toRead);
		String value;
		if(enumType != null)
			value = enumType.getFromValue(intVal);
		else
			value = "" + intVal;
		Node in = document.createTextNode(value);
		res.appendChild(in);
		return res;
	}

	@Override
	public Node readIndirect(Document document, long arrPos, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws UnsupportedCombinaisonException, IOException, InvalidPositionException {
		byte[] byteVal = new byte[8];
		RandomAccessFile file = data.setup(arrPos);
		file.read(byteVal);
		return readDirect(document, byteVal, data, data1, data2);
	}
}
