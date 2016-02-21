package com.dexesttp.hkxpack.tagxml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;

/**
 * Handles the conversion between a {@link HKXArrayMember} and its contents as an {@link Element}.
 */
class TagXMLArrayMemberHandler {
	private final TagXMLDataCreator dataCreator;

	TagXMLArrayMemberHandler(TagXMLDataCreator dataCreator) {
		this.dataCreator = dataCreator;
	}
	
	/**
	 * Fills the given {@link Element} with the {@link HKXArrayMember}'s contents.
	 * @param memberNode the {@link Element} to fill.
	 * @param member the {@link HKXArrayMember} to get the data from.
	 */
	void fillArray(Element memberNode, HKXArrayMember member) {
		memberNode.setAttribute("numelements", "" + ((HKXArrayMember) member).contents().size());
		for(HKXData data : member.contents()) {
			Node newChild = dataCreator.create(data);
			memberNode.appendChild(newChild);
		}
	}

}
