package com.dexesttp.hkxpack.hkxwriter.object.array;

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

public class HKXArrayMemberHandler implements HKXMemberHandler {
	private final ByteBuffer outFile;
	private final long offset;
	private final List<DataInternal> data1;
	private final HKXMemberHandlerFactory memberHandlerFactory;

	public HKXArrayMemberHandler(ByteBuffer outFile, long offset,
			List<DataInternal> data1List, HKXMemberHandlerFactory hkxMemberHandlerFactory) {
		this.outFile = outFile;
		this.offset = offset;
		this.data1 = data1List;
		this.memberHandlerFactory = hkxMemberHandlerFactory;
	}

	@Override
	public HKXMemberCallback write(HKXMember member, long currentPos) {
		final HKXArrayMember arrMember = (HKXArrayMember) member;
		int size = arrMember.contents().size();
		
		
		HKXArrayMemberCallback arrCallback = null;
		switch(arrMember.getSubType().getFamily()) {
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
		
		if(member.getType() == HKXType.TYPE_ARRAY) {
			byte[] sizeVals = ByteUtils.fromULong(size, 4);
			byte[] arrayData = new byte[]{
					0, 0, 0, 0,  0, 0, 0, 0,
					sizeVals[0], sizeVals[1], sizeVals[2], sizeVals[3],
					sizeVals[0], sizeVals[1], sizeVals[2], (byte) 0x80
			};
			outFile.position((int) (currentPos + offset));
			outFile.put(arrayData);
			if(size == 0)
				return (memberCallbacks, position) -> { return 0; };
			
			final DataInternal arrData = new DataInternal();
			arrData.from = currentPos + offset;
			return new HKXBaseArrayMemberCallback(arrCallback, data1, arrData);
		}
		else {
			byte[] sizeVals = ByteUtils.fromULong(size + 1, 2);
			outFile.position((int) (currentPos + offset));
			outFile.put(sizeVals);
			return new HKXRelArrayMemberCallback(arrCallback, outFile, currentPos, offset);
		}
	}

	private HKXArrayMemberCallback handlePointer(final HKXArrayMember arrMember) {
		final List<HKXArrayPointerMemberHandler> apmhList = new ArrayList<>(); 
		for(HKXData data : arrMember.contents()) {
			if(data instanceof HKXPointerMember) {
				HKXPointerMember internalPointer = (HKXPointerMember) data;
				HKXArrayPointerMemberHandler arrayPointerMemberHandler = memberHandlerFactory.createAPMH();
				arrayPointerMemberHandler.setPointer(internalPointer);
				apmhList.add(arrayPointerMemberHandler);
			}
		}
		return new HKXPointerArrayMemberCallback(arrMember, apmhList);
	}
}
