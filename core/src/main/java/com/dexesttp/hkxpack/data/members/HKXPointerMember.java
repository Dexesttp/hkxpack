package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * A {@link HKXMember} referencing another {@link HKXObject}
 */
public class HKXPointerMember implements HKXMember {
	private final String name;
	private final HKXType type;
	private final HKXType subtype;
	private transient String targetObjectName;

	/**
	 * Create a {@link HKXPointerMember}, a link to a given {@link HKXObject}.
	 * @param name the name of the {@link HKXPointerMember}.
	 * @param type the type of the {@link HKXPointerMember}.
	 * Note that the {@link HKXType#getFamily()} function should be {@link HKXTypeFamily#POINTER}, although this isn't checked.
	 * @param subtype the type of the target of the {@link HKXPointerMember}, if described in the classXML.
	 * @param targetObjectName the target object's name.
	 */
	public HKXPointerMember(final String name, final HKXType type, final HKXType subtype, final String targetObjectName) {
		this.name = name;
		this.type = type;
		this.subtype = subtype;
		this.targetObjectName = targetObjectName;
	}

	/**
	 * Set this {@link HKXPointerMember}'s target name.
	 * @param targetObjectName the name of the target object
	 */
	public void set(final String targetObjectName) {
		this.targetObjectName = targetObjectName;
	}

	/**
	 * Get this {@link HKXPointerMember}'s target name.
	 * @return the name of the target object.
	 */
	public String get() {
		return targetObjectName;
	}

	/**
	 * get this {@link HKXPointerMember}'s subtype, if renseigned by the classXML.
	 * @return
	 */
	public HKXType getSubtype() {
		return subtype;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXType getType() {
		return type;
	}
}
