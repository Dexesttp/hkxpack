package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;
import java.util.function.Function;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.PtrMember;

public enum PtrMemberResolver {
	TYPE_POINTER(8, (value) -> {return null;}),
	TYPE_FUNCTIONPOINTER(8, (value) -> {return null;}),
	TYPE_CSTRING(8, (value) -> {return null;}),
	TYPE_STRINGPTR(8, (value) -> {return null;}),
	TYPE_QSTRANSFORM(96, (value) -> {return null;});
	
	private final int size;
	private final Function<byte[], String> action;

	private PtrMemberResolver(int size, Function<byte[], String> action) {
		this.size = size;
		this.action = action;
	}
	
	public ResolvedMember resolve(ResolvedMember resolvedMember, String name) {
		return new PtrMember<ResolvedMember>(name) {
			@Override
			public long getSize() {
				return size;
			}

			@Override
			public Resolver<Node> getResolver(HKXHandler handler) throws IOException, UnresolvedMemberException, UninitializedHKXException {
				// TODO return not null
				return null;
			}
		};
	}
}
