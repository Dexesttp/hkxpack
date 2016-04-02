package com.dexesttp.hkxpack.tagreader.serialized;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

class TagXMLEmbeddedObjectSerializedHandler implements TagXMLSerializedHandler {
	private final HKXDescriptorFactory descriptorFactory;
	private final TagXMLSerializedHandlerFactory serializedHandlerFactory;

	TagXMLEmbeddedObjectSerializedHandler(TagXMLSerializedHandlerFactory tagXMLSerializedHandlerFactory, HKXDescriptorFactory descriptorFactory) {
		this.serializedHandlerFactory = tagXMLSerializedHandlerFactory;
		this.descriptorFactory = descriptorFactory;
		
	}

	@Override
	public HKXMember handleMember(HKXMemberTemplate objectTemplate) throws ClassFileReadException, InvalidTagXMLException {
		HKXDescriptor classDescriptor = descriptorFactory.get(objectTemplate.target);
		// Create object 
		HKXObject result = new HKXObject("", classDescriptor);	
		
		// Fill object
		for(HKXMemberTemplate memberTemplate : classDescriptor.getMemberTemplates()) {
			TagXMLSerializedHandler memberHandler = serializedHandlerFactory.getSerializedHandler(memberTemplate.vtype);
			result.getMembersList().add(memberHandler.handleMember(memberTemplate));
		}
		
		return result;
	}

}
