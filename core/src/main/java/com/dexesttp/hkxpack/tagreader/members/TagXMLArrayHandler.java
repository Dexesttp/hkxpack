package com.dexesttp.hkxpack.tagreader.members;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.TagXMLNodeHandler;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Handles reading a {@link HKXArrayMember} from a TagXML file.
 */
class TagXMLArrayHandler implements TagXMLContentsHandler {
	private static final Pattern SIMPLE_PATTERN = Pattern.compile("(\\S+)(?:\\s|\\z)");
	private final transient TagXMLEmbeddedObjectHandler objectHandler;
	private final transient TagXMLDirectHandler directHandler;
	private final transient TagXMLComplexHandler complexHandler;

	TagXMLArrayHandler(final TagXMLNodeHandler nodeHandler) {
		this.objectHandler = new TagXMLEmbeddedObjectHandler(nodeHandler);
		this.directHandler = new TagXMLDirectHandler();
		this.complexHandler = new TagXMLComplexHandler();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember handleNode(final Node member, final HKXMemberTemplate memberTemplate) throws ClassFileReadException, InvalidTagXMLException {
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
			case POINTER:
				handlePointer(result, member, memberTemplate);
				break;
			case OBJECT:
				handleObject(result, member, memberTemplate.target);
				break;
			default:
				break;
		}
		return result;
	}

	private void handleDirect(final HKXArrayMember root, final Node member, final HKXType subtype) {
		Matcher m = SIMPLE_PATTERN.matcher(member.getTextContent());
		while(m.find()) {
			HKXMember contents = directHandler.handleString(m.group(1), "", subtype);
			root.add(contents);
		}
	}

	private void handleComplex(final HKXArrayMember root, final Node member, final HKXType subtype) {
		Pattern pattern = complexHandler.getPattern(subtype);
		Matcher m = pattern.matcher(member.getTextContent());
		while(m.find()) {
			HKXMember contents = complexHandler.handleMatcher(m, "", subtype);
			root.add(contents);
		}
	}

	private void handleString(final HKXArrayMember root, final Node member) {
		NodeList children = member.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals("hkcstring")) {
				HKXStringMember string = createStringMember(root);
				string.set(child.getTextContent());
				root.add(string);
			}
		}
	}

	private void handlePointer(final HKXArrayMember root, final Node member, final HKXMemberTemplate memberTemplate) {
		Matcher m = SIMPLE_PATTERN.matcher(member.getTextContent());
		while(m.find()) {
			HKXPointerMember contents = createPointerMember(memberTemplate);
			contents.set(m.group(1));
			root.add(contents);
		}
	}

	private void handleObject(final HKXArrayMember root, final Node member, final String target) throws ClassFileReadException, InvalidTagXMLException {
		NodeList children = member.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals("hkobject")) {
				HKXMember subObject = objectHandler.handleNode(child, target);
				root.add(subObject);
			}
		}
	}

	private HKXPointerMember createPointerMember(final HKXMemberTemplate memberTemplate) {
		return new HKXPointerMember("", memberTemplate.vsubtype, HKXType.TYPE_VOID, memberTemplate.target);
	}

	private HKXStringMember createStringMember(final HKXArrayMember root) {
		return new HKXStringMember("", root.getSubType());
	}
}
