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
		for(ClassXMLMember member : members)
			if(member instanceof ImportedMember)
				res.addContent(((ImportedMember) member).resolve());
		return res;
	}
	
	public String getName() {
		return classname;
	}
}
