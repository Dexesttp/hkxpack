package com.dexesttp.hkxpack.hkxwriter.object;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.hkx.HKXUtils;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;

/**
 * Handles a {@link HKXStringMember} for output to a HKX file.
 * 
 * @see HKXMemberHandler
 */
public class HKXStringMemberHandler implements HKXMemberHandler {
	private final transient ByteBuffer outFile;
	private final transient long offset;
	private final transient List<DataInternal> data1;

	/**
	 * Creates a {@link HKXStringMemberHandler}.
	 * 
	 * @param outFile   the file to output to
	 * @param offset    the member's offset in the object
	 * @param data1List the list of internal pointers to put the {@link String}'s
	 *                  location reference in.
	 */
	public HKXStringMemberHandler(final ByteBuffer outFile, final long offset, final List<DataInternal> data1List) {
		this.outFile = outFile;
		this.offset = offset;
		this.data1 = data1List;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMemberCallback write(final HKXMember member, final long currentPos) {
		final HKXStringMember strMember = (HKXStringMember) member;
		if (strMember.get() == null || strMember.get().isEmpty()) {
			return (callbacks, position) -> {
				return 0;
			};
		}
		final DataInternal stringData = new DataInternal();
		stringData.from = currentPos + offset;
		return (callbacks, position) -> {
			stringData.to = position;
			data1.add(stringData);
			((Buffer) outFile).position((int) position);
			outFile.put(strMember.get().getBytes());
			outFile.put((byte) 0x00);
			return HKXUtils.snapString(position + strMember.get().length() + 1) - position;
		};
	}

}
