package com.dexesttp.hkxpack.hkx.header.internals;

public class HeaderDescriptor {
	/**
	 * The file id, same for all hkx files (I assume says that it's a hkx file)
	 */
	public byte[] file_id = new byte[]
			{87, -32, -32, 87, 16, -64, -64, 16, 0, 0, 0, 0};
	/**
	 * The file version, over 4 bytes. See docs for what versions are what.
	 */
	public byte[] version = new byte[4];
	/**
	 * Extra data. This isn't labeled as constants as it changes between version 8 and 11.
	 */
	public byte[] extras = new byte[4];
	/**
	 * Some constant data. No idea what it is for.
	 */
	public byte[] constants = new byte[]
			{3, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 75, 0, 0, 0};
	/**
	 * A string containing the version name.
	 */
	public byte[] verName = new byte[14];
	/**
	 * Some more constants.
	 */
	public byte[] constants_2 = new byte[]
			{0, -1, 0, 0, 0, 0};
	/**
	 * This is either FF on version 8, or some data on version 11.
	 */
	public byte[] extras_v11 = new byte[2];
	/**
	 * This appears to be a padding before the next data chunk for version 11.
	 */
	public byte[] padding_v11 = new byte[2];
}
