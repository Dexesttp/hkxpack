package com.dexesttp.hkxpack.xml.classxml.definition;

import java.io.IOException;

public class ImportedClass extends ClassXML {
	private final String classname;
	private final int classID;

	public ImportedClass(String classname, int classID) {
		this.classname = classname;
		this.classID = classID;
	}

	public ResolvedClass resolve() throws IOException {
		return null;
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
