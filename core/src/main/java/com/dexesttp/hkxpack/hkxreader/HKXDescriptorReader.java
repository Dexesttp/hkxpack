package com.dexesttp.hkxpack.hkxreader;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;

class HKXDescriptorReader {
	private final HKXObjectReader creator;
	private final PointerNameGenerator generator;

	/**
	 * Create a new HKXDescriptorReader
	 * @param creator
	 * @param generator
	 */
	HKXDescriptorReader(HKXObjectReader creator, PointerNameGenerator generator) {
		this.creator = creator;
		this.generator = generator;
	}

	/**
	 * Read an HKXObject from the file.
	 * @param position the position the {@link HKXObject} shoud be read from.
	 * @param descriptor the {@link HKXDescriptor} describing the {@link HKXObject} to read.
	 * @return an {@link HKXObject} containing all the read contents.
	 */
	HKXObject read(long position, HKXDescriptor descriptor) {
		String objectName = generator.get(position);
		return creator.createHKXObject(objectName, position, descriptor);
	}
}
