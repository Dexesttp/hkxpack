package com.dexesttp.hkxpack.tagreader;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

class TagXMLDirectHandler {
	@SuppressWarnings("unchecked")
	HKXMember handleString(String content, String memberName, HKXType memberType) {
		HKXMember result = null;
		switch(memberType) {
			case TYPE_BOOL:
				result = new HKXDirectMember<Boolean>(memberName, memberType);
				if(content.toUpperCase().equals("TRUE"))
					((HKXDirectMember<Boolean>) result).set(true);
				else
					((HKXDirectMember<Boolean>) result).set(false);
				break;
			case TYPE_CHAR:
			case TYPE_UINT8:
			case TYPE_INT8:
				result = new HKXDirectMember<Character>(memberName, memberType);
				((HKXDirectMember<Character>) result).set((char) Integer.parseInt(content));
				break;
			case TYPE_UINT16:
			case TYPE_ULONG:
			case TYPE_UINT32:
			case TYPE_UINT64:
			case TYPE_INT16:
			case TYPE_INT32:
			case TYPE_INT64:
				result = new HKXDirectMember<Integer>(memberName, memberType);
				((HKXDirectMember<Integer>) result).set(Integer.parseInt(content));
				break;
			case TYPE_HALF:
			case TYPE_REAL:
				result = new HKXDirectMember<Double>(memberName, memberType);
				((HKXDirectMember<Double>) result).set((double) Float.parseFloat(content));
				break;
			default:
				throw new IllegalArgumentException();
				
		}
		return result;
	}
}
