package com.dexesttp.hkxpack.tagreader.serialized;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Handles embedded {@link HKXObject} members in TagXML files.
 */
class TagXMLEmbeddedObjectSerializedHandler implements TagXMLSerializedHandler {
	private final transient HKXDescriptorFactory descriptorFactory;
	private final transient TagXMLSerializedHandlerFactory serializedHandlerFactory;

	TagXMLEmbeddedObjectSerializedHandler(final TagXMLSerializedHandlerFactory tagXMLSerializedHandlerFactory,
			final HKXDescriptorFactory descriptorFactory) {
		this.serializedHandlerFactory = tagXMLSerializedHandlerFactory;
		this.descriptorFactory = descriptorFactory;

	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember handleMember(final HKXMemberTemplate objectTemplate)
			throws ClassFileReadException, InvalidTagXMLException {
		HKXDescriptor classDescriptor = descriptorFactory.get(objectTemplate.target);
		// Create object
		HKXObject result = new HKXObject("", classDescriptor);

		// Fill object
		for (HKXMemberTemplate memberTemplate : classDescriptor.getMemberTemplates()) {
			TagXMLSerializedHandler memberHandler = serializedHandlerFactory.getSerializedHandler(memberTemplate.vtype);
			result.getMembersList().add(memberHandler.handleMember(memberTemplate));
		}

		return result;
	}

}
