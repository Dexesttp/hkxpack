package com.dexesttp.hkxpack.tagreader.members;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Handles a complex TagXML member.
 */
public class TagXMLComplexHandler implements TagXMLContentsHandler {
	private static final String NUMBER_MATCHER = "(-?\\d+(?:\\.\\d+)?(?:E-?\\d+)? ?)";
	private static final String VECTOR4_MATCHER = "\\("+NUMBER_MATCHER+NUMBER_MATCHER+NUMBER_MATCHER+NUMBER_MATCHER+"\\)";
	private static final Pattern PATTERN_FOR_VEC4 = Pattern.compile(VECTOR4_MATCHER);
	private static final Pattern PATTERN_FOR_QS = Pattern.compile(VECTOR4_MATCHER + VECTOR4_MATCHER + VECTOR4_MATCHER);
	private static final Pattern PATTERN_FOR_M4 = Pattern.compile(VECTOR4_MATCHER + VECTOR4_MATCHER + VECTOR4_MATCHER + VECTOR4_MATCHER);
	
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember handleNode(final Node member, final HKXMemberTemplate memberTemplate) throws InvalidTagXMLException {
		return handleString(member.getTextContent(), memberTemplate.name, memberTemplate.vtype);
	}

	HKXMember handleString(final String contents, final String memberName, final HKXType memberType) {
		Pattern pattern = getPattern(memberType);
		Matcher m = pattern.matcher(contents);
		if(!m.find()) {
			throw new IllegalArgumentException("memberName : " + memberName);
		}
		return handleMatcher(m, memberName, memberType);
	}

	Pattern getPattern(final HKXType memberType) {
		switch(memberType) {
			case TYPE_VECTOR4:
			case TYPE_QUATERNION:
				return PATTERN_FOR_VEC4;
			case TYPE_MATRIX3:
			case TYPE_QSTRANSFORM:
				return PATTERN_FOR_QS;
			case TYPE_MATRIX4:
			case TYPE_TRANSFORM:
				return PATTERN_FOR_M4;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	HKXMember handleMatcher(final Matcher m, final String memberName, final HKXType memberType) {
		HKXDirectMember<Double[]> member = new HKXDirectMember<>(memberName, memberType);
		switch(m.groupCount()) {
			case 3 :
				member.set(new Double[]{
					Double.parseDouble(m.group(1)),
					Double.parseDouble(m.group(2)),
					Double.parseDouble(m.group(3))
				});
				break;
			case 4:
				member.set(new Double[]{
					Double.parseDouble(m.group(1)),
					Double.parseDouble(m.group(2)),
					Double.parseDouble(m.group(3)),
					Double.parseDouble(m.group(4))
				});
				break;
			case 12:
				member.set(new Double[]{
					Double.parseDouble(m.group(1)),
					Double.parseDouble(m.group(2)),
					Double.parseDouble(m.group(3)),
					Double.parseDouble(m.group(4)),
					Double.parseDouble(m.group(5)),
					Double.parseDouble(m.group(6)),
					Double.parseDouble(m.group(7)),
					Double.parseDouble(m.group(8)),
					Double.parseDouble(m.group(9)),
					Double.parseDouble(m.group(10)),
					Double.parseDouble(m.group(11)),
					Double.parseDouble(m.group(12))
				});
				break;
			case 16:
				member.set(new Double[]{
					Double.parseDouble(m.group(1)),
					Double.parseDouble(m.group(2)),
					Double.parseDouble(m.group(3)),
					Double.parseDouble(m.group(4)),
					Double.parseDouble(m.group(5)),
					Double.parseDouble(m.group(6)),
					Double.parseDouble(m.group(7)),
					Double.parseDouble(m.group(8)),
					Double.parseDouble(m.group(9)),
					Double.parseDouble(m.group(10)),
					Double.parseDouble(m.group(11)),
					Double.parseDouble(m.group(12)),
					Double.parseDouble(m.group(13)),
					Double.parseDouble(m.group(14)),
					Double.parseDouble(m.group(15)),
					Double.parseDouble(m.group(16))
				});
				break;
			default:
				break;
		}
		return member;
	}
}
