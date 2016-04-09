package com.dexesttp.hkxpack.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A HKXFile contains all data carried by a HKX File in the form of a list of {@link HKXObject}, in a DOM format.
 * <p>
 * Main methods :
 * {@link #getClassVersion()} / {@link #getContentsVersion()} retrieves the version of the file, used as a description parameter for the contents.
 * {@link #add(HKXObject)} / {@link #addAll(HKXObject...)} adds a single or a collection of {@link HKXObject} to the file.
 * {@link #getContentCollection()} retrieves all linked {@link HKXObject}.
 */
public class HKXFile {
	private final transient String contentsversion;
	private final transient int classversion;
	private final transient Collection<HKXObject> content;

	/**
	 * Creates a new {@link HKXFile}.
	 * @param contentsversion the contents version of this {@link HKXFile}.
	 * @param classversion the class version of this {@link HKXFile}.
	 */
	// TODO add ways to select between content/class version with a specific class.
	public HKXFile(final String contentsversion, final int classversion) {
		content = new ArrayList<>();
		this.contentsversion = contentsversion;
		this.classversion = classversion;
	}

	/**
	 * Get this {@link HKXFile}'s contents version.
	 * @return the contents version, as a {@link String}.
	 */
	public String getContentsVersion() {
		return contentsversion;
	}

	/**
	 * Get this {@link HKXFile}'s class version.
	 * @return the class version, as {@link int}.
	 */
	public int getClassVersion() {
		return classversion;
	}

	/**
	 * Retrieves all base {@link HKXObject}
	 * @return
	 */
	public Collection<HKXObject> getContentCollection() {
		return content;
	}

	/**
	 * Add a {@link HKXObject} as a base element of the file.
	 * @param object the {@link HKXObject} to add to the {@link HKXFile}.
	 */
	public void add(final HKXObject object) {
		content.add(object);
	}

	/**
	 * Add a collection of {@link HKXObject} as a base element of the file.
	 * @param hkxObjects the collection of {@link HKXObject} to add.
	 */
	public void addAll(final HKXObject... hkxObjects) {
		for(final HKXObject object : hkxObjects) {
			this.add(object);
		}
	}
}
