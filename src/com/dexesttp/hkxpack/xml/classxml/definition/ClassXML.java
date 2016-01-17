package com.dexesttp.hkxpack.xml.classxml.definition;

import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;


public abstract class ClassXML {
	protected List<ClassXMLMember> members = new ArrayList<>();

	public void addContent(ClassXMLMember memberObj) {
		members.add(memberObj);
	}

	public List<ClassXMLMember> getMembers() {
		return members;
	}

	public abstract String getClassName();

	public abstract int getClassID();
}
