package com.dexesttp.hkxpack.hkxreader;
 

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXFailedMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkxreader.member.HKXMemberReader;
import com.dexesttp.hkxpack.hkxreader.member.HKXMemberReaderFactory;
import com.dexesttp.hkxpack.resources.LoggerUtil;

public class HKXObjectReader {

	private HKXMemberReaderFactory memberFactory;
	
	/**
	 * Initialize the HKXObjectReader
	 * @param memberReaderFactory the {@link HKXMemberReaderFactory} used to create the {@link HKXObject}'s {@link HKXMember}s {@link HKXMemberReader}.
	 */
	HKXObjectReader(HKXMemberReaderFactory memberReaderFactory) {
		this.memberFactory = memberReaderFactory;
	}

	/**
	 * Creates an HKXObject from a descriptor, a position and the object's name.
	 * @param objectName the name of the object to create.
	 * @param position the position to read the object from.
	 * @param descriptor a descriptor of the {@link HKXObject}'s internal structure.
	 * @return the read {@link HKXObject}
	 */
	public HKXObject createHKXObject(String objectName, long position, HKXDescriptor descriptor) {
		HKXObject result = new HKXObject(objectName, descriptor);
		for(HKXMemberTemplate memberTemplate : descriptor.getMemberTemplates()) {
			HKXMember member;
			try {
				HKXMemberReader memberReader = memberFactory.getMemberReader(memberTemplate);
				member = memberReader.read(position);
			} catch (ClassFileReadError | InvalidPositionException e) {
				member = new HKXFailedMember(memberTemplate.name, memberTemplate.vtype, e.getClass().getName());
				LoggerUtil.add(e);
			}
			result.members().add(member);
		}
		return result;
	}

}
