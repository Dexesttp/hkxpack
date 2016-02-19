package com.dexesttp.hkxpack.hkxreader;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;

class HKXDescriptorReader {
	private final HKXObjectReader creator;
	private final PointerNameGenerator generator;

	public HKXDescriptorReader(HKXObjectReader creator, PointerNameGenerator generator) {
		this.creator = creator;
		this.generator = generator;
	}

	public HKXObject read(long position, HKXDescriptor descriptor) {
		String objectName = generator.get(position);
		return creator.createHKXObject(objectName, position, descriptor);
	}
}
