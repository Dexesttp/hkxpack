package com.dexesttp.hkxpack.xml.classxml.definition.enumeration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class EnumObj {
	private String name;
	private String flags;
	private Map<String, Integer> content = new HashMap<>();
	
	public EnumObj(String name, String flags) {
		this.name = name;
		this.flags = flags;
	}

	public void addEntry(String name, int value) {
		content.put(name,  value);
	}
	
	public String getName() {
		return name;
	}

	public String getFlags() {
		return flags;
	}

	public String getFromValue(int value) {
		for(Entry<String, Integer> key : content.entrySet()) {
			if(key.getValue() == value)
				return key.getKey();
		}
		return "ERROR_ENUM_VALUE_NOT_FOUND";
	}
}
