package com.dexesttp.hkxpack.resources;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ClassFilesUtils {
	private static String folder = "";
	public static Map<String, String> filenameConverter = new HashMap<String, String>();
	
	public static void setFolder(String newFolder) {
		folder = newFolder;
	}
	
	public static void initFolder() {
		File dirFile = new File(folder);
		for(final File fileEntry : dirFile.listFiles()) {
			String className = extractName(fileEntry.getName());
			filenameConverter.put(className, fileEntry.getAbsolutePath());
		}
	}
	
	public static String getFileName(String classname) {
		return filenameConverter.get(classname);
	}
	
	private static String extractName(String fullName) {
		return fullName.substring(0, fullName.indexOf("_"));
	}
}
