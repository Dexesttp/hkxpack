package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.data.members.reader.StructMemberReader;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;
import com.dexesttp.hkxpack.xml.classxml.definition.classes.ClassResolver;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonImportedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public enum StructMemberResolver implements BaseMemberResolver {
	TYPE_STRUCT;

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public MemberReader getReader(String name, long offset, String vsubtype, String ctype, String etype)
			throws UnknownEnumerationException, NumberFormatException, UnknownClassException, IOException, NonResolvedClassException, UnsupportedCombinaisonException, NonImportedClassException {
		ClassResolver classInst = ClassXMLList.getInstance().getOrResolveReadableClass(ctype);
		return new StructMemberReader(name, offset, classInst.getReader());
	}
}
