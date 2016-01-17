package com.dexesttp.hkxpack.xml.classxml.definition.members;

import java.io.IOException;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;

public abstract class ClassXMLMember {
	protected final String name; 
	
	public ClassXMLMember(String name) {
		this.name = name;
	}

	public abstract Resolver<Node> getResolver(HKXHandler handler) throws IOException, UnresolvedMemberException, UninitializedHKXException;
}
