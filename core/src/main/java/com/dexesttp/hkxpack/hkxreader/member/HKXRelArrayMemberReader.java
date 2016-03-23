package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.dexesttp.hkxpack.data.members.HKXFailedMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.enums.HKXTypeFamily;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class HKXRelArrayMemberReader implements HKXMemberReader {
	private final HKXReaderConnector connector;
	private final String name;
	private final HKXType subtype;
	private long offset;

	public HKXRelArrayMemberReader(HKXReaderConnector connector, String name, HKXType subtype, long offset) {
		this.connector = connector;
		this.name = name;
		this.subtype = subtype;
		this.offset = offset;
	}
	
	@Override
	public HKXMember read(long classOffset) throws IOException, InvalidPositionException {
		System.out.println("//////////");
		System.out.println("Name : " + name);
		System.out.println("Offset : " + offset);
		System.out.println("Subtype : " + subtype);
		// Random idea.
		System.out.println("Probable size : " + 4);
		RandomAccessFile file = connector.data.setup(classOffset + offset);
		byte[] bSize = new byte[2];
		byte[] bOff = new byte[2];
		file.read(bSize);
		file.read(bOff);
		System.out.println("Contents : " + Arrays.toString(bSize) + " // " + Arrays.toString(bOff));
		int mayBeASize = ByteUtils.getInt(bSize);
		System.out.println("Size : " + mayBeASize);
		int mayBeAnOffset = ByteUtils.getInt(bOff);
		System.out.println("Offset (?) : " + mayBeAnOffset);
		int memberSize = 0;
		if(subtype.getFamily() == HKXTypeFamily.OBJECT)
			memberSize = 16;
		else
			memberSize = (int) MemberSizeResolver.getSize(subtype);
		file = connector.data.setup(classOffset + mayBeAnOffset);
		byte[] bRes = new byte[memberSize];
		for(int i = 0; i < mayBeASize; i++) {
			file.read(bRes);
			System.out.println("Found data : " + Arrays.toString(bRes));
		}
		return new HKXFailedMember(name, HKXType.TYPE_RELARRAY, "Can't read RelArrays yet");
	}

}
