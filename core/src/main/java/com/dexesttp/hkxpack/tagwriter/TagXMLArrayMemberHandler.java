package com.dexesttp.hkxpack.tagwriter;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;

/**
 * Handles the conversion between a {@link HKXArrayMember} and its contents as an {@link Element}.
 */
class TagXMLArrayMemberHandler {
	private final transient TagXMLDataCreator dataCreator;
	private static final int MAX_LENGTH_PER_LINE = 64;

	TagXMLArrayMemberHandler(final TagXMLDataCreator dataCreator) {
		this.dataCreator = dataCreator;
	}
	
	/**
	 * Fills the given {@link Element} with the {@link HKXArrayMember}'s contents.
	 * @param memberNode the {@link Element} to fill.
	 * @param member the {@link HKXArrayMember} to get the data from.
	 */
	void fillArray(final Element memberNode, final HKXArrayMember member) {
		memberNode.setAttribute("numelements", Integer.toString(((HKXArrayMember) member).getContentsList().size()));
		switch(member.getSubType().getFamily()) {
			case DIRECT:
			case COMPLEX:
			case ENUM:
				handleEnumOutType(memberNode, member);
				break;
			case STRING:
				handleStringOutType(memberNode, member);
				break;
			case POINTER:
				handlePtrOutType(memberNode, member);
				break;
			default:
				handleNodeOutType(memberNode, member);
				break;
		}
	}

	private void handleStringOutType(final Element memberNode, final HKXArrayMember member) {
		for(HKXData data : member.getContentsList()) {
			HKXStringMember subMember = (HKXStringMember) data;
			String subMemberString = subMember.get();
			Element stringNode = dataCreator.getDocument().createElement("hkcstring");
			stringNode.setTextContent(subMemberString);
			memberNode.appendChild(stringNode);
		}
	}

	private void handleNodeOutType(final Element memberNode, final HKXArrayMember member) {
		for(HKXData data : member.getContentsList()) {
			Node newChild = dataCreator.create(data);
			memberNode.appendChild(newChild);
		}
	}

	private void handleEnumOutType(final Element memberNode, final HKXArrayMember member) {
		TagXMLDirectMemberHandler directMemberHandler = new TagXMLDirectMemberHandler();
		StringBuffer accu = new StringBuffer();
		StringBuffer contents = new StringBuffer();
		for(HKXData data : member.getContentsList()) {
			HKXDirectMember<?> subMember = (HKXDirectMember<?>) data;
			String subMemberString = directMemberHandler.getStringValue(subMember);
			if((contents + subMemberString).length() > MAX_LENGTH_PER_LINE ) {
				if(contents.length() == 0) {
					accu.append('\n');
				}
				else {
					accu.append('\n').append(contents.substring(0, contents.length() - 1));
				}
				contents.setLength(0);
			}
			contents.append(subMemberString).append(' ');
		}
		if(contents.length() != 0) {
			if(accu.length() != 0) {
				accu.append('\n');
			}
			accu.append(contents.substring(0, contents.length() - 1));
		}
		memberNode.setTextContent(accu.toString());
	}


	private void handlePtrOutType(final Element memberNode, final HKXArrayMember member) {
		StringBuffer accu = new StringBuffer();
		accu.append('\n');
		for(HKXData data : member.getContentsList()) {
			HKXPointerMember subMember = (HKXPointerMember) data;
			String subMemberString = subMember.get();
			accu.append(subMemberString).append('\n');
		}
		memberNode.setTextContent(accu.toString());
	}
}
