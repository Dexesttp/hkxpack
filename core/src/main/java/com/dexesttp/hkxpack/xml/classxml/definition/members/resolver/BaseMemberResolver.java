package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;

public interface BaseMemberResolver {
	public void setExtraData(String vsubtype, String ctype, String etype);
	public int getSize();
	public Node getData(Document document, byte[] toRead, DataInterface data, Data1Interface data1, Data2Interface data2) throws IOException, InvalidPositionException;
}
