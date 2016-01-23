package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.util.function.Function;

import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.BaseMemberReader;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.DirectMemberReader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public enum DirectMemberResolver implements BaseMemberResolver {
	TYPE_VOID(0, (value) -> {return "";}),
	TYPE_BOOL(1, (value) -> {return ByteUtils.getInt(value) == 0 ? "false" : "true";}),
	TYPE_CHAR(1, (value) -> {return ByteUtils.getIntString(value);}),
	TYPE_INT8(1, (value) -> {return ""+ByteUtils.getSIntString(value);}),
	TYPE_UINT8(1, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_INT16(2, (value) -> {return ""+ByteUtils.getSIntString(value);}),
	TYPE_UINT16(2, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_ULONG(8, (value) -> {return ""+ByteUtils.getLongString(value);}),
	TYPE_HALF(4, (value) -> {return ""+ByteUtils.getLongString(value);}),
	TYPE_INT32(4, (value) -> {return ""+ByteUtils.getSIntString(value);}),
	TYPE_UINT32(4, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_INT64(16, (value) -> {return ""+ByteUtils.getSIntString(value);}),
	TYPE_UINT64(16, (value) -> {return ByteUtils.getIntString(value);}),
	TYPE_REAL(4, (value) -> {return ""+ByteUtils.getFloat(value);}),
	// TODO change these ones when you encounter them for the first time.
	TYPE_VARIANT(32, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_VECTOR4(128, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_MATRIX4(128, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_QUATERNION(128, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_MATRIX3(96, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_ROTATION(96, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_TRANSFORM(96, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_QSTRANSFORM(128, (value) -> {return ""+ByteUtils.getIntString(value);});

	private final int size;
	private final Function<byte[], String> action;
	private DirectMemberResolver(int size, Function<byte[], String> action) {
		this.action = action;
		this.size = size;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public BaseMemberReader getReader(String name, String vsubtype, String ctype, String etype)
			throws UnsupportedCombinaisonException {
		return new DirectMemberReader(name, size, action);
	}
}
