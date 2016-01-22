package com.dexesttp.hkxpack.xml.classxml.definition.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ImportedMember;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NotKnownClassException;

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

	public ReadableClass resolve() throws IOException, NonResolvedClassException, NotKnownClassException {
		ReadableClass classInst = new ReadableClass(classname, classID);
		if(parent != null) {
			ReadableClass parentInst = ClassXMLList.getInstance().getReadableClass(parent);
			classInst.addMembers(parentInst.getMembers());
		}
		for(ImportedMember member : members)
			classInst.addMember(member.resolve());
		return classInst;
	}
}
