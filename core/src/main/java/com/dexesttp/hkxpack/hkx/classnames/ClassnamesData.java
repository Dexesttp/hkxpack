package com.dexesttp.hkxpack.hkx.classnames;

import java.util.LinkedHashMap;

/**
 * Stores all the ClassNames from a file or intended to be stored in a file.
 */
public class ClassnamesData extends LinkedHashMap<Long, Classname> {
	private static final long serialVersionUID = 5525421716171216039L;
	
	/**
	 * Add a classname to this ClassnamesData
	 * @param position the position (in {@link bytes}) the classname whould be found at.
	 * @param name the name of the classname to add.
	 * @param uuid the UUID of the classname to add.
	 * @return the added {@link Classname} object.
	 */
	public Classname put(final long position, final String name, final byte[] uuid){
		return super.put(position, new Classname(name, uuid));
	}

	/**
	 * Returns {@link true} if the {@link ClassnamesData} contains the given classname.
	 * @param name the classname to check the existence of.
	 * @return the existence of the classname.
	 */
	public boolean containsClass(final String name) {
		for(Classname classname : this.values()) {
			if(classname.name.equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retrieves the position of the given classname, or {@literal 0x05} if no position was found.
	 * @param name the name to check against.
	 * @return the classname's position in the {@literal __classnames__} section
	 */
	public long getPosition(final String name) {
		for(java.util.Map.Entry<Long, Classname> entries : this.entrySet()) {
			if(entries.getValue().name.equals(name)) {
				return entries.getKey();
			}
		}
		return 0x05;
	}
}
