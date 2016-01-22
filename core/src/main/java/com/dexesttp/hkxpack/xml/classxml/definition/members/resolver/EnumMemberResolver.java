package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.util.function.Function;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.resources.ByteUtils;

public enum EnumMemberResolver implements BaseMemberResolver {
	TYPE_ENUM((value) -> {return ByteUtils.getInt(value);}),
	TYPE_FLAGS((value) -> {return ByteUtils.getInt(value);});

	private final Function<byte[], Integer> action;

	@Override
	public int getSize() {
		return 8;
	}
	
	private EnumMemberResolver(Function<byte[], Integer> action) {
		this.action = action;
	}
	public void resolve(String name, String classname) {
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
