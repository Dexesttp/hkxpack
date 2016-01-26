package com.dexesttp.hkxpack.xml.classxml.definition.members.type;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;

public class ResolvedMember extends ClassXMLMember {
	private final MemberReader reader;
	
	public ResolvedMember(String name, String classname, MemberReader reader) {
		super(name, classname);
		this.reader = reader;
	}
	
	public MemberReader getReader() {
		return reader;
	}
}
