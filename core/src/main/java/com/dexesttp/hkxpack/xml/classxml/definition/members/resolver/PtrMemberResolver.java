package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.data.members.reader.PtrMemberReader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;

public enum PtrMemberResolver implements BaseMemberResolver {
	TYPE_POINTER,
	TYPE_FUNCTIONPOINTER;

	@Override
	public int getSize() {
		return 8;
	}

	@Override
	public MemberReader getReader(String name, long offset, String vsubtype, String ctype, String etype)
			throws UnknownEnumerationException {
		// TODO change PTR_SIZE
		return new PtrMemberReader(name, offset, 8);
	}
}
