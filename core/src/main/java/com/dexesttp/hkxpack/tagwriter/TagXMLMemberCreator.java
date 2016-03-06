package com.dexesttp.hkxpack.tagwriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXEnumMember;
import com.dexesttp.hkxpack.data.members.HKXFailedMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;

/**
 * Creates {@link Node} from {@link HKXMember}<br >
 * Please use {@link TagXMLDataCreator} as the handled class.
 */
class TagXMLMemberCreator {
	private Document document;
	private TagXMLDataCreator dataCreator;
	private TagXMLArrayMemberHandler arrayMemberHandler;

	/**
	 * Creates a new {@link TagXMLMemberCreator} from its parent {@link TagXMLDataCreator}.<br >
	 * This shoud only be done by a {@link TagXMLDataCreator}.
	 * @param tagXMLDataCreator
	 */
	TagXMLMemberCreator(TagXMLDataCreator tagXMLDataCreator) {
		this.dataCreator = tagXMLDataCreator;
		this.document = tagXMLDataCreator.document();
		this.arrayMemberHandler = new TagXMLArrayMemberHandler(dataCreator);
	}

	/**
	 * Creates a {@link Node} from a {@link HKXMember}.<br >
	 * @param member
	 * @return
	 */
	Node create(HKXMember member) {
		if(member instanceof HKXFailedMember) {
			HKXFailedMember failedMember = (HKXFailedMember) member;
			return document.createComment(failedMember.getFailMessage());
		}
		Element memberNode = document.createElement("hkparam");
		memberNode.setAttribute("name", member.getName());
		switch(member.getType().getFamily()) {
			case DIRECT:
			case COMPLEX:
				TagXMLDirectMemberHandler directMemberHandler = new TagXMLDirectMemberHandler();
				String newDirectChild = directMemberHandler.getStringValue((HKXDirectMember<?>) member);
				memberNode.setTextContent(newDirectChild);
				break;
			case ENUM:
				String newEnumChildContent = ((HKXEnumMember) member).get();
				memberNode.setTextContent(newEnumChildContent);
				break;
			case STRING:
				String newStringChildContent = ((HKXStringMember) member).get();
				memberNode.setTextContent(newStringChildContent);
				break;
			case POINTER:
				String newPointerChildContent = ((HKXPointerMember) member).get();
				memberNode.setTextContent(newPointerChildContent);
				break;
			case ARRAY:
				arrayMemberHandler.fillArray(memberNode, (HKXArrayMember) member);
				break;
			case OBJECT:
				Node newObjectNode = dataCreator.create(member);
				memberNode.appendChild(newObjectNode);
				break;
			default:
				break;
		}
		return memberNode;
	}
}
