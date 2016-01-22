package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;

public enum PtrMemberResolver implements BaseMemberResolver {
	TYPE_POINTER,
	TYPE_FUNCTIONPOINTER;

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public void setExtraData(String vsubtype, String ctype, String etype) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node getData(Document document, byte[] toRead, DataInterface data, Data1Interface data1,
			Data2Interface data2) {
		// TODO Auto-generated method stub
		return null;
	}
}
