package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;
import java.util.function.Function;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.EnumMember;

public enum EnumMemberResolver {
	TYPE_ENUM(8, (value) -> {return null;}),
	TYPE_FLAGS(8, (value) -> {return null;});
	
	private final int size;
	private final Function<byte[], String> action;

	private EnumMemberResolver(int size, Function<byte[], String> action) {
		this.size = size;
		this.action = action;
	}
	
	public ResolvedMember resolve(ResolvedMember resolved, String ename, String name) {
		return new EnumMember(name) {
			@Override
			public long getSize() {
				return size;
			}
			
			@Override
			public Resolver<Node> getResolver(HKXHandler handler) throws IOException, UnresolvedMemberException {
				// TODO Auto-generated method stub
				return null;
			}

		};
	}
}
