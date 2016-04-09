package com.dexesttp.hkxpack.hkxreader.member;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.member.arrays.HKXArrayContentsReader;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Reads a Relative-positionned Array as a {@link HKXArrayMember} from the HKX file.
 * @see HKXArrayContentsReader
 */
public class HKXRelArrayMemberReader implements HKXMemberReader {
	private final transient HKXReaderConnector connector;
	private final transient String name;
	private final transient HKXType subtype;
	private final transient long offset;
	private final transient HKXArrayContentsReader internals;

	HKXRelArrayMemberReader(final HKXReaderConnector connector, final String name, final HKXType subtype,
			final HKXArrayContentsReader arrayContentsReader, final long offset) {
		this.connector = connector;
		this.name = name;
		this.subtype = subtype;
		this.internals = arrayContentsReader;
		this.offset = offset;
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember read(final long classOffset) throws InvalidPositionException {
		ByteBuffer file = connector.data.setup(classOffset + offset);
		byte[] bSize = new byte[2];
		byte[] bOff = new byte[2];
		file.get(bSize);
		file.get(bOff);
		int size = ByteUtils.getUInt(bSize)-1;
		int offset = ByteUtils.getUInt(bOff);
		HKXArrayMember res = new HKXArrayMember(name, HKXType.TYPE_RELARRAY, subtype);
		for(int i = 0; i < size; i++) {
			res.add(internals.getContents(classOffset + offset, i));
		}
		return res;
	}

}
