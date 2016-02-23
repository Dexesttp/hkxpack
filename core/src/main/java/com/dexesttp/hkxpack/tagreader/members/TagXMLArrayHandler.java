package com.dexesttp.hkxpack.tagreader.members;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.TagXMLNodeHandler;

class TagXMLArrayHandler implements TagXMLContentsHandler {
	private final TagXMLNodeHandler nodeHandler;

	TagXMLArrayHandler(TagXMLNodeHandler nodeHandler) {
		this.nodeHandler = nodeHandler;
	}

	HKXMember handleNode(Node objectNode, HKXType vsubtype) {
		// TODO handle array
		return null;
	}

	@Override
	public HKXMember handleNode(Node member, HKXMemberTemplate memberTemplate) {
		return handleNode(member, memberTemplate.vsubtype);
	}
}
