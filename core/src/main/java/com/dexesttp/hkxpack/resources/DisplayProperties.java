package com.dexesttp.hkxpack.resources;

/**
 * DisplayProperties is used when outputting data in the middle of the HKXPack
 * core. I'm not usre it's useful anymore as all data is logged in
 * {@link LoggerUtil}.
 */
public class DisplayProperties {
	// Output properties
	public static boolean ignoreSerialized = true;
	public static boolean displayEmbeddedData;
	// Display properties
	public static boolean displayDebugInfo;
	public static boolean displayFileDebugInfo;
	public static boolean displayReadTypesInfo;
	public static boolean displayClassImportsInfo;
}
