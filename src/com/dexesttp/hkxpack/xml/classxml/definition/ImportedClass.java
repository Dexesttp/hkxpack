package com.dexesttp.hkxpack.xml.classxml.definition;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ImportedMember;

public class ImportedClass extends ClassXML {
	private final String classname;
	private final int classID;

	public ImportedClass(String classname, int classID) {
		this.classname = classname;
		this.classID = classID;
	}

	public ResolvedClass resolve() {
		ResolvedClass res = new ResolvedClass(classname, classID);
		try {
			for(ClassXMLMember member : members)
				if(member instanceof ImportedMember)
					res.addContent(((ImportedMember) member).resolve());
		} catch(IllegalArgumentException e) {
			throw new IllegalArgumentException(classname + " ----- " + e.getMessage());
		}
		return res;
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
