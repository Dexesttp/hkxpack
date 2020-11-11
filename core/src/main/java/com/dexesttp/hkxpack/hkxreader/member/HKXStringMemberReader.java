package com.dexesttp.hkxpack.hkxreader.member;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Reads a {@link HKXStringMember} from a HKX file.
 */
class HKXStringMemberReader implements HKXMemberReader {
	private final transient HKXReaderConnector connector;
	private final transient String name;
	private final transient long memberOffset;
	private final transient HKXType vtype;

	HKXStringMemberReader(final HKXReaderConnector connector, final String name, final HKXType vtype,
			final long offset) {
		this.connector = connector;
		this.name = name;
		this.memberOffset = offset;
		this.vtype = vtype;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember read(final long classOffset) throws InvalidPositionException {
		String contents = "";
		try {
			DataInternal data = connector.data1.readNext();
			if (data.from == memberOffset + classOffset) {
				ByteBuffer file = connector.data.setup(data.to);
				contents = ByteUtils.readString(file);
			} else {
				connector.data1.backtrack();
			}
		} catch (InvalidPositionException e) {
			// NO OP. Met when the last item of the HKX file is a String and is empty.
			contents = "";
		}
		HKXStringMember result = new HKXStringMember(name, vtype);
		result.set(contents);
		return result;
	}
}
