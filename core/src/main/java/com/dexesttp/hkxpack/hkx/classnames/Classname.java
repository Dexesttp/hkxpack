package com.dexesttp.hkxpack.hkx.classnames;

/**
 * Represents a ClassName object in the {@literal __classnames__} section of a
 * HKX File.
 */
public class Classname {
	/**
	 * the name of the class
	 */
	public transient String name;
	/**
	 * the UUID of the class.
	 */
	public transient byte[] uuid;

	/**
	 * Create a ClassName either to write it to the file or when it was read from a
	 * file.
	 * 
	 * @param classname the class name.
	 * @param uuid      the class UUID, as defined in the relevant classXML.
	 */
	public Classname(final String classname, final byte[] uuid) {
		this.name = classname;
		this.uuid = uuid.clone();
	}
}
