package com.dexesttp.hkxpack.hkx.header;

/**
 * Contains information about a HKX File version and format.
 */
public class HeaderData {
	/**
	 * The version of the file, as an integer.
	 */
	public int version;

	/**
	 * The version name and identifier, contains additionnal information about the file's version.
	 */
	public String versionName;

	/**
	 * The padding after the header. Supported paddings are 0x00 (for most files) and 0x10 (for animation files).
	 */
	public long paddingAfter;
}
