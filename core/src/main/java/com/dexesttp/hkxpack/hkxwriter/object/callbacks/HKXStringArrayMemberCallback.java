package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.hkx.HKXUtils;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;

/**
 * Handles a {@link HKXStringMember}'s callback while in an array
 */
public class HKXStringArrayMemberCallback implements HKXArrayMemberCallback {
	private final transient List<DataInternal> data1;
	private final transient HKXArrayMember arrMember;
	private final transient ByteBuffer outFile;

	/**
	 * Creates a {@link HKXStringArrayMemberCallback}
	 * @param data1 the {@link DataInternal} list to add String references to.
	 * @param arrMember the parent {@link HKXArrayMember}
	 * @param outFile the {@link ByteBuffer} to output data to.
	 */
	public HKXStringArrayMemberCallback(final List<DataInternal> data1, final HKXArrayMember arrMember, final ByteBuffer outFile) {
		this.data1 = data1;
		this.arrMember = arrMember;
		this.outFile = outFile;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public long process(final List<HKXMemberCallback> memberCallbacks, final long position) {
		long newPos = position;
		long memberSize = MemberSizeResolver.getSize(arrMember.getSubType());
		List<HKXMemberCallback> internalCallbacks = new ArrayList<>();
		for(HKXData data : arrMember.getContentsList()) {
			if(data instanceof HKXMember) {
				HKXMember internalMember = (HKXMember) data;
				internalCallbacks.add(stringHandler((HKXStringMember) internalMember, newPos));
				newPos += memberSize;
			}
		}
		internalCallbacks.add((callbacks, newPosition) -> {return HKXUtils.snapLine(newPosition) - newPosition;});
		memberCallbacks.addAll(0, internalCallbacks);
		return newPos - position;
	}
	
	/**
	 * Handles a {@link HKXStringMember} content's writing to a file.
	 * @param internalMember the {@link HKXStringMember} to write
	 * @param pos the position to write it at
	 * @return the next valid position for a {@link HKXStringMember}.
	 */
	public HKXMemberCallback stringHandler(final HKXStringMember internalMember, final long pos) {
		final DataInternal stringData = new DataInternal();
		stringData.from = pos;
		return (callbacks, position) -> { 
			stringData.to = position;
			data1.add(stringData);
			((Buffer)outFile).position((int) position);
			outFile.put(internalMember.get().getBytes());
			outFile.put((byte) 0x00);
			long outSize = internalMember.get().length() + 1;
			return outSize + ((position + outSize) % 2);
		};
	}
}
