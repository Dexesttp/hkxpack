package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.data.members.reader.ArrayMemberReader;
import com.dexesttp.hkxpack.xml.classxml.definition.members.MemberResolver;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonImportedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public enum ArrayMemberResolver implements BaseMemberResolver {
	TYPE_ARRAY,
	TYPE_SIMPLEARRAY,
	TYPE_INPLACEARRAY,
	TYPE_RELARRAY;

	@Override
	public int getSize() {
		return 16;
	}

	@Override
	public MemberReader getReader(String name, long offset, String vsubtype, String ctype, String etype) throws UnknownEnumerationException, NumberFormatException, UnknownClassException, IOException, NonResolvedClassException, UnsupportedCombinaisonException, NonImportedClassException {
		BaseMemberResolver internal = MemberResolver.getEnum(vsubtype);
		if(internal == null)
			return null;
		MemberReader internalReader = internal.getReader("", 0, "", ctype, etype);
		// TODO change PTR_SIZE
		return new ArrayMemberReader(name, offset, internalReader, 8);
	}
}
