package com.dexesttp.hkxpack.tagreader.members;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;

public class TagXMLComplexHandler implements TagXMLContentsHandler {
	private static final Pattern pattern3 = Pattern.compile("\\((-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?)\\)");
	private static final Pattern pattern4 = Pattern.compile("\\((-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?)\\)");
	private static final Pattern patternQS = Pattern.compile("\\((-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?)\\)" + 
				"\\((-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?)\\)" + 
				"\\((-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?) (-?\\d+(?:\\.\\d+)?)\\)");
	
	@Override
	public HKXMember handleNode(Node member, HKXMemberTemplate memberTemplate) {
		return handleString(member.getTextContent(), memberTemplate.name, memberTemplate.vtype);
	}

	int getSpaceNumbers(HKXType type) {
		switch(type) {
			case TYPE_MATRIX3:
				return 2;
			case TYPE_VECTOR4:
			case TYPE_TRANSFORM:
			case TYPE_QUATERNION:
				return 3;
			case TYPE_QSTRANSFORM:
				return 7;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	HKXMember handleString(String contents, String memberName, HKXType memberType) {
		switch(memberType) {
			case TYPE_MATRIX3:
				return handle3Sized(contents, memberName, memberType);
			case TYPE_VECTOR4:
			case TYPE_TRANSFORM:
			case TYPE_QUATERNION:
				return handle4Sized(contents, memberName, memberType);
			case TYPE_QSTRANSFORM:
				return handleQSTransform(contents, memberName);
			default:
				throw new IllegalArgumentException();
		}
	}

	private HKXMember handle3Sized(String contents, String memberName, HKXType memberType) {
		Matcher m = pattern3.matcher(contents);
		if(!m.find())
			throw new IllegalArgumentException();
		HKXDirectMember<Double[]> member = new HKXDirectMember<>(memberName, memberType);
		member.set(new Double[]{
				Double.parseDouble(m.group(1)),
				Double.parseDouble(m.group(2)),
				Double.parseDouble(m.group(3))
		});
		return member;
	}

	private HKXMember handle4Sized(String contents, String memberName, HKXType memberType) {
		Matcher m = pattern4.matcher(contents);
		if(!m.find())
			throw new IllegalArgumentException();
		HKXDirectMember<Double[]> member = new HKXDirectMember<>(memberName, memberType);
		member.set(new Double[]{
				Double.parseDouble(m.group(1)),
				Double.parseDouble(m.group(2)),
				Double.parseDouble(m.group(3)),
				Double.parseDouble(m.group(4))
		});
		return member;
	}

	private HKXMember handleQSTransform(String contents, String memberName) {
		Matcher m = patternQS.matcher(contents);
		if(!m.find())
			throw new IllegalArgumentException();
		HKXDirectMember<Double[]> member = new HKXDirectMember<>(memberName, HKXType.TYPE_QSTRANSFORM);
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
		return member;
	}
}
