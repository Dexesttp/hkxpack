package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;
import java.util.function.Function;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.ArrayMember;

public enum ArrayMemberResolver {
	TYPE_ARRAY(4, (value) -> {return null;}),
	TYPE_SIMPLEARRAY(4, (value) -> {return null;}),
	TYPE_INPLACEARRAY(4, (value) -> {return null;});
	
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
			public Resolver<Node> getResolver(HKXHandler handler) throws IOException, UnresolvedMemberException, UninitializedHKXException {
				byte[] arrayValue = new byte[size];
				//file.seek(position);
				//file.read(arrayValue);
				long arraySize = ByteUtils.getInt(arrayValue);
				Node root = handler.getDocument().createElement(name);
				for(int i = 0; i < size; i++) {
					// TODO rework logic to change how that works into a thing that actually works.
					Node member = resolvedMember.getResolver(handler).solve(null);
					root.appendChild(member);
				}
				return null;
			}
		};
	}
}
