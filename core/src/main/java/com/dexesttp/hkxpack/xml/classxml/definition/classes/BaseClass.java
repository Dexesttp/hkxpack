package com.dexesttp.hkxpack.xml.classxml.definition.classes;

public class BaseClass extends ClassXML {
	private final String name;
	private final int classID;
	
	public BaseClass(String name, int classID) {
		this.name = name;
		this.classID = classID;
	}

	@Override
	public String getClassName() {
		return name;
	}

	@Override
	public int getClassID() {
		return classID;
	}

}
