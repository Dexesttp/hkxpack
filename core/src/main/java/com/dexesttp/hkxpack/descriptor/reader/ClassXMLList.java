package com.dexesttp.hkxpack.descriptor.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadError;
import com.dexesttp.hkxpack.resources.ClassFilesUtils;

class ClassXMLList {
	private static final String classResourcesList = "/properties/classxmllist.txt";
	public final Map<String, String> filenameConverter = new HashMap<String, String>();
	
	ClassXMLList() throws ClassListReadError {
		try {
			readEntries();
		} catch(IOException e) {
			throw new ClassListReadError(e);
		}
	}
	
	private void readEntries() throws IOException {
		URL paths = ClassFilesUtils.class.getResource(classResourcesList);
		BufferedReader reader = new BufferedReader(new InputStreamReader(paths.openStream()));
		String fileEntry;
		while((fileEntry = reader.readLine()) != null) {
			String className = extractName(fileEntry);
			filenameConverter.put(className, "/classxml/" + fileEntry);
		}
	}

	/**
	 * Retrieve a filename from the class name
	 * @param classname the class name
	 * @return the file name to retrieve data from
	 */
	public String getFileName(String classname) {
		return filenameConverter.get(classname);
	}
	
	private String extractName(String fullName) {
		return fullName.substring(0, fullName.indexOf("_"));
	}
}
