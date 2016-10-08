package com.dexesttp.hkxpack.hkx.exceptions;

/**
 * Thrown if a file with a non-supported version was attempted to be read.
 */
public class UnsupportedVersionError extends Exception {
	private static final long serialVersionUID = -1869309792519998570L;

	/**
	 * Creates an {@link UnsupportedVersionError}.
	 * @param versionName the illegal version name
	 */
	public UnsupportedVersionError(final String versionName) {
		super("File version not supported : " + versionName);
	}
}
