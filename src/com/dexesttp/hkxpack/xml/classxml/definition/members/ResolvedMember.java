package com.dexesttp.hkxpack.xml.classxml.definition.members;

public abstract class ResolvedMember extends ClassXMLMember {

	public ResolvedMember(String name) {
		super(name);
	}

	public abstract long getSize();
	
	public abstract String apply(byte[] value);
}
