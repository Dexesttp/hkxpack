package com.dexesttp.hkxpack.hkxreader.member.arrays;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberDataResolver;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;

/**
 * Reads a {@link HKXDirectMember} from an array.
 */
class HKXDirectArrayContentsReader implements HKXArrayContentsReader {
	private final transient HKXReaderConnector connector;
	private final transient HKXType contentType;

	HKXDirectArrayContentsReader(final HKXReaderConnector connector, final HKXType contentType) {
		this.connector = connector;
		this.contentType = contentType;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXData getContents(final long arrayStart, final int position) throws InvalidPositionException {
		final int contentSize = (int) MemberSizeResolver.getSize(contentType);
		byte[] bytesToRead = new byte[contentSize];
		ByteBuffer file = connector.data.setup(arrayStart + position * contentSize);
		file.get(bytesToRead);
		return MemberDataResolver.getMember("", contentType, bytesToRead);
	}
}
