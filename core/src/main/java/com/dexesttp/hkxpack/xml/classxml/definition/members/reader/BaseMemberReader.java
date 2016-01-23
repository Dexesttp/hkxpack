package com.dexesttp.hkxpack.xml.classxml.definition.members.reader;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public abstract class BaseMemberReader {
	protected final String name;
	protected final int size;

	BaseMemberReader(String name, int size) {
		this.name = name;
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public abstract Node readDirect(Document document, byte[] toRead, DataInterface data, Data1Interface data1, Data2Interface data2) throws IOException, InvalidPositionException, UnsupportedCombinaisonException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException;

	public abstract Node readIndirect(Document document, long arrPos, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws UnsupportedCombinaisonException, IOException, InvalidPositionException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException;
}
