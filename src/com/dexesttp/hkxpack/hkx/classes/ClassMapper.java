package com.dexesttp.hkxpack.hkx.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.dexesttp.hkxpack.hkx.definition.ClassName;

public class ClassMapper {
	private Map<Long, ClassName> content = new HashMap<>();
	
	public void add(long position, ClassName value) {
		content.put(position,  value);
	}
	
	public String getName(long key) {
		return content.get(key).className;
	}
	
	public byte[] getCode(long key) {
		return content.get(key).classCode;
	}
	
	public long getKey(String str) {
		for(Entry<Long, ClassName> entry : content.entrySet()) {
			if(entry.getValue().className.equals(str))
				return entry.getKey();
		}
		return -1;
	}
}
