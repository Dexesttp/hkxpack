package com.dexesttp.hkxpack.tagxml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.l10n.SBundle;

class TagXMLDataCreator {
	private final Document document;
	private final TagXMLMemberCreator memberCreator;
	private TagXMLObjectCreator objectCreator;

	TagXMLDataCreator(Document document) {
		this.document = document;
		this.memberCreator = new TagXMLMemberCreator(this);
		this.objectCreator = new TagXMLObjectCreator(this);
	}

	Node create(HKXData content) {
		if(content instanceof HKXObject)
			return objectCreator.create((HKXObject) content);
		if(content instanceof HKXMember)
			return memberCreator.create((HKXMember) content);
		throw new IllegalArgumentException(SBundle.getString("error.tag.create.type.unknown") + "[#060]");
	}

	TagXMLMemberCreator memberCreator() {
		return memberCreator;
	}

	Document document() {
		return document;
	}
}
