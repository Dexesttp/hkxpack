package com.dexesttp.hkxpack.xml.classxml.definition.classes;

public class ReadableClass extends ClassXML {
	protected final String classname;
	protected final int classID;
	
	public ReadableClass(String classname, int classID) {
		this.classname = classname;
		this.classID = classID;
	}

	@Override
	public String getClassName() {
		return classname;
	}

	@Override
	public int getClassID() {
		return classID;
	}
}
