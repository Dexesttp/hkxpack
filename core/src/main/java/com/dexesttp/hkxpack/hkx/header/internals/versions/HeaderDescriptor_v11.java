package com.dexesttp.hkxpack.hkx.header.internals.versions;

import com.dexesttp.hkxpack.hkx.header.internals.HeaderDescriptor;

/**
 * Describes the v11 version of the header.
 */
public class HeaderDescriptor_v11 extends HeaderDescriptor {
	/**
	 * The version where this header is necessary
	 */
	public static final int VERSION_11 = 11;
	/**
	 * Padding bytes found after the Header descriptor, if there is padding.
	 */
	public byte[] padding = new byte[]
			{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	/**
	 * Creates a {@link HeaderDescriptor_v11}.
	 */
	public HeaderDescriptor_v11() {
		super();
		version = new byte[] {11, 0, 0, 0};
		extras = new byte[] {8, 1, 0, 1};
		verName = new byte[] {'h', 'k', '_', '2', '0', '1', '4', '.', '1', '.', '0', '-', 'r', '1'};
		extras11 = new byte[] {21, 0};
		padding11 = new byte[] {16, 0};
	}
}
