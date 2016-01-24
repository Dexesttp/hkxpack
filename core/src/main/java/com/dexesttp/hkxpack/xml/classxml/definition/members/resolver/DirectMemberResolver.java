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
	TYPE_VECTOR4(16, (value) -> {
		return "("+
				ByteUtils.getFloat(subarray(value, 0, 4))+" "+
				ByteUtils.getFloat(subarray(value, 4, 8))+" "+
				ByteUtils.getFloat(subarray(value, 8, 12))+" "+
				ByteUtils.getFloat(subarray(value, 12, 16))+")";
		}),
	TYPE_QSTRANSFORM(48, (value) -> {
		return "("+
				ByteUtils.getFloat(subarray(value, 0, 4))+" "+
				ByteUtils.getFloat(subarray(value, 4, 8))+" "+
				ByteUtils.getFloat(subarray(value, 8, 12))+") ("+
				ByteUtils.getFloat(subarray(value, 16, 20))+","+
				ByteUtils.getFloat(subarray(value, 20, 24))+","+
				ByteUtils.getFloat(subarray(value, 24, 28))+","+
				ByteUtils.getFloat(subarray(value, 28, 32))+") ("+
				ByteUtils.getFloat(subarray(value, 32, 36))+" "+
				ByteUtils.getFloat(subarray(value, 36, 40))+" "+
				ByteUtils.getFloat(subarray(value, 40, 44))+")";}),
	TYPE_MATRIX3(12, (value) -> {
		return "("+
				ByteUtils.getFloat(subarray(value, 0, 4))+","+
				ByteUtils.getFloat(subarray(value, 4, 8))+","+
				ByteUtils.getFloat(subarray(value, 8, 12))+"]";}),
	// TODO change these ones when you encounter them for the first time.
	TYPE_VARIANT(32, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_MATRIX4(128, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_QUATERNION(128, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_ROTATION(96, (value) -> {return ""+ByteUtils.getIntString(value);}),
	TYPE_TRANSFORM(96, (value) -> {return ""+ByteUtils.getIntString(value);});

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
	
	private static byte[] subarray(byte[] array, int begin, int end) {
		byte[] res = new byte[end - begin];
		for(int i = begin; i < end; i++) {
			res[i - begin] = array[i];	
		}
		return res;
	}
}
