package com.dexesttp.hkxpack.hkxreader;

import java.util.HashMap;
import java.util.Map;

/**
 * Generate a name based on a file position
 */
public class PointerNameGenerator {
	private static final int START_VALUE = 90;
	private static final int INCREMENT = 1;
	private transient int position;
	private final transient Map<Long, String> contents = new HashMap<>();

	/**
	 * Create a new {@link PointerNameGenerator}, starting at the
	 * {@value #START_VALUE} value.
	 */
	PointerNameGenerator() {
		this(START_VALUE);
	}

	/**
	 * Create a new {@link PointerNameGenerator}, with names starting at the given
	 * value.
	 * 
	 * @param startValue the starting values for names.
	 */
	public PointerNameGenerator(final int startValue) {
		this.position = startValue;
	}

	/**
	 * Creates the next name
	 * 
	 * @return
	 */
	private String createName() {
		int nameID = position;
		position += INCREMENT;
		return "#" + nameID;
	}

	/**
	 * Get the name associated with a file position
	 * 
	 * @param position
	 * @return
	 */
	public String get(final long position) {
		if (contents.containsKey(position)) {
			return contents.get(position);
		}
		String name = createName();
		contents.put(position, name);
		return name;
	}
}
