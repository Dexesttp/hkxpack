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
			if(contents.containsValue(i))
				return contents.inverse().get(i);
			return "" + i;
		}
		
		int get(String str) {
			if(contents.containsKey(str))
				return contents.get(str);
			try {
				return Integer.parseInt(str);
			} catch(NumberFormatException e) {
				return 0;
			}
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
		HKXEnum enumContainer = contents.get(enumName);
		if(enumContainer != null)
			return enumContainer.get(value);
		return "" + value;
	}
	
	/**
	 * Resolve a name from a given enumeration into its value.
	 * @param enumName
	 * @param value
	 * @return
	 */
	public int resolve(String enumName, String value) {
		HKXEnum enumContainer = contents.get(enumName);
		if(enumContainer == null)
			return 0;
		return enumContainer.get(value);
	}
}
