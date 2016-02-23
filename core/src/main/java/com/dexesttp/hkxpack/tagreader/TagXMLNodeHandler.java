package com.dexesttp.hkxpack.tagreader;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;
import com.dexesttp.hkxpack.tagreader.members.TagXMLMemberHandler;

public class TagXMLNodeHandler {
	private final TagXMLMemberHandler memberHandler;
	private final TagXMLObjectHandler objectHandler;

	TagXMLNodeHandler(HKXDescriptorFactory descriptorFactory) {
		this.memberHandler = new TagXMLMemberHandler(this);
		this.objectHandler = new TagXMLObjectHandler(descriptorFactory, memberHandler);
	}

	HKXObject handleObject(Node objectNode) throws ClassFileReadError, InvalidTagXMLException {
		return objectHandler.handleObject(objectNode);
	}
}
