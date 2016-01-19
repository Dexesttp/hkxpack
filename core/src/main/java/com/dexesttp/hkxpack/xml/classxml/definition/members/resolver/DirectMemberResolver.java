package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.RandomAccessFile;
import java.util.function.Function;

import com.dexesttp.hkxpack.resources.ByteUtils;

public enum DirectMemberResolver {
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
	TYPE_QSTRANSFORM(128, (value) -> {return ""+ByteUtils.getInt(value);}),
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
	
	private DirectMemberResolver(int size, Function<byte[], String> action) {
	}
	
	private DirectMemberResolver(Function<RandomAccessFile, String> action) {
	}
	
	public void resolve(String name, String classname) {
	}
}
