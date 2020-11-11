package com.dexesttp.hkxpack.hkx.header.internals;

/**
 * A general purpose Section descriptor, designed on the v8 and v11 sections.
 */
public class SectionDescriptor {
	/**
	 * Name of the section, null-terminated
	 */
	public byte[] secName = new byte[16];
	/**
	 * Constant value
	 */
	public byte[] constant = new byte[] { 0, 0, 0, -1 };
	/**
	 * Offset of the section (from the beginning of the file)
	 */
	public byte[] offset = new byte[4];
	/**
	 * First data part (position from the offset).
	 */
	public byte[] data1 = new byte[4];
	/**
	 * Second data part (position from the offset).
	 */
	public byte[] data2 = new byte[4];
	/**
	 * Third data part (position from the offset).
	 */
	public byte[] data3 = new byte[4];
	/**
	 * Fourth data part (position from the offset).
	 */
	public byte[] data4 = new byte[4];
	/**
	 * Fifth data part (position from the offset).
	 */
	public byte[] data5 = new byte[4];
	/**
	 * End of the section (from the offset)
	 */
	public byte[] end = new byte[4];
}
