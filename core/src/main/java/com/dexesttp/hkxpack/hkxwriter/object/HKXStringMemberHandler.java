package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.hkx.HKXUtils;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkxwriter.object.callbacks.HKXMemberCallback;

public class HKXStringMemberHandler implements HKXMemberHandler {
	private final RandomAccessFile outFile;
	private final long offset;
	private final List<DataInternal> data1;

	public HKXStringMemberHandler(RandomAccessFile outFile, long offset, List<DataInternal> data1List) {
		this.outFile = outFile;
		this.offset = offset;
		this.data1 = data1List;
	}

	@Override
	public HKXMemberCallback write(HKXMember member, long currentPos) throws IOException {
		final HKXStringMember strMember = (HKXStringMember) member;
		if(strMember.get().isEmpty())
			return (callbacks, position) -> {return 0;};
		final DataInternal stringData = new DataInternal();
		stringData.from = currentPos + offset;
		return (callbacks, position) -> { 
			stringData.to = position;
			data1.add(stringData);
			outFile.seek(position);
			outFile.writeBytes(strMember.get());
			outFile.writeByte(0x00);
			return HKXUtils.snapString(position + strMember.get().length() + 1) - position;
		};
	}

}
