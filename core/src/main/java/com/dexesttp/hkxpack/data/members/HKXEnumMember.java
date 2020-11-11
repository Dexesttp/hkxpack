package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * Stores an enumeration instance.
 */
public class HKXEnumMember implements HKXMember {
	private final String name;
	private final HKXType type;
	private final HKXType subtype;
	private final String enumerationName;
	private transient String value;

	/**
	 * Creates a new {@link HKXEnumMember}. It should be done via
	 * {@link HKXEnumResolver#resolve(String, int)} or
	 * {@link HKXEnumResolver#resolve(String, String)}.
	 * 
	 * @param name            the name of the enumeration to create.
	 * @param type            the type of the enumeration to create. Note that
	 *                        {@link HKXType#getFamily()} should return
	 *                        {@link HKXTypeFamily#ENUM}, although this is't
	 *                        checked.
	 * @param subtype         the internal type of the enumeration to create.
	 * @param enumerationName the name of the enumeration instantiated by this
	 *                        {@link HKXEnumMember}.
	 */
	public HKXEnumMember(final String name, final HKXType type, final HKXType subtype, final String enumerationName) {
		this.name = name;
		this.type = type;
		this.subtype = subtype;
		this.enumerationName = enumerationName;
	}

	/**
	 * Sets this instance's value. This should be used witht he help of
	 * {@link HKXEnumResolver}.
	 * 
	 * @param value the new enumeration's value.
	 */
	public void set(final String value) {
		this.value = value;
	}

	/**
	 * Get this instance's value.
	 * 
	 * @return
	 */
	public String get() {
		return value;
	}

	/**
	 * Get this {@link HKXEnumMember}'s internal storage type.
	 * 
	 * @return the relevant {@link HKXType}.
	 */
	public HKXType getSubtype() {
		return subtype;
	}

	/**
	 * Get this {@link HKXEnumMember}'s enumeration name.
	 * 
	 * @return
	 */
	public String getEnumerationName() {
		return enumerationName;
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
