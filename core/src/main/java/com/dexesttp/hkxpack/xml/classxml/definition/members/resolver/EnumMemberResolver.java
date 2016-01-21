package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.util.function.Function;

import com.dexesttp.hkxpack.resources.ByteUtils;

public enum EnumMemberResolver implements BaseMemberResolver {
	TYPE_ENUM((value) -> {return ByteUtils.getInt(value);}),
	TYPE_FLAGS((value) -> {return ByteUtils.getInt(value);});

	private final Function<byte[], Integer> action;

	private EnumMemberResolver(Function<byte[], Integer> action) {
		this.action = action;
	}
	public void resolve(String name, String classname) {
	}
}
