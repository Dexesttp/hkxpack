package com.dexesttp.hkxpack.descriptor;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;

public class HKXEnumResolver {
	private Map<String, HKXEnum> contents = new HashMap<>();
	
	private class HKXEnum {
		private BiMap<String, Integer> contents;
		HKXEnum(BiMap<String, Integer> contents) {
			this.contents = contents;
		}
		
		String get(int i) {
			return contents.inverse().get(i);
		}
		
		int get(String str) {
			return contents.get(str);
		}
	}
	
	void add(String name, BiMap<String, Integer> contents) {
		this.contents.put(name, new HKXEnum(contents));
	}
	
	public String resolve(String enumName, int value) {
		return contents.get(enumName).get(value);
	}
	
	public int resolve(String enumName, String value) {
		return contents.get(enumName).get(value);
	}
}
