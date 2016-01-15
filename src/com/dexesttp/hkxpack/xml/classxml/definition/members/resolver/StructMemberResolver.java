package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.util.function.Function;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;

public enum StructMemberResolver {
	TYPE_STRUCT(8, (value) -> {return null;});
	
	private final int size;
	private final Function<byte[], String> action;

	private StructMemberResolver(int size, Function<byte[], String> action) {
		this.size = size;
		this.action = action;
	}
	
	// TODO fix plz
	public ResolvedMember resolve(String className, String name) {
		return new ResolvedMember(name) {
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
