package com.dexesttp.hkxpack.xml.classxml.definition.members;

import com.dexesttp.hkxpack.xml.classxml.definition.members.resolver.BaseMemberResolver;

public abstract class ResolvedMember extends ClassXMLMember {
	private final BaseMemberResolver resolver;

	public ResolvedMember(String name, String classname, BaseMemberResolver resolver) {
		super(name, classname);
		this.resolver = resolver;
	}
}
