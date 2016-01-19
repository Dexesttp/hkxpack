package com.dexesttp.hkxpack.xml.classxml.definition.members.resolved;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;

public abstract class ArrayMember<T extends ResolvedMember> extends ResolvedMember {

	public ArrayMember(String name, String classname) {
		super(name, classname);
	}
}
