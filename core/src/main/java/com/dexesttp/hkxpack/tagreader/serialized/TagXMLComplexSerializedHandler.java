package com.dexesttp.hkxpack.tagreader.serialized;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

class TagXMLComplexSerializedHandler implements TagXMLSerializedHandler {

	@Override
	public HKXMember handleMember(HKXMemberTemplate memberTemplate) throws ClassFileReadError, InvalidTagXMLException {
		return emptyMember(memberTemplate);
	}

	private HKXMember emptyMember(HKXMemberTemplate memberTemplate) {
		HKXDirectMember<Double[]> member = new HKXDirectMember<>(memberTemplate.name, memberTemplate.vtype);
		switch(memberTemplate.vtype) {
			case TYPE_VECTOR4:
			case TYPE_QUATERNION:
				member.set(new Double[]{0., 0., 0., 0.});
				break;
			case TYPE_MATRIX3:
			case TYPE_QSTRANSFORM:
				member.set(new Double[]{0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.});
				break;
			case TYPE_MATRIX4:
			case TYPE_TRANSFORM:
				member.set(new Double[]{0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.});
			default:
				break;
		}
		return member;
	}
}
