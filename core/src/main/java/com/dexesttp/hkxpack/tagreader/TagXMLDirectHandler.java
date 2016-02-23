package com.dexesttp.hkxpack.tagreader;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.enums.HKXTypeFamily;

/**
 * Handler for String content of {@link HKXTypeFamily#DIRECT} types.
 */
class TagXMLDirectHandler {

	/**
	 * Create a {@link HKXMember} from a {@link String}, its name and its type, providing that {@link HKXType#getFamily()} is {@link HKXTypeFamily#DIRECT}
	 * @param content the {@link String} to transform.
	 * @param memberName the {@link HKXMember} name
	 * @param memberType the {@link HKXTypeFamily#DIRECT} {@link HKXType}
	 * @return the resuting {@link HKXMember}
	 */
	HKXMember handleString(String content, String memberName, HKXType memberType) {
		HKXMember result = null;
		switch(memberType) {
			case TYPE_BOOL:
				result = handleBoolean(content, memberName, memberType);
				break;
			case TYPE_CHAR:
			case TYPE_UINT8:
			case TYPE_INT8:
				result = handleChar(content, memberName, memberType);
				break;
			case TYPE_UINT16:
			case TYPE_ULONG:
			case TYPE_UINT32:
			case TYPE_UINT64:
			case TYPE_INT16:
			case TYPE_INT32:
			case TYPE_INT64:
				result = handleInt(content, memberName, memberType);
				break;
			case TYPE_HALF:
			case TYPE_REAL:
				result = handleDouble(content, memberName, memberType);
				break;
			default:
				throw new IllegalArgumentException();
				
		}
		return result;
	}

	private HKXMember handleBoolean(String content, String memberName, HKXType memberType) {
		HKXDirectMember<Boolean> result = new HKXDirectMember<>(memberName, memberType);
		if(content.toUpperCase().equals("TRUE"))
			result.set(true);
		else
			result.set(false);
		return result;
	}

	private HKXMember handleChar(String content, String memberName, HKXType memberType) {
		HKXDirectMember<Character> result = new HKXDirectMember<>(memberName, memberType);
		result.set((char) Integer.parseInt(content));
		return result;
	}

	private HKXMember handleInt(String content, String memberName, HKXType memberType) {
		HKXDirectMember<Integer> result = new HKXDirectMember<>(memberName, memberType);
		result.set(Integer.parseInt(content));
		return result;
	}

	private HKXMember handleDouble(String content, String memberName, HKXType memberType) {
		HKXDirectMember<Double> result = new HKXDirectMember<>(memberName, memberType);
		result.set((double) Float.parseFloat(content));
		return result;
	}
}
