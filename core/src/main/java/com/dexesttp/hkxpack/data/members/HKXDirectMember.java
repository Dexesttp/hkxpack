package com.dexesttp.hkxpack.data.members;

import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * Stores all basic data found as members of an object.
 * @param <T> the Java type of the stored data. It may be a boxed type, a custom object or an array.
 */
public class HKXDirectMember<T> implements HKXMember {
	private final HKXType type;
	private final String name;
	private transient T value;

	/**
	 * Creates a {@link HKXDirectMember}.
	 * @param name the name of the {@link HKXDirectMember}.
	 * @param type the type of the {@link HKXDirectMember}, as {@link HKXType}.
	 * Note that {@link HKXType#getFamily()} should return either {@link HKXTypeFamily#DIRECT} or {@link HKXTypeFamily#COMPLEX}, although this isn't checked.
	 */
	public HKXDirectMember(final String name, final HKXType type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Set this {@link HKXDirectMember}'s value.
	 * @param value the value to set the member's content to.
	 */
	public void set(final T value) {
		this.value = value;
	}

	/**
	 * Get this {@link HKXDirectMember}'s value.
	 * @return the requested value.
	 */
	public T get() {
		return value;
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
