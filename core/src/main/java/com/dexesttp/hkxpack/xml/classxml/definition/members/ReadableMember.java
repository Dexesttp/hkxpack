package com.dexesttp.hkxpack.xml.classxml.definition.members;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.hkx.structs.Member;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolver.BaseMemberResolver;

public class ReadableMember extends ClassXMLMember {
	private final BaseMemberResolver resolver;
	private final long offset;
	private final String flag;
	
	@SuppressWarnings("rawtypes") 
	public ReadableMember(String name, String classname, String flags, long offset, Enum enumInst) {
		super(name, classname);
		this.offset = offset;
		this.flag = flags;
		resolver = (BaseMemberResolver) enumInst;
	}

	public Member getStruct() {
		return new Member(resolver.getSize(), offset);
	}

	public Node read(Document document, byte[] toRead, DataInterface data, Data1Interface data1, Data2Interface data2) throws IOException, InvalidPositionException {
		return resolver.getData(document, toRead, data, data1, data2);
	}
}
