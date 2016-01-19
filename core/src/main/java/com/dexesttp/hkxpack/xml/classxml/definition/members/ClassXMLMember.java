package com.dexesttp.hkxpack.xml.classxml.definition.members;

public abstract class ClassXMLMember {
	protected final String name; 
	protected final String classname;
	
	public ClassXMLMember(String name, String classname) {
		this.name = name;
		this.classname = classname;
	}
}
