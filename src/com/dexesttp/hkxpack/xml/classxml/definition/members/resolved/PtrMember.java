package com.dexesttp.hkxpack.xml.classxml.definition.members.resolved;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;

public abstract class PtrMember<T extends ResolvedMember> extends ResolvedMember {

	public PtrMember(String name, String classname) {
		super(name, classname);
	}
}
