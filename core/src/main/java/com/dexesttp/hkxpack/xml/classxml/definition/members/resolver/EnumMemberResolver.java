package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.util.function.Function;

import com.dexesttp.hkxpack.resources.ByteUtils;

public enum EnumMemberResolver {
	TYPE_ENUM(8, (value) -> {return ByteUtils.getInt(value);}),
	TYPE_FLAGS(8, (value) -> {return ByteUtils.getInt(value);});

	private EnumMemberResolver(int size, Function<byte[], Integer> action) {
	}
	
	public void resolve(String ename, String name, String classname) {
	}
}
