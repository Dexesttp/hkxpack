package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.BaseMemberReader;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.StringMemberReader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public enum StringMemberResolver implements BaseMemberResolver {
	TYPE_CSTRING(0),
	TYPE_STRINGPTR(4);

	private int size;

	private StringMemberResolver(int size) {
		this.size = size;
	}
	
	@Override
	public int getSize() {
		return size;
	}

	@Override
	public BaseMemberReader getReader(String name, String vsubtype, String ctype, String etype)
			throws UnsupportedCombinaisonException, UnknownEnumerationException {
		return new StringMemberReader(name, getSize());
	}
}
