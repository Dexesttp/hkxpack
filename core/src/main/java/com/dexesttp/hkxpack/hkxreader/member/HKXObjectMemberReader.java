package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXObjectReader;

class HKXObjectMemberReader implements HKXMemberReader {
	private final HKXDescriptor descriptor;
	private final HKXObjectReader objectReader;
	private final String name;
	private final long offset;

	HKXObjectMemberReader(HKXObjectReader objectReader, String name, long offset, HKXDescriptor descriptor) {
		this.objectReader = objectReader;
		this.name = name;
		this.offset = offset;
		this.descriptor = descriptor;
	}

	@Override
	public HKXMember read(long classOffset) throws IOException, InvalidPositionException {
		HKXObject res = objectReader.createHKXObject(name, classOffset + offset, descriptor);
		return res;
	}
}
