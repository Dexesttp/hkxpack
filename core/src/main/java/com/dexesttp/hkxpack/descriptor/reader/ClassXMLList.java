package com.dexesttp.hkxpack.descriptor.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadException;

/**
 * List of all the available ClassXML resources.
 */
class ClassXMLList {
	private static final String CLASS_RESOURCES_LIST = "/properties/classxmllist.txt";
	public final transient Map<String, String> filenameConverter = new HashMap<String, String>();

	ClassXMLList() throws ClassListReadException {
		try {
			readEntries();
		} catch (IOException e) {
			throw new ClassListReadException(e);
		}
	}

	private void readEntries() throws IOException {
		URL paths = ClassXMLList.class.getResource(CLASS_RESOURCES_LIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(paths.openStream()));
		String fileEntry = reader.readLine();
		while (fileEntry != null) {
			String className = extractName(fileEntry);
			filenameConverter.put(className, "/classxml/" + fileEntry);
			fileEntry = reader.readLine();
		}
	}

	/**
	 * Retrieve a filename from the class name
	 * 
	 * @param classname the class name
	 * @return the file name to retrieve data from
	 */
	String getFileName(final String classname) {
		return filenameConverter.get(classname);
	}

	private String extractName(final String fullName) {
		return fullName.substring(0, fullName.indexOf('_'));
	}
}
