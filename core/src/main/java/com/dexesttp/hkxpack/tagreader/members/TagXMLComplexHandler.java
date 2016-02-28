package com.dexesttp.hkxpack.tagreader.members;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;

public class TagXMLComplexHandler implements TagXMLContentsHandler {
	private static final String numMatch = "(-?\\d+(?:\\.\\d+)?(?:E-?\\d+)? ?)";
	private static final Pattern pattern3 = Pattern.compile("\\("+numMatch+numMatch+numMatch+"\\)");
	private static final Pattern pattern4 = Pattern.compile("\\("+numMatch+numMatch+numMatch+numMatch+"\\)");
	private static final Pattern patternQS = Pattern.compile(
			  "\\("+numMatch+numMatch+numMatch+"\\)"
			+ "\\("+numMatch+numMatch+numMatch+numMatch+"\\)"
			+ "\\("+numMatch+numMatch+numMatch+"\\)");
	
	@Override
	public HKXMember handleNode(Node member, HKXMemberTemplate memberTemplate) {
		return handleString(member.getTextContent(), memberTemplate.name, memberTemplate.vtype);
	}
	
	HKXMember handleString(String contents, String memberName, HKXType memberType) {
		Pattern pattern = getPattern(memberType);
		Matcher m = pattern.matcher(contents);
		if(!m.find())
			throw new IllegalArgumentException("memberName : " + memberName);
		return handleMatcher(m, memberName, memberType);
	}

	Pattern getPattern(HKXType memberType) {
		switch(memberType) {
			case TYPE_MATRIX3:
				return pattern3;
			case TYPE_VECTOR4:
			case TYPE_TRANSFORM:
			case TYPE_QUATERNION:
				return pattern4;
			case TYPE_QSTRANSFORM:
				return patternQS;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	HKXMember handleMatcher(Matcher m, String memberName, HKXType memberType) {
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
			case 10:
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
					Double.parseDouble(m.group(10))
				});
				break;
		}
		return member;
	}
}
