package com.dexesttp.hkxpack.xml.classxml.definition.members.type;

import java.io.IOException;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.MemberResolver;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.BaseMemberReader;
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
	
	public ReadableMember resolve() throws NumberFormatException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException, UnsupportedCombinaisonException, NonImportedClassException, IOException {
		BaseMemberResolver enumInst = MemberResolver.getEnum(vtype);
		if(enumInst == null)
			System.err.println("Unknown type : " + vtype);
		BaseMemberReader reader = enumInst.getReader(name, vsubtype, ctype, etype);
		return new ReadableMember(name, classname, Long.parseLong(offset), flag, reader);
	}
}
