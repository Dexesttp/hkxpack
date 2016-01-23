package com.dexesttp.hkxpack.xml.classxml;

import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxpack.xml.classxml.definition.enumeration.EnumObj;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;

public class ClassXMLEnums {
	private static ClassXMLEnums instance;

	public static ClassXMLEnums getInstance() {
		if(instance == null)
			instance = new ClassXMLEnums();
		return instance;
	}

	private ClassXMLEnums() {
	}

	private Map<String, EnumObj> enumerations = new HashMap<>();
	public void addEnum(String name, EnumObj enumeration) {
		if(enumerations.containsKey(name))
			return;
		enumerations.put(name, enumeration);
	}
	
	public EnumObj getEnum(String name) throws UnknownEnumerationException {
		if(!enumerations.containsKey(name))
			throw new UnknownEnumerationException(name);
		return enumerations.get(name);
	}
}
