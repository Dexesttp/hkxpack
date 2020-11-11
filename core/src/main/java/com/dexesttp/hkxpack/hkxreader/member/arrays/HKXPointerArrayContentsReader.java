package com.dexesttp.hkxpack.hkxreader.member.arrays;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.PointerNameGenerator;

/**
 * Reads a {@link HKXPointerMember} from an array.
 */
public class HKXPointerArrayContentsReader implements HKXArrayContentsReader {
	private final transient PointerNameGenerator generator;
	private final transient HKXReaderConnector connector;

	HKXPointerArrayContentsReader(final HKXReaderConnector connector, final PointerNameGenerator generator) {
		this.connector = connector;
		this.generator = generator;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXData getContents(final long arrayStart, final int position) throws InvalidPositionException {
		long contentsPosition = arrayStart + position * 0x08;
		DataExternal data = connector.data2.readNext();
		String target = "null";
		if (data.from == contentsPosition) {
			target = generator.get(data.to);
		} else {
			connector.data2.backtrack();
		}
		return new HKXPointerMember("", HKXType.TYPE_POINTER, HKXType.TYPE_NONE, target);
	}
}
