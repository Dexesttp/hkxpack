package com.dexesttp.hkxpack.hkxwriter.object.array;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberHandler;
import com.dexesttp.hkxpack.hkxwriter.object.HKXMemberHandlerFactory;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXArrayMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXBaseArrayMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXDefaultArrayMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXObjectArrayMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXPointerArrayMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXRelArrayMemberCallback;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXStringArrayMemberCallback;
import com.dexesttp.hkxpack.resources.byteutils.ByteUtils;

/**
 * Handles an {@link HKXArrayMember} writing to a HKX File based on its type and
 * contents.
 */
public class HKXArrayMemberHandler implements HKXMemberHandler {
	private final transient ByteBuffer outFile;
	private final transient long offset;
	private final transient List<DataInternal> data1;
	private final transient HKXMemberHandlerFactory memberHandlerFactory;

	/**
	 * Creates a {@link HKXArrayMemberHandler}
	 * 
	 * @param outFile                 the {@link ByteBuffer} to write to
	 * @param offset                  the offset of the {@link HKXArrayMember} in
	 *                                its class
	 * @param data1List               the {@link DataInternal} list to write the
	 *                                array reference to, if needed
	 * @param hkxMemberHandlerFactory the {@link HKXMemberHandlerFactory} to use
	 *                                while solving the array's components.
	 */
	public HKXArrayMemberHandler(final ByteBuffer outFile, final long offset, final List<DataInternal> data1List,
			final HKXMemberHandlerFactory hkxMemberHandlerFactory) {
		this.outFile = outFile;
		this.offset = offset;
		this.data1 = data1List;
		this.memberHandlerFactory = hkxMemberHandlerFactory;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMemberCallback write(final HKXMember member, final long currentPos) {
		final HKXArrayMember arrMember = (HKXArrayMember) member;
		int size = arrMember.getContentsList().size();

		HKXArrayMemberCallback arrCallback = null;
		switch (arrMember.getSubType().getFamily()) {
		case POINTER:
			arrCallback = handlePointer(arrMember);
			break;
		case STRING:
			arrCallback = new HKXStringArrayMemberCallback(data1, arrMember, outFile);
			break;
		case OBJECT:
			arrCallback = new HKXObjectArrayMemberCallback(arrMember, memberHandlerFactory);
			break;
		default:
			arrCallback = new HKXDefaultArrayMemberCallback(arrMember, memberHandlerFactory);
			break;
		}

		if (member.getType() == HKXType.TYPE_ARRAY) {
			byte[] sizeVals = ByteUtils.fromULong(size, 4);
			byte[] arrayData = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, sizeVals[0], sizeVals[1], sizeVals[2], sizeVals[3],
					sizeVals[0], sizeVals[1], sizeVals[2], (byte) 0x80 };
			((Buffer) outFile).position((int) (currentPos + offset));
			outFile.put(arrayData);
			if (size == 0) {
				return (memberCallbacks, position) -> {
					return 0;
				};
			}

			final DataInternal arrData = new DataInternal();
			arrData.from = currentPos + offset;
			return new HKXBaseArrayMemberCallback(arrCallback, data1, arrData);
		} else {
			byte[] sizeVals = ByteUtils.fromULong(size + 1, 2);
			((Buffer) outFile).position((int) (currentPos + offset));
			outFile.put(sizeVals);
			return new HKXRelArrayMemberCallback(arrCallback, outFile, currentPos, offset);
		}
	}

	/**
	 * Handles writing a {@link HKXPointerMember}'s array to the file properly, by
	 * deferring most of its definition
	 * 
	 * @param arrMember the {@link HKXArrayMember} that contains all values
	 * @return the relevant {@link HKXArrayMemberCallback}.
	 * @see HKXPointerArrayMemberCallback
	 */
	private HKXArrayMemberCallback handlePointer(final HKXArrayMember arrMember) {
		final List<HKXArrayPointerMemberHandler> apmhList = new ArrayList<>();
		for (HKXData data : arrMember.getContentsList()) {
			if (data instanceof HKXPointerMember) {
				HKXPointerMember internalPointer = (HKXPointerMember) data;
				HKXArrayPointerMemberHandler arrayPointerMemberHandler = memberHandlerFactory.createAPMH();
				arrayPointerMemberHandler.setPointer(internalPointer);
				apmhList.add(arrayPointerMemberHandler);
			}
		}
		return new HKXPointerArrayMemberCallback(arrMember, apmhList);
	}
}
