package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.data.members.reader.StringMemberReader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;

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
	public MemberReader getReader(String name, long offset, String vsubtype, String ctype, String etype)
			throws UnknownEnumerationException {
		// TODO change PTR_SIZE
		return new StringMemberReader(name, offset, 8);
	}
}
