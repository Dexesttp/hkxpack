package com.dexesttp.hkxpack.tagreader.serialized;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Handles generation of complex serialized members.
 */
class TagXMLComplexSerializedHandler implements TagXMLSerializedHandler {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember handleMember(final HKXMemberTemplate memberTemplate) throws ClassFileReadException, InvalidTagXMLException {
		return emptyMember(memberTemplate);
	}

	private HKXMember emptyMember(final HKXMemberTemplate memberTemplate) {
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
				break;
			default:
				break;
		}
		return member;
	}
}
