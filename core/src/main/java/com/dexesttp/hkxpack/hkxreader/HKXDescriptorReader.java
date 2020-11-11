package com.dexesttp.hkxpack.hkxreader;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;

/**
 * Reads an {@link HKXObject} based on its {@link HKXDescriptor}.
 */
class HKXDescriptorReader {
	private final transient HKXObjectReader creator;
	private final transient PointerNameGenerator generator;

	/**
	 * Create a new HKXDescriptorReader
	 * 
	 * @param creator   the {@link HKXObjectReader} to use while creating the
	 *                  {@link HKXObject}.
	 * @param generator the {@link PointerNameGenerator} to generate names from.
	 */
	HKXDescriptorReader(final HKXObjectReader creator, final PointerNameGenerator generator) {
		this.creator = creator;
		this.generator = generator;
	}

	/**
	 * Read an HKXObject from the file.
	 * 
	 * @param position   the position the {@link HKXObject} shoud be read from.
	 * @param descriptor the {@link HKXDescriptor} describing the {@link HKXObject}
	 *                   to read.
	 * @return an {@link HKXObject} containing all the read contents.
	 */
	HKXObject read(final long position, final HKXDescriptor descriptor) {
		String objectName = generator.get(position);
		return creator.createHKXObject(objectName, position, descriptor);
	}
}
