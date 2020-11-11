package com.dexesttp.hkxpack.hkxreader.member;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.member.arrays.HKXArrayContentsReader;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Reads a classic (v8) array from a HKX file.
 * 
 * @see HKXArrayContentsReader
 */
public class HKXArrayMemberReader implements HKXMemberReader {
	private final transient HKXReaderConnector connector;
	private final transient String name;
	private final transient HKXType subtype;
	private final transient HKXArrayContentsReader internals;
	private final transient long memberOffset;

	HKXArrayMemberReader(final HKXReaderConnector connector, final String name, final HKXType subtype,
			final HKXArrayContentsReader internals, final long offset) {
		this.connector = connector;
		this.name = name;
		this.subtype = subtype;
		this.internals = internals;
		this.memberOffset = offset;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember read(final long classOffset) throws InvalidPositionException {
		final int memberSize = (int) MemberSizeResolver.getSize(HKXType.TYPE_ARRAY);
		ByteBuffer file = connector.data.setup(classOffset + memberOffset);
		byte[] baseArrayBytes = new byte[memberSize];
		file.get(baseArrayBytes);
		HKXArrayMember result = new HKXArrayMember(name, HKXType.TYPE_ARRAY, subtype);
		int arrSize = getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + memberOffset;
			for (int i = 0; i < arrSize; i++) {
				HKXData data = internals.getContents(arrValue.to, i);
				result.add(data);
			}
		}
		return result;
	}

	private int getSizeComponent(final byte[] arrayBaseBytes) {
		byte[] sizeSpecificBytes = new byte[] { arrayBaseBytes[8], arrayBaseBytes[9], arrayBaseBytes[10],
				arrayBaseBytes[11] };
		return ByteUtils.getUInt(sizeSpecificBytes);
	}
}
