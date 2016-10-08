package com.dexesttp.hkxpack.hkxreader.member.arrays;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.ObjectSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXObjectReader;

/**
 * Reads an {@link HKXObject} from an array.
 */
class HKXObjectArrayContentsReader implements HKXArrayContentsReader {
	private final transient HKXObjectReader reader;
	private final transient HKXDescriptor descriptor;
	private final transient int contentSize;

	HKXObjectArrayContentsReader(final HKXObjectReader reader, final HKXDescriptorFactory descriptorFactory,
			final HKXDescriptor descriptor) throws ClassFileReadException {
		this.reader = reader;
		this.descriptor = descriptor;
		this.contentSize = (int) ObjectSizeResolver.getSize(descriptor, descriptorFactory);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXData getContents(final long arrayStart, final int position) throws InvalidPositionException {
		long contentsPos = arrayStart + position * contentSize;
		return reader.createHKXObject("", contentsPos, descriptor);
	}
}
