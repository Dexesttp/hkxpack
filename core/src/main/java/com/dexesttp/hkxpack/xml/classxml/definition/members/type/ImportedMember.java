package com.dexesttp.hkxpack.xml.classxml.definition.members.type;

import java.io.IOException;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.data.members.reader.SerializedMemberReader;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.MemberResolver;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolver.BaseMemberResolver;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonImportedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public class ImportedMember extends ClassXMLMember {
	private final String offset;
	private final String vtype;
	private final String vsubtype;
	private final String ctype;
	private final String etype;
	private final String flag;

	public ImportedMember(String name, String classname, String offset, String flag, String vtype, String vsubtype, String ctype, String etype) {
		super(name, classname);
		this.offset = offset;
		this.vtype = vtype;
		this.vsubtype = vsubtype;
		this.ctype = ctype;
		this.etype = etype;
		this.flag = flag;
	}
	
	public ResolvedMember resolve() throws NumberFormatException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException, UnsupportedCombinaisonException, NonImportedClassException, IOException {
		BaseMemberResolver enumInst = MemberResolver.getEnum(vtype);
		if(enumInst == null)
			System.err.println("Unknown type : " + vtype);
		MemberReader reader;
		if(flag == "SERIALIZE_IGNORED")
			reader = new SerializedMemberReader(name, Long.parseLong(offset), enumInst.getSize());
		else
			reader = enumInst.getReader(name, Long.parseLong(offset), vsubtype, ctype, etype);
		return new ResolvedMember(name, classname, reader);
	}
}
