package com.dexesttp.hkxpack.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A HKXFile contains all data carried by a HKX File in the form of a list of {@link HKXObject}, in a OOP format.
 * <p>
 * Main methods :
 * {@link #getClassVersion()} / {@link #getContentsVersion()} retrieves the version of the file, used as a description parameter for the contents.
 * {@link #add(HKXObject)} / {@link #addAll(HKXObject...)} adds a single or a collection of {@link HKXObject} to the file.
 * {@link #content()} retrieves all linked {@link HKXObject}.
 */
public class HKXFile {
	private final String contentsversion;
	private final int classversion;
	private final Collection<HKXObject> content;

	public HKXFile(String contentsversion, int classversion) {
		content = new ArrayList<>();
		this.contentsversion = contentsversion;
		this.classversion = classversion;
	}

	public String getContentsVersion() {
		return contentsversion;
	}

	public int getClassVersion() {
		return classversion;
	}

	/**
	 * Retrieves all base {@link HKXObject}
	 * @return
	 */
	public Collection<HKXObject> content() {
		return content;
	}

	/**
	 * Add a {@link HKXObject} as a base element of the file.
	 * @param object
	 */
	public void add(HKXObject object) {
		content.add(object);
	}

	/**
	 * Add a collection of {@link HKXObject} as a base element of the file.
	 * @param hkxObjects
	 */
	public void addAll(HKXObject... hkxObjects) {
		for(HKXObject object : hkxObjects) {
			add(object);
		}
	}
}
