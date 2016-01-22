package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.RandomAccessFile;
import java.util.function.Function;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.resources.ByteUtils;

public enum StringMemberResolver implements BaseMemberResolver {
	TYPE_CSTRING((file) -> {
		try {
			return ByteUtils.readString(file);
		} catch(Exception e) {
			return "";
		}
	}),
	TYPE_STRINGPTR((file) -> {
		try {
			return ByteUtils.readString(file);
		} catch(Exception e) {
			return "";
		}
	});
	
	@Override
	public int getSize() {
		return 0;
	}
	
	private final Function<RandomAccessFile, String> action;
	private StringMemberResolver(Function<RandomAccessFile, String> action) {
		this.action = action;
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
