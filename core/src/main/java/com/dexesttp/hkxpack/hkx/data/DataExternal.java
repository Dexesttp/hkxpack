package com.dexesttp.hkxpack.hkx.data;

/**
 * A data position descriptor aimed at a given section.
 */
public class DataExternal {
	/**
	 * The data parent, in the current section.
	 */
	public long from;

	/**
	 * The section the data is in.
	 */
	public int section;

	/**
	 * The data position, in the given section.
	 */
	public long to;
}
