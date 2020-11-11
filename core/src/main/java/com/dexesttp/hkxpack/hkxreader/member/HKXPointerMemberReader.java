package com.dexesttp.hkxpack.hkxreader.member;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.PointerNameGenerator;

/**
 * Reads a {@link HKXPointerMember} from a HKX file.
 */
class HKXPointerMemberReader implements HKXMemberReader {
	private final transient HKXReaderConnector connector;
	private final transient PointerNameGenerator generator;
	private final transient String name;
	private final transient HKXType vtype;
	private final transient long memberOffset;

	HKXPointerMemberReader(final HKXReaderConnector connector, final PointerNameGenerator generator, final String name,
			final HKXType contentType, final long offset) {
		this.connector = connector;
		this.generator = generator;
		this.name = name;
		this.vtype = contentType;
		this.memberOffset = offset;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember read(final long classOffset) throws InvalidPositionException {
		DataExternal data = connector.data2.readNext();
		String target = "null";
		if (data.from == memberOffset + classOffset) {
			target = generator.get(data.to);
		} else {
			connector.data2.backtrack();
		}
		return new HKXPointerMember(name, HKXType.TYPE_POINTER, vtype, target);
	}
}
