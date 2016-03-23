package com.dexesttp.hkxpack.hkxreader.member.arrays;

import java.io.IOException;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.ObjectSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXObjectReader;

class HKXObjectArrayContentsReader implements HKXArrayContentsReader {
	private final HKXObjectReader reader;
	private final HKXDescriptor descriptor;
	private final int contentSize;

	HKXObjectArrayContentsReader(HKXObjectReader reader, HKXDescriptorFactory descriptorFactory, HKXDescriptor descriptor) throws ClassFileReadError {
		this.reader = reader;
		this.descriptor = descriptor;
		this.contentSize = (int) ObjectSizeResolver.getSize(descriptor, descriptorFactory);
	}

	@Override
	public long getSize() {
		return contentSize;
	}

	@Override
	public HKXData getContents(long arrayStart, int position) throws IOException, InvalidPositionException {
		long contentsPos = arrayStart + position * contentSize;
		HKXObject data = reader.createHKXObject("", contentsPos, descriptor);
		return data;
	}
}
