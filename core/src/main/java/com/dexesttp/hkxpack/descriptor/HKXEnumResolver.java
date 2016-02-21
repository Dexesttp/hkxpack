package com.dexesttp.hkxpack.descriptor;

import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxpack.descriptor.reader.ClassXMLReader;
import com.google.common.collect.BiMap;

/**
 * An HKXEnumResolver stores all read enumerations as accessible values.
 */
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
	
	/**
	 * Add a new Enum to the resolver.
	 * <p>
	 * This should only be done by the {@link ClassXMLReader} method. Please don't use this unless you're doing something technical.
	 * @param name
	 * @param contents
	 */
	public void add(String name, BiMap<String, Integer> contents) {
		this.contents.put(name, new HKXEnum(contents));
	}
	
	/**
	 * Resolve a value from a given enumeration into its name.
	 * @param enumName
	 * @param value
	 * @return
	 */
	public String resolve(String enumName, int value) {
		return contents.get(enumName).get(value);
	}
	
	/**
	 * Resolve a name from a given enumeration into its value.
	 * @param enumName
	 * @param value
	 * @return
	 */
	public int resolve(String enumName, String value) {
		return contents.get(enumName).get(value);
	}
}
