package com.dexesttp.hkxpack.hkxreader.member;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXObjectReader;

/**
 * Create a {@link HKXObject} from a HKX file, and returns it as
 * {@link HKXMember}.
 */
class HKXObjectMemberReader implements HKXMemberReader {
	private final transient HKXDescriptor descriptor;
	private final transient HKXObjectReader objectReader;
	private final transient String name;
	private final transient long offset;

	HKXObjectMemberReader(final HKXObjectReader objectReader, final String name, final long offset,
			final HKXDescriptor descriptor) {
		this.objectReader = objectReader;
		this.name = name;
		this.offset = offset;
		this.descriptor = descriptor;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember read(final long classOffset) throws InvalidPositionException {
		return objectReader.createHKXObject(name, classOffset + offset, descriptor);
	}
}
