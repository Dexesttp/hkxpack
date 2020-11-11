package com.dexesttp.hkxpack.descriptor;

import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxpack.descriptor.reader.ClassXMLReader;
import com.google.common.collect.BiMap;

/**
 * An HKXEnumResolver stores all read enumerations as accessible values.
 */
public class HKXEnumResolver {
	private final transient Map<String, HKXEnum> contents = new HashMap<>();

	/**
	 * An enumeration extracted from a classXML file.
	 */
	private class HKXEnum {
		private final transient BiMap<String, Integer> contents;

		HKXEnum(final BiMap<String, Integer> contents) {
			this.contents = contents;
		}

		String get(final int index) {
			if (contents.containsValue(index)) {
				return contents.inverse().get(index);
			}
			return Integer.toString(index);
		}

		int get(final String enumName) {
			if (contents.containsKey(enumName)) {
				return contents.get(enumName);
			}
			try {
				return Integer.parseInt(enumName);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * Add a new Enum to the resolver.
	 * <p>
	 * This should only be done by the {@link ClassXMLReader} method. Please don't
	 * use this unless you're doing something technical.
	 * 
	 * @param name
	 * @param contents
	 */
	public void add(final String name, final BiMap<String, Integer> contents) {
		this.contents.put(name, new HKXEnum(contents));
	}

	/**
	 * Resolve a value from a given enumeration into its name.
	 * 
	 * @param enumName
	 * @param value
	 * @return
	 */
	public String resolve(final String enumName, final int value) {
		HKXEnum enumContainer = contents.get(enumName);
		if (enumContainer != null) {
			return enumContainer.get(value);
		}
		return Integer.toString(value);
	}

	/**
	 * Resolve a name from a given enumeration into its value.
	 * 
	 * @param enumName
	 * @param value
	 * @return
	 */
	public int resolve(final String enumName, final String value) {
		HKXEnum enumContainer = contents.get(enumName);
		if (enumContainer == null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return enumContainer.get(value);
	}
}
