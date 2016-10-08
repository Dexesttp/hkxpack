package com.dexesttp.hkxpack.hkxreader.member;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXEnumMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Reads an enumeration as a {@link HKXEnumMember} from a HKX file.
 */
public class HKXEnumMemberReader implements HKXMemberReader {
	private final transient HKXReaderConnector connector;
	private final transient HKXEnumResolver enumResolver;
	private final transient String name;
	private final transient HKXType vtype;
	private final transient HKXType vsubtype;
	private final transient String etype;
	private final transient long memberOffset;

	HKXEnumMemberReader(final HKXReaderConnector connector, final HKXEnumResolver enumResolver,
			final String name, final HKXType vtype, final HKXType vsubtype, final String target,
			final long offset) {
		this.connector = connector;
		this.enumResolver = enumResolver;
		this.name = name;
		this.vtype = vtype;
		this.vsubtype = vsubtype;
		this.etype = target;
		this.memberOffset = offset;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember read(final long classOffset) throws InvalidPositionException {
		final int memberSize = (int) MemberSizeResolver.getSize(vsubtype);
		ByteBuffer file = connector.data.setup(classOffset + memberOffset);
		byte[] bytesToRead = new byte[memberSize];
		file.get(bytesToRead);
		int contents = ByteUtils.getUInt(bytesToRead);
		HKXEnumMember result = new HKXEnumMember(name, vtype, vsubtype, etype);
		result.set(enumResolver.resolve(etype, contents));
		return result;
	}
}
