package com.dexesttp.hkxpack.hkxreader;

import java.io.IOException;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXFailedMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.member.HKXMemberReader;
import com.dexesttp.hkxpack.hkxreader.member.HKXMemberReaderFactory;

public class HKXObjectReader {

	private HKXMemberReaderFactory memberFactory;
	
	HKXObjectReader(HKXMemberReaderFactory memberFactory) {
		this.memberFactory = memberFactory;
	}

	public HKXObject createHKXObject(String objectName, long position, HKXDescriptor descriptor) {
		HKXObject result = new HKXObject(objectName, descriptor);
		for(HKXMemberTemplate memberTemplate : descriptor.getMemberTemplates()) {
			HKXMember member;
			try {
				HKXMemberReader memberReader = memberFactory.getMemberReader(memberTemplate);
				member = memberReader.read(position);
			} catch (IOException | InvalidPositionException e) {
				member = new HKXFailedMember(memberTemplate.name, memberTemplate.vtype, e.getClass().getName());
				e.printStackTrace();
			}
			result.members().add(member);
		}
		return result;
	}

}
