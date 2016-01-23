package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import com.dexesttp.hkxpack.xml.classxml.ClassXMLEnums;
import com.dexesttp.hkxpack.xml.classxml.definition.enumeration.EnumObj;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.BaseMemberReader;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.EnumMemberReader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public enum EnumMemberResolver implements BaseMemberResolver {
	TYPE_ENUM,
	TYPE_FLAGS;

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public BaseMemberReader getReader(String name, String vsubtype, String ctype, String etype)
			throws UnsupportedCombinaisonException, UnknownEnumerationException {
		EnumObj enumType = null;
		try {
			enumType = ClassXMLEnums.getInstance().getEnum(etype);
		} catch(UnknownEnumerationException e) {
			// NO OP
		}
		return new EnumMemberReader(name, getSize(), enumType);
	}
}
