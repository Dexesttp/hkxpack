package com.dexesttp.hkxpack.tagxml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;

class TagXMLObjectCreator {
	
	private Document document;
	private TagXMLMemberCreator memberCreator;

	TagXMLObjectCreator(TagXMLDataCreator tagXMLDataCreator) {
		this.document = tagXMLDataCreator.document();
		this.memberCreator = tagXMLDataCreator.memberCreator();
	}
	
	Node create(HKXObject object) {
		Element res = document.createElement("hkxobject");
		// Create base class node.
		res.setAttribute("class", object.getDescriptor().getName());
		res.setAttribute("name", object.getName());
		res.setAttribute("signature","0x" + Long.toHexString(object.getDescriptor().getSignature()));
		for(HKXMember member : object.members()) {
			Node memberNode = memberCreator.create(member);
			res.appendChild(memberNode);
		}
		return res;
	}
}
