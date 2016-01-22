package com.dexesttp.hkxpack.xml.classxml.definition.classes;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.hkx.structs.Struct;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ReadableMember;

public class ReadableClass extends ClassXML {
	protected final String classname;
	protected final int classID;
	protected List<ReadableMember> members = new ArrayList<>();
	
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
	
	void addMembers(List<ReadableMember> list) {
		for(ReadableMember member : list)
			members.add(member);
	}
	
	void addMember(ReadableMember member) {
		members.add(member);
	}
	
	List<ReadableMember> getMembers() {
		return members;
	}

	public Struct getStruct() {
		Struct res = new Struct();
		for(ReadableMember member : members)
			res.members.add(member.getStruct());
		return res;
	}

	public Node resolve(Struct currentStruct, DataInterface data, Data1Interface data1, Data2Interface data2) {
		// TODO this other thing right there.
		return null;
	}
}
