package com.dexesttp.hkxpack.xml.classxml.definition.members.reader;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public class StringMemberReader extends BaseMemberReader {
	public StringMemberReader(String name, int size) {
		super(name, size);
	}

	@Override
	public Node readDirect(Document document, byte[] toRead, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws IOException, InvalidPositionException, UnsupportedCombinaisonException {
		if(DisplayProperties.displayDebugInfo)
			System.out.println("[MEM]\t[STR]\t[DIR]\t" + name);
		RandomAccessFile file;
		DataInternal ptrAddr = data1.readNext();
		file = data.setup(ptrAddr.to);
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		String value = ByteUtils.readString(file);
		Node txt = document.createTextNode(value);
		res.appendChild(txt);
		return res;
	}

	@Override
	public Node readIndirect(Document document, long arrPos, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws UnsupportedCombinaisonException, IOException, InvalidPositionException {
		if(DisplayProperties.displayDebugInfo)
			System.out.println("[MEM]\t[STR]\t[INDIR]\t" + arrPos);
		RandomAccessFile file;
		DataInternal ptrAddr = data1.readNext();
		file = data.setup(ptrAddr.to);
		Element res = document.createElement("hkparam");
		String value = ByteUtils.readString(file);
		Node txt = document.createTextNode(value);
		res.appendChild(txt);
		return res;
	}

}
