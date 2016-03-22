package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.dexesttp.hkxpack.data.members.HKXFailedMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
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
		byte[] b = new byte[4];
		file.read(b);
		System.out.println("Contents : " + Arrays.toString(b));
		int mayBeAFlag = b[0];
		System.out.print("Flag (?) : ");
		for(int i = 0; i < 8; i++) {
			System.out.print(""+mayBeAFlag % 2);
			mayBeAFlag >>= 1;
		}
		System.out.println("");
		byte[] bOff = new byte[] {b[2], b[3]};
		int mayBeAnOffset = ByteUtils.getInt(bOff);
		System.out.println("Offset (?) : " + mayBeAnOffset);
		byte[] bRes = new byte[16];
		file = connector.data.setup(classOffset + mayBeAnOffset);
		file.read(bRes);
		System.out.println("Found data : " + Arrays.toString(bRes));
		return new HKXFailedMember(name, HKXType.TYPE_RELARRAY, "Can't read RelArrays yet");
	}

}
