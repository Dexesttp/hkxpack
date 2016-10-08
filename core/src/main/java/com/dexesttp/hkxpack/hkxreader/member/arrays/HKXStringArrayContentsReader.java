package com.dexesttp.hkxpack.hkxreader.member.arrays;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Reads a {@link HKXStringMember} from an array
 */
class HKXStringArrayContentsReader implements HKXArrayContentsReader {
	private final transient HKXReaderConnector connector;
	private final transient HKXType contentsType;

	HKXStringArrayContentsReader(final HKXReaderConnector connector, final HKXType contentsType) {
		this.connector = connector;
		this.contentsType = contentsType;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXData getContents(final long arrayStart, final int position) throws InvalidPositionException {
		long descriptorPosition = arrayStart + position * 0x08;
		DataInternal data = connector.data1.readNext();
		String contents = "";
		if(data.from == descriptorPosition) {
			ByteBuffer file = connector.data.setup(data.to);
			contents = ByteUtils.readString(file);
		} else {
			connector.data1.backtrack();
		}
		HKXStringMember result = new HKXStringMember("", contentsType);
		result.set(contents);
		return result;
	}

}
