package com.dexesttp.hkxpack.descriptor.enums;

/**
 * List of known flag values for ClassXML components.
 */
public enum Flag {
	UNKNOWN, FLAGS_NONE, NOT_OWNED, ALIGN_8, ALIGN_16, SERIALIZE_IGNORED;

	/**
	 * Get the relevant {@link Flag} from its name as a {@link String}.
	 * 
	 * @param string the name of the {@link Flag}.
	 * @return the {@link Flag} instance.
	 */
	public static Flag fromString(final String string) {
		try {
			return Flag.valueOf(string);
		} catch (IllegalArgumentException e) {
			return Flag.UNKNOWN;
		}
	}
}
