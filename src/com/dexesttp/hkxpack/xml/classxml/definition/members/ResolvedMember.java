package com.dexesttp.hkxpack.xml.classxml.definition.members;

public abstract class ResolvedMember extends ClassXMLMember {

	public ResolvedMember(String name, String classname) {
		super(name, classname);
	}

	public abstract long getSize();
}
