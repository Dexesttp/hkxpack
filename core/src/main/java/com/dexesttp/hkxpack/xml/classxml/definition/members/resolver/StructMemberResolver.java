package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;

import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;
import com.dexesttp.hkxpack.xml.classxml.definition.classes.ReadableClass;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.BaseMemberReader;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.StructMemberReader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonImportedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public enum StructMemberResolver implements BaseMemberResolver {
	TYPE_STRUCT;

	@Override
	public int getSize() {
		// TODO fix that for ALL files, not just anims.
		return 24;
	}

	@Override
	public BaseMemberReader getReader(String name, String vsubtype, String ctype, String etype)
			throws UnsupportedCombinaisonException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException, NonImportedClassException, NumberFormatException, IOException {
		ReadableClass classInst = ClassXMLList.getInstance().getOrResolveReadableClass(ctype);
		return new StructMemberReader(name, getSize(), classInst);
	}
}
