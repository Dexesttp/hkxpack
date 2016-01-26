package com.dexesttp.hkxpack.xml.classxml.definition.classes;

import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.structures.StructureReader;
import com.dexesttp.hkxpack.xml.classxml.definition.members.type.ResolvedMember;

public class ClassResolver extends ClassXML {
	protected final String classname;
	protected final int classID;
	protected List<ResolvedMember> members = new ArrayList<>();
	
	public ClassResolver(String classname, int classID) {
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
	
	void addMembers(List<ResolvedMember> list) {
		for(ResolvedMember member : list)
			members.add(member);
	}
	
	void addMember(ResolvedMember member) {
		members.add(member);
	}
	
	List<ResolvedMember> getMembers() {
		return members;
	}
	
	public int getSize() {
		return (int) (members.get(members.size() - 1).getReader().getOffset() + members.get(members.size() - 1).getReader().getSize());
	}

	public StructureReader getReader() {
		StructureReader res = new StructureReader(classname, classID);
		for(ResolvedMember member : members) {
			res.addMember(member.getReader());
		}
		return res;
	}
}
