package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class HKXArrayMemberHandler implements HKXMemberHandler {
	private final RandomAccessFile outFile;
	private final long offset;
	private final List<DataInternal> data1;
	private final HKXMemberHandlerFactory memberHandlerFactory;

	public HKXArrayMemberHandler(RandomAccessFile outFile, long offset,
			List<DataInternal> data1List, HKXMemberHandlerFactory hkxMemberHandlerFactory) {
		this.outFile = outFile;
		this.offset = offset;
		this.data1 = data1List;
		this.memberHandlerFactory = hkxMemberHandlerFactory;
	}

	@Override
	public HKXMemberCallback write(HKXMember member, long currentPos) throws IOException {
		final HKXArrayMember arrMember = (HKXArrayMember) member;
		int size = arrMember.contents().size();
		byte[] sizeVals = ByteUtils.fromLong(size, 4);
		byte[] arrayData = new byte[]{
				0, 0, 0, 0,  0, 0, 0, 0,
				sizeVals[0], sizeVals[1], sizeVals[2], sizeVals[3],
				sizeVals[0], sizeVals[1], sizeVals[2], (byte) 0x80
		};
		outFile.seek(currentPos + offset);
		outFile.write(arrayData);
		if(size == 0)
			return (memberCallbacks, position) -> { return 0; };
		
		final DataInternal arrData = new DataInternal();
		arrData.from = currentPos + offset;
		
		switch(arrMember.getSubType().getFamily()) {
			case OBJECT:
				return (memberCallbacks, position) -> {
					arrData.to = position;
					data1.add(arrData);
					long newPos = position;
					List<HKXMemberCallback> internalCallbacks = new ArrayList<>();
					for(HKXData data : arrMember.contents()) {
						if(data instanceof HKXObject) {
							HKXObject internalObject = (HKXObject) data;
							long objectSize = MemberSizeResolver.getSize(internalObject.getDescriptor());
							HKXMemberHandler memberHandler = new HKXObjectMemberHandler(0, memberHandlerFactory.clone(internalCallbacks), internalCallbacks);
							internalCallbacks.add(memberHandler.write(internalObject, newPos));
							newPos += objectSize;
						}
					}
					memberCallbacks.addAll(0, internalCallbacks);
					return newPos - position;
				};
			default:
				return (memberCallbacks, position) -> {
					arrData.to = position;
					data1.add(arrData);
					long newPos = position;
					long memberSize = MemberSizeResolver.getSize(arrMember.getSubType());
					for(HKXData data : arrMember.contents()) {
						if(data instanceof HKXMember) {
							HKXMember internalMember = (HKXMember) data;
							HKXMemberHandler memberHandler = memberHandlerFactory.create(internalMember.getType(), 0, "");
							memberCallbacks.add(memberHandler.write(internalMember, newPos));
							newPos += memberSize;
						}
					}
					return newPos - position;
				};
		}
	}
}
