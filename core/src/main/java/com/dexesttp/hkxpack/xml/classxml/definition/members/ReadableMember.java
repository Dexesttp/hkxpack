package com.dexesttp.hkxpack.xml.classxml.definition.members;

import com.dexesttp.hkxpack.xml.classxml.definition.members.resolver.BaseMemberResolver;

public class ReadableMember extends ClassXMLMember {
	private final BaseMemberResolver resolver;

	public ReadableMember(String name, String classname, BaseMemberResolver resolver) {
		super(name, classname);
		this.resolver = resolver;
	}

	public ReadableMember(String name, String classname, String flags, String offset, Enum enumInst) {
		super(name, classname);
		resolver = (BaseMemberResolver) enumInst;
	}
}
