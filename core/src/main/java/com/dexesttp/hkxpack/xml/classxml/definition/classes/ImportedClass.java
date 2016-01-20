package com.dexesttp.hkxpack.xml.classxml.definition.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ImportedMember;

public class ImportedClass extends ClassXML {
	private final String classname;
	private String parent;
	private final int classID;
	protected List<ImportedMember> members = new ArrayList<>();

	public ImportedClass(String classname, int classID) {
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

	public void setParent(String parentName) {
		this.parent = parentName;
	}

	public void addContent(ImportedMember memberObj) {
		members.add(memberObj);
	}

	public List<ImportedMember> getMembers() {
		return members;
	}

	public ReadableClass resolve() throws IOException {
		// TODO resolve imported class into actual class.
		System.out.println(parent);
		return null;
	}
}
