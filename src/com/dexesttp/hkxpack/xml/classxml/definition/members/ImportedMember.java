package com.dexesttp.hkxpack.xml.classxml.definition.members;

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
}
