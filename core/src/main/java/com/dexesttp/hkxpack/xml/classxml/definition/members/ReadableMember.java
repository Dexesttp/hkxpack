package com.dexesttp.hkxpack.xml.classxml.definition.members;

import com.dexesttp.hkxpack.hkx.structs.Member;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolver.BaseMemberResolver;

public class ReadableMember extends ClassXMLMember {
	private final BaseMemberResolver resolver;
	private final long offset;
	private final String flag;
	
	@SuppressWarnings("rawtypes") 
	public ReadableMember(String name, String classname, String flags, long offset, Enum enumInst) {
		super(name, classname);
		this.offset = offset;
		this.flag = flags;
		resolver = (BaseMemberResolver) enumInst;
	}

	public Member getStruct() {
		return new Member(resolver.getSize(), offset);
	}
}
