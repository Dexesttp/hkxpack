package com.dexesttp.hkxpack.tagreader.serialized;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

class TagXMLDirectSerializedHandler implements TagXMLSerializedHandler {

	@Override
	public HKXMember handleMember(HKXMemberTemplate memberTemplate) throws ClassFileReadError, InvalidTagXMLException {
		return emptyMember(memberTemplate);
	}

	@SuppressWarnings("unchecked")
	private HKXMember emptyMember(HKXMemberTemplate memberTemplate) {
		HKXMember result = null;
		switch(memberTemplate.vtype) {
			case TYPE_BOOL:
				result = new HKXDirectMember<Boolean>(memberTemplate.name, memberTemplate.vtype);
				((HKXDirectMember<Boolean>) result).set(new Boolean(false));
				break;
			case TYPE_CHAR:
			case TYPE_UINT8:
			case TYPE_INT8:
				result = new HKXDirectMember<Character>(memberTemplate.name, memberTemplate.vtype);
				((HKXDirectMember<Character>) result).set(new Character((char) 0));
				break;
			case TYPE_UINT16:
			case TYPE_ULONG:
			case TYPE_UINT32:
			case TYPE_UINT64:
			case TYPE_INT16:
			case TYPE_INT32:
			case TYPE_INT64:
				result = new HKXDirectMember<Integer>(memberTemplate.name, memberTemplate.vtype);
				((HKXDirectMember<Integer>) result).set(new Integer(0));
				break;
			case TYPE_HALF:
			case TYPE_REAL:
				result = new HKXDirectMember<Double>(memberTemplate.name, memberTemplate.vtype);
				((HKXDirectMember<Double>) result).set(new Double(0));
			default:
				break;
		}
		return result;
	}
}
