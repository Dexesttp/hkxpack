package com.dexesttp.hkxpack.hkxreader.member;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.dexesttp.hkxpack.data.members.HKXFailedMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkx.types.ObjectSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;

public class HKXRelArrayMemberReader implements HKXMemberReader {
	private final HKXReaderConnector connector;
	private final String name;
	private final HKXType subtype;
	private long offset;
	private int subsize;

	public HKXRelArrayMemberReader(HKXReaderConnector connector, String name, HKXType subtype, long offset) {
		this.connector = connector;
		this.name = name;
		this.subtype = subtype;
		this.offset = offset;
		this.subsize = 4;
	}
	
	@Override
	public HKXMember read(long classOffset) throws IOException, InvalidPositionException {
		System.out.println("//////////");
		System.out.println("Name : " + name);
		System.out.println("Offset : " + offset);
		System.out.println("Subtype : " + subtype);
		// Random idea.
		System.out.println("Probable size : " + subsize);
		RandomAccessFile file = connector.data.setup(classOffset + offset);
		byte[] b = new byte[subsize];
		file.read(b);System.out.println("Contents : " + Arrays.toString(b));
		Data1Interface data1 = connector.data1;
		DataInternal arrValue = data1.readNext();
		if(arrValue.from == classOffset + offset) {
			System.out.println("Found contents linked to : " + arrValue.to);
		} else {
			System.out.println("No contents found.");
			data1.backtrack();
		}
		return new HKXFailedMember(name, HKXType.TYPE_RELARRAY, "Can't read RelArrays yet");
	}

}
