package com.dexesttp.hkxpack.resources;

import java.util.ArrayList;

public class DisplayProperties {
	public static boolean ignoreSerialized = true;
	public static boolean displayEmbeddedData = false;
	public static boolean displayDebugInfo = false;
	public static boolean displayFileDebugInfo = false;
	public static boolean displayReadTypesInfo = false;
	public static boolean displayClassImportsInfo = false;
	
	private static ArrayList<String> arrStr = new ArrayList<>();
	// Read types handler (to be refactored elsewhere)
	public static void addReadType(String string) {
		if(!arrStr.contains(string))
			arrStr.add(string);
	}
	
	public static void outReadTypesInfo() {
		for(String name : arrStr) 
			System.out.println("[TYP]\t\t\t" + name);
	}
}
