package com.dexesttp.hkxpack.hkxreader;

import java.util.HashMap;
import java.util.Map;

/**
 * Generate a name based on a file position
 */
public class PointerNameGenerator {
	private static int START_VALUE = 90;
	private static int INCREMENT = 1;
	private int position;
	private Map<Long, String> contents = new HashMap<>();

	PointerNameGenerator() {
		this(START_VALUE);
	}

	public PointerNameGenerator(int startValue) {
		this.position = startValue;
	}

	/**
	 * Creates the next name
	 * @return
	 */
	private String createName() {
		int nameID = position;
		position += INCREMENT;
		String name = "#" + nameID;
		return name;
	}

	/**
	 * Get the name associated with a file position
	 * @param position
	 * @return
	 */
	public String get(long position) {
		if(contents.containsKey(position))
			return contents.get(position);
		String name = createName();
		contents.put(position, name);
		return name;
	}
}
