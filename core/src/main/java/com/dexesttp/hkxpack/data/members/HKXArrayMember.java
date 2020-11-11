package com.dexesttp.hkxpack.data.members;

import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * The DOM equivalent for all data arrays defined in {@link HKXType}.
 */
public class HKXArrayMember implements HKXMember {
	private final String name;
	private final HKXType type;
	private final transient HKXType subtype;
	private final transient List<HKXData> contents;

	/**
	 * Creates a new {@link HKXArrayMember}.
	 * 
	 * @param name    the name of the member.
	 * @param type    the type of the member. the {@link HKXType#getFamily()} method
	 *                of this type should return {@link HKXTypeFamily#ARRAY}, but
	 *                this isn't required.
	 * @param subtype the type of this array's content.
	 */
	public HKXArrayMember(final String name, final HKXType type, final HKXType subtype) {
		this.name = name;
		this.type = type;
		this.subtype = subtype;
		this.contents = new ArrayList<>();
	}

	/**
	 * Retrieves this array's intended content type.
	 * 
	 * @return this array's content type, as a {@link HKXType}.
	 */
	public HKXType getSubType() {
		return subtype;
	}

	/**
	 * Add a HKXData at the end of this array.
	 * 
	 * @param data the {@link HKXData} to add.
	 * @throws IllegalArgumentException if the given data isn't of the right type.
	 */
	public void add(final HKXData data) {
		if (subtype != HKXType.TYPE_NONE && data.getType() != subtype) {
			throw new IllegalArgumentException(
					"Array data type is defined as : " + subtype + " while the given argument is " + data.getType());
		}
		this.contents.add(data);
	}

	/**
	 * Get the n-th HKXData of this array.
	 * 
	 * @param position the position of the data to retrieve.
	 * @return the relevant {@link HKXData}.
	 */
	public HKXData get(final int position) {
		return contents.get(position);
	}

	/**
	 * Retrieve the contents of the array, as a list of {@link HKXData}.
	 * 
	 * @return the array's contents.
	 */
	public List<HKXData> getContentsList() {
		return this.contents;
	}

	@Override
	/**
	 * @{@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	@Override
	/**
	 * @{@inheritDoc}
	 */
	public HKXType getType() {
		return type;
	}
}
