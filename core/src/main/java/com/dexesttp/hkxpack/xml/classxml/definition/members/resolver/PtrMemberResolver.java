package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.BaseMemberReader;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.PtrMemberReader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public enum PtrMemberResolver implements BaseMemberResolver {
	TYPE_POINTER,
	TYPE_FUNCTIONPOINTER;

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public BaseMemberReader getReader(String name, String vsubtype, String ctype, String etype)
			throws UnsupportedCombinaisonException, UnknownEnumerationException {
		return new PtrMemberReader(name, getSize());
	}
}
