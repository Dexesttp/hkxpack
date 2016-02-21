package com.dexesttp.hkxpack.hkx.header;

/**
 * Contains information about a given section of the file.
 */
public class SectionData {
	/**
	 * Name of the section
	 * Supported sections are __classnames__, __types__ and __data__
	 */
	public String name;

	/**
	 * Offset of the section in the file.
	 * <p>
	 * Note that this is a general offset, from the beginning of the file.
	 */
	public long offset;

	/**
	 * Internal offset of the file's first extra data component.
	 * <p>
	 * Note that this is an internal offset, based on the general {@link offset} of the section.
	 */
	public long data1;

	/**
	 * Internal offset of the file's second extra data component.
	 * <p>
	 * Note that this is an internal offset, based on the general {@link offset} of the section.
	 */
	public long data2;

	/**
	 * Internal offset of the file's third extra data component.
	 * <p>
	 * Note that this is an internal offset, based on the general {@link offset} of the section.
	 */
	public long data3;

	/**
	 * Internal offset of the file's fourth extra data component.
	 * <p>
	 * Note that this is an internal offset, based on the general {@link offset} of the section.
	 */
	public long data4;

	/**
	 * Internal offset of the file's fifth extra data component.
	 * <p>
	 * Note that this is an internal offset, based on the general {@link offset} of the section.
	 */
	public long data5;

	/**
	 * Internal offset of the file's section end.
	 * <p>
	 * Note that this is an internal offset, based on the general {@link offset} of the section.
	 */
	public long end;
}
