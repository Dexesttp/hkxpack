package com.dexesttp.hkxpack.hkxreader.member;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberDataResolver;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;

/**
 * Reads a direct or complex member from a HKX file.
 * @see MemberDataResolver
 */
class HKXDirectMemberReader implements HKXMemberReader {
	private final transient HKXReaderConnector connector;
	private final transient String name;
	private final transient HKXType vtype;
	private final transient long memberOffset;

	HKXDirectMemberReader(final HKXReaderConnector connector, final String name,
			final HKXType contentType, final long offset) {
		this.connector = connector;
		this.name = name;
		this.vtype = contentType;
		this.memberOffset = offset;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember read(final long classOffset) throws InvalidPositionException {
		final int memberSize = (int) MemberSizeResolver.getSize(vtype);
		ByteBuffer file = connector.data.setup(classOffset + memberOffset);
		byte[] bytesToRead = new byte[memberSize];
		file.get(bytesToRead);
		return MemberDataResolver.getMember(name, vtype, bytesToRead);
	}

}
