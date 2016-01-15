package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.util.function.Function;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.ArrayMember;

public enum ArrayMemberResolver {
	TYPE_ARRAY(8, (value) -> {return null;});
	
	private final int size;
	private final Function<byte[], String> action;

	private ArrayMemberResolver(int size, Function<byte[], String> action) {
		this.size = size;
		this.action = action;
	}
	
	public ResolvedMember resolve(ResolvedMember resolvedMember, String name) {
		return new ArrayMember<ResolvedMember> (name){
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
