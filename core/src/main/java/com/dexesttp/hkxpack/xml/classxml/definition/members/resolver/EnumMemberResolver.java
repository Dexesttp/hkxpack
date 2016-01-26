package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.data.members.reader.EnumMemberReader;
import com.dexesttp.hkxpack.resources.LoggerUtil;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLEnums;
import com.dexesttp.hkxpack.xml.classxml.definition.enumeration.EnumObj;
import com.dexesttp.hkxpack.xml.classxml.definition.members.MemberResolver;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;

public enum EnumMemberResolver implements BaseMemberResolver {
	TYPE_ENUM,
	TYPE_FLAGS;

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public MemberReader getReader(String name, long offset, String vsubtype, String ctype, String etype) {
		BaseMemberResolver internal = MemberResolver.getEnum(vsubtype);
		EnumObj enumInternal = null;
		try {
			enumInternal = ClassXMLEnums.getInstance().getEnum(etype);
		} catch(UnknownEnumerationException e) {
			LoggerUtil.error("READ", "ENUM", "MISS", "Unknown enum : " + etype);
		}
		return new EnumMemberReader(name, offset, internal.getSize(), enumInternal);
	}
}
