package com.dexesttp.hkxpack.xml.classxml.definition.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.hkx.structs.Member;
import com.dexesttp.hkxpack.hkx.structs.Struct;
import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.xml.classxml.definition.members.type.ReadableMember;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

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
	
	public int getSize() {
		return getStruct().getSize();
	}

	public Node resolve(Document doc, String uUName, Struct currentStruct, DataInterface data, Data1Interface data1, Data2Interface data2) throws IOException, InvalidPositionException, UnsupportedCombinaisonException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException {
		return resolve(doc, uUName, currentStruct, data, data1, data2, false);
	}

	public Node resolve(Document doc, String uUName, Struct currentStruct, DataInterface data,
			Data1Interface data1, Data2Interface data2, boolean isStruct) throws IOException, InvalidPositionException, UnsupportedCombinaisonException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException {
		if(DisplayProperties.displayDebugInfo)
			System.out.println("[CLA]\t\t[CNAME]\t" + classname);
		Element rootNode = doc.createElement("hkobject");
		if(!isStruct) {
			rootNode.setAttribute("class", classname);
			rootNode.setAttribute("name", uUName);
			rootNode.setAttribute("signature", "0x" + Integer.toHexString(classID));
		}
		for(int i = 0; i < members.size(); i++) {
			Member member = currentStruct.members.get(i);
			ReadableMember reader = members.get(i);
			Node internal = reader.read(doc, member.toRead, data, data1, data2);
			if(internal != null)
				rootNode.appendChild(internal);
		}
		return rootNode;
	}
}
