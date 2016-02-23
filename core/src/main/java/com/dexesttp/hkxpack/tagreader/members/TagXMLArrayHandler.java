package com.dexesttp.hkxpack.tagreader.members;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.TagXMLNodeHandler;

class TagXMLArrayHandler implements TagXMLContentsHandler {
	private final TagXMLNodeHandler nodeHandler;

	TagXMLArrayHandler(TagXMLNodeHandler nodeHandler) {
		this.nodeHandler = nodeHandler;
	}

	@Override
	public HKXMember handleNode(Node member, HKXMemberTemplate memberTemplate) {
		HKXArrayMember result = new HKXArrayMember(memberTemplate.name, memberTemplate.vtype, memberTemplate.vsubtype);
		return result;
	}
}
