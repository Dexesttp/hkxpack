package com.dexesttp.hkxpack.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ClassFilesUtils {
	private static String classResourcesList = "/properties/classxmllist.txt";
	public static Map<String, String> filenameConverter = new HashMap<String, String>();
	
	/**
	 * Set the folder where all the classDefs are.
	 * @param newFolder the folder to use.
	 * @deprecated
	 */
	public static void setFolder(String newFolder) {
		classResourcesList = newFolder;
	}
	
	public static void initFolder() throws IOException {
		URL paths = ClassFilesUtils.class.getResource(classResourcesList);
		BufferedReader reader = new BufferedReader(new InputStreamReader(paths.openStream()));
		String fileEntry;
		while((fileEntry = reader.readLine()) != null) {
			String className = extractName(fileEntry);
			filenameConverter.put(className, "/classxml/" + fileEntry);
		}
	}
	
	public static String getFileName(String classname) {
		return filenameConverter.get(classname);
	}
	
	private static String extractName(String fullName) {
		return fullName.substring(0, fullName.indexOf("_"));
	}
}
