package com.dexesttp.hkxpack.xml.classxml.definition;

public class ImportedClass extends ClassXML {
	private final String classname;
	private final int classID;

	public ImportedClass(String classname, int classID) {
		this.classname = classname;
		this.classID = classID;
	}

	public ResolvedClass resolve() {
		ResolvedClass res = new ResolvedClass(classname, classID);
		return res;
	}
	
	public String getName() {
		return classname;
	}
}
