package com.dexesttp.hkxpack.hkx.exceptions;

/**
 * An {@link InvalidPositionException} signals a reading error due to the fact a HKXDescriptor asked the HKXFile reading components to go over the file's section limits.
 * <br >
 * Please report any instance of {@link InvalidPositionException} as a HKXPack issue, as it is not intended behavior and can provide additional insight about the HXK structure.<br >
 * Make sure to provide a copy of the file you attempted to read, or information about how to retrieve the file if given file is under copyright.
 * <br >
 * <a href="https://github.com/Dexesttp/hkxpack/issues">Link to HKXPack's issue tracker</a>
 */
public class InvalidPositionException extends Exception {
	private static final long serialVersionUID = 5256901069828621035L;
	private final String section;
	
	/**
	 * Creates an {@link InvalidPositionException}.
	 * @param sectName the section name where the exception happened
	 * @param pos the invalid position
	 */
	public InvalidPositionException(final String sectName, final long pos) {
		super("Invalid position in " + sectName + " : " + pos);
		this.section = sectName;
	}

	/**
	 * Get this invalid section's name.
	 * @return the name
	 */
	public String getSection() {
		return section;
	}
}
