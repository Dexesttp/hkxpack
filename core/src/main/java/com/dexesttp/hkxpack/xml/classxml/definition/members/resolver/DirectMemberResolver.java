package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.util.function.Function;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.resources.ByteUtils;

public enum DirectMemberResolver implements BaseMemberResolver {
	TYPE_VOID(0, (value) -> {return "";}),
	TYPE_BOOL(1, (value) -> {return ByteUtils.getInt(value) == 0 ? "false" : "true";}),
	TYPE_CHAR(1, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_INT8(8, (value) -> {return ""+ByteUtils.getSInt(value);}),
	TYPE_UINT8(8, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_INT16(16, (value) -> {return ""+ByteUtils.getSInt(value);}),
	TYPE_UINT16(16, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_ULONG(16, (value) -> {return ""+ByteUtils.getLong(value);}),
	TYPE_HALF(16, (value) -> {return ""+ByteUtils.getLong(value);}),
	TYPE_INT32(32, (value) -> {return ""+ByteUtils.getSInt(value);}),
	TYPE_UINT32(32, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_INT64(64, (value) -> {return ""+ByteUtils.getSInt(value);}),
	TYPE_UINT64(64, (value) -> {return ""+ByteUtils.getInt(value);}),
	// TODO change these ones
	TYPE_REAL(32, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_VECTOR4(128, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_MATRIX4(128, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_QUATERNION(128, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_MATRIX3(96, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_ROTATION(96, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_TRANSFORM(96, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_QSTRANSFORM(128, (value) -> {return ""+ByteUtils.getInt(value);});
	
	private final int size;
	private DirectMemberResolver(int size, Function<byte[], String> action) {
		this.size = size;
	}
	
	public void resolve(String name, String classname) {
	}

	@Override
	public int getSize() {
		return size;
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
