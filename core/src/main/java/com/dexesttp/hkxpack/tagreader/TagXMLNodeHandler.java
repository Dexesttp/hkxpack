package com.dexesttp.hkxpack.tagreader;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.resources.DOMUtils;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;
import com.dexesttp.hkxpack.tagreader.members.TagXMLMemberHandler;

public class TagXMLNodeHandler {
	private final TagXMLMemberHandler memberHandler;
	private final TagXMLObjectHandler objectHandler;

	TagXMLNodeHandler(HKXDescriptorFactory descriptorFactory) {
		this.memberHandler = new TagXMLMemberHandler(this);
		this.objectHandler = new TagXMLObjectHandler(descriptorFactory, memberHandler);
	}

	public HKXObject handleObject(Node objectNode) throws ClassFileReadError, InvalidTagXMLException {
		// Retrieve descriptor
		String className = DOMUtils.getNodeAttr("class", objectNode);
		return objectHandler.handleObject(objectNode, className);
	}
	
	public HKXObject handleSubObject(Node objectNode, String className) throws ClassFileReadError, InvalidTagXMLException {
		return objectHandler.handleObject(objectNode, className);
	}
}
