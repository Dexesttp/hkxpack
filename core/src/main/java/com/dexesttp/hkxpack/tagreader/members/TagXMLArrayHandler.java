package com.dexesttp.hkxpack.tagreader.members;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.TagXMLNodeHandler;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

class TagXMLArrayHandler implements TagXMLContentsHandler {
	private final TagXMLEmbeddedObjectHandler objectHandler;
	private final TagXMLDirectHandler directHandler;
	private final TagXMLComplexHandler complexHandler;

	TagXMLArrayHandler(TagXMLNodeHandler nodeHandler) {
		this.objectHandler = new TagXMLEmbeddedObjectHandler(nodeHandler);
		this.directHandler = new TagXMLDirectHandler();
		this.complexHandler = new TagXMLComplexHandler();
	}

	@Override
	public HKXMember handleNode(Node member, HKXMemberTemplate memberTemplate) throws ClassFileReadError, InvalidTagXMLException {
		HKXArrayMember result = new HKXArrayMember(memberTemplate.name, memberTemplate.vtype, memberTemplate.vsubtype);
		// Change behavior based on internal content type's family
		switch (memberTemplate.vsubtype.getFamily()) {
			case DIRECT:
				handleDirect(result, member, memberTemplate.vsubtype);
				break;
			case COMPLEX:
				handleComplex(result, member, memberTemplate.vsubtype);
				break;
			case STRING:
				handleString(result, member);
				break;
			case OBJECT:
				handleObject(result, member, memberTemplate.target);
				break;
			default:
				break;
		}
		return result;
	}

	private void handleDirect(HKXArrayMember root, Node member, HKXType subtype) {
		Pattern pattern = Pattern.compile("(\\S+)[\\s|$]");
		Matcher m = pattern.matcher(member.getTextContent());
		while(m.find()) {
			HKXMember contents = directHandler.handleString(m.group(1), "", subtype);
			root.add(contents);
		}
	}

	private void handleComplex(HKXArrayMember root, Node member, HKXType subtype) {
		Pattern pattern = complexHandler.getPattern(subtype);
		Matcher m = pattern.matcher(member.getTextContent());
		while(m.find()) {
			HKXMember contents = complexHandler.handleMatcher(m, "", subtype);
			root.add(contents);
		}
	}

	private void handleString(HKXArrayMember result, Node member) {
		NodeList children = member.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals("cstring")) {
				HKXStringMember string = new HKXStringMember("", HKXType.TYPE_CSTRING);
				string.set(child.getTextContent());
				result.add(string);
			}
		}
	}

	private void handleObject(HKXArrayMember result, Node member, String target) throws ClassFileReadError, InvalidTagXMLException {
		NodeList children = member.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals("hkobject")) {
				HKXMember subObject = objectHandler.handleNode(child, target);
				result.add(subObject);
			}
		}
	}
}
