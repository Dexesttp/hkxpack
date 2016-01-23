package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;

import com.dexesttp.hkxpack.xml.classxml.definition.members.MemberResolver;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.ArrayMemberReader;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.BaseMemberReader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonImportedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public enum ArrayMemberResolver implements BaseMemberResolver {
	TYPE_ARRAY(16),
	TYPE_SIMPLEARRAY(16),
	TYPE_INPLACEARRAY(16),
	TYPE_RELARRAY(16);

	private final int size;

	private ArrayMemberResolver(int size) {
		this.size = size;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public BaseMemberReader getReader(String name, String vsubtype, String ctype, String etype) throws UnsupportedCombinaisonException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException, NonImportedClassException, NumberFormatException, IOException {
		if(vsubtype == "")
			throw new UnsupportedCombinaisonException();
		BaseMemberReader reader = MemberResolver.getEnum(vsubtype).getReader("", "", ctype, etype);
		return new ArrayMemberReader(name, getSize(), reader);
	}
}
