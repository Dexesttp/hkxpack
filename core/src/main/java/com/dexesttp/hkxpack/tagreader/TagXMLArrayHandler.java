package com.dexesttp.hkxpack.tagreader;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

public class TagXMLArrayHandler {
	private final TagXMLNodeHandler nodeHandler;

	public TagXMLArrayHandler(TagXMLNodeHandler nodeHandler) {
		this.nodeHandler = nodeHandler;
	}

	public HKXMember handleNode(Node objectNode, HKXType vsubtype) {
		// TODO handle array
		return null;
	}

}
