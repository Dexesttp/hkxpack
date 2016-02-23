package com.dexesttp.hkxpack.tagreader;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.resources.DOMUtils;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;
import com.dexesttp.hkxpack.tagreader.members.TagXMLMemberHandler;

class TagXMLObjectHandler {
	private final HKXDescriptorFactory descriptorFactory;
	private final TagXMLMemberHandler memberHandler;

	TagXMLObjectHandler(HKXDescriptorFactory descriptorFactory, TagXMLMemberHandler memberHandler) {
		this.descriptorFactory = descriptorFactory;
		this.memberHandler = memberHandler;
	}

	HKXObject handleObject(Node objectNode, String className) throws ClassFileReadError, InvalidTagXMLException {
		HKXDescriptor classDescriptor = descriptorFactory.get(className);
		// Create object 
		String objectName = DOMUtils.getNodeAttr("name", objectNode);
		HKXObject result = new HKXObject(objectName, classDescriptor);
		
		// Fill object
		for(HKXMemberTemplate memberTemplate : classDescriptor.getMemberTemplates()) {
			HKXMember member = memberHandler.getMember(objectNode, memberTemplate);
			result.members().add(member);
		}
		
		return result;
	}
}
