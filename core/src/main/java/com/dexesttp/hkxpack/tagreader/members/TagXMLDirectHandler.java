package com.dexesttp.hkxpack.tagreader.members;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Handler for {@link Node} content of {@link HKXTypeFamily#DIRECT} types.
 */
class TagXMLDirectHandler implements TagXMLContentsHandler {
	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember handleNode(final Node member, final HKXMemberTemplate memberTemplate)
			throws InvalidTagXMLException {
		return handleString(member.getTextContent(), memberTemplate.name, memberTemplate.vtype);
	}

	/**
	 * Create a {@link HKXMember} from a {@link String}, its name and its type,
	 * providing that {@link HKXType#getFamily()} is {@link HKXTypeFamily#DIRECT}
	 * 
	 * @param content    the {@link String} to transform.
	 * @param memberName the {@link HKXMember} name
	 * @param memberType the {@link HKXTypeFamily#DIRECT} {@link HKXType}
	 * @return the resuting {@link HKXMember}
	 */
	HKXMember handleString(final String content, final String memberName, final HKXType memberType) {
		HKXMember result = null;
		switch (memberType) {
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

	private HKXMember handleBoolean(final String content, final String memberName, final HKXType memberType) {
		HKXDirectMember<Boolean> result = new HKXDirectMember<>(memberName, memberType);
		if (content.equalsIgnoreCase("TRUE")) {
			result.set(true);
		} else {
			result.set(false);
		}
		return result;
	}

	private HKXMember handleChar(final String content, final String memberName, final HKXType memberType) {
		HKXDirectMember<Character> result = new HKXDirectMember<>(memberName, memberType);
		result.set((char) Integer.parseInt(content));
		return result;
	}

	private HKXMember handleInt(final String content, final String memberName, final HKXType memberType) {
		HKXDirectMember<Integer> result = new HKXDirectMember<>(memberName, memberType);
		result.set(Integer.parseInt(content));
		return result;
	}

	private HKXMember handleDouble(final String content, final String memberName, final HKXType memberType) {
		HKXDirectMember<Double> result = new HKXDirectMember<>(memberName, memberType);
		result.set((double) Float.parseFloat(content));
		return result;
	}
}
