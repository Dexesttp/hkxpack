package com.dexesttp.hkxpack.xml.classxml.definition.members;

import java.io.IOException;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolver.MemberResolver;

public class ImportedMember extends ClassXMLMember {
	private final String vtype;
	private final String vsubtype;
	private final String ctype;
	private final String etype;

	public ImportedMember(String name, String vtype, String vsubtype, String ctype, String etype) {
		super(name);
		this.vtype = vtype;
		this.vsubtype = vsubtype;
		this.ctype = ctype;
		this.etype = etype;
	}

	public ResolvedMember resolve() {
		return MemberResolver.resolve(name, vtype, vsubtype, ctype, etype);
	}

	@Override
	public Resolver<Node> getResolver(HKXHandler handler) throws IOException, UnresolvedMemberException {
		throw new UnresolvedMemberException(name);
	}
}
