package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.util.function.Function;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.DirectMember;

public enum DirectMemberResolver {
	TYPE_VOID(0, (value) -> {return null;}),
	TYPE_BOOL(1, (value) -> {return null;}),
	TYPE_CHAR(1, (value) -> {return null;}),
	TYPE_INT8(8, (value) -> {return null;}),
	TYPE_UINT8(8, (value) -> {return null;}),
	TYPE_INT16(16, (value) -> {return null;}),
	TYPE_UINT16(16, (value) -> {return null;}),
	TYPE_INT32(32, (value) -> {return null;}),
	TYPE_UINT32(32, (value) -> {return null;}),
	TYPE_INT64(64, (value) -> {return null;}),
	TYPE_UINT64(64, (value) -> {return null;}),
	TYPE_REAL(32, (value) -> {return null;}),
	TYPE_VECTOR4(128, (value) -> {return null;}),
	TYPE_MATRIX4(128, (value) -> {return null;}),
	TYPE_QUATERNION(128, (value) -> {return null;}),
	TYPE_ROTATION(96, (value) -> {return null;}),
	TYPE_TRANSFORM(96, (value) -> {return null;});
	

	
	private final int size;
	private final Function<byte[], String> action;
	
	private DirectMemberResolver(int size, Function<byte[], String> action) {
		this.size = size;
		this.action = action;
	}
	
	public ResolvedMember resolve(String name) {
		return new DirectMember(name) {
			@Override
			public long getSize() {
				return size;
			}
			@Override
			public String apply(byte[] value) {
				return action.apply(value);
			}
		};
	}
}
