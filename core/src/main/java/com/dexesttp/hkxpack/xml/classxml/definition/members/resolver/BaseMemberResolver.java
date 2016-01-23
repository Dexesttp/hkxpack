package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;

import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.BaseMemberReader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonImportedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public interface BaseMemberResolver {
	/**
	 * Get the data size in the actual data section.
	 * @return the data size
	 */
	public int getSize();
	public BaseMemberReader getReader(String name, String vsubtype, String ctype, String etype) throws UnsupportedCombinaisonException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException, NonImportedClassException, NumberFormatException, IOException;
}
