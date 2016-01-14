package com.dexesttp.hkxpack.xml.classxml.definition;

import java.util.HashMap;
import java.util.Map;

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
}
