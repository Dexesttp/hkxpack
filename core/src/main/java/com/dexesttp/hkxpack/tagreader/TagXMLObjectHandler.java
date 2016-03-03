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

/**
 * Handles a {@link Node} into a {@link HKXObject}.
 */
class TagXMLObjectHandler {
	private final HKXDescriptorFactory descriptorFactory;
	private final TagXMLMemberHandler memberHandler;

	/**
	 * Creates a {@link TagXMLObjectHandler}.
	 * @param descriptorFactory the {@link HKXDescriptorFactory} used to retrieve a hkclass descriptor.
	 * @param memberHandler the {@link TagXMLMemberHandler} used while parsing members.
	 */
	TagXMLObjectHandler(HKXDescriptorFactory descriptorFactory, TagXMLMemberHandler memberHandler) {
		this.descriptorFactory = descriptorFactory;
		this.memberHandler = memberHandler;
	}

	/**
	 * Handle an object {@link Node} into a {@link HKXObject}, given its classname.
	 * @param objectNode the {@link Node} to read the data from.
	 * @param className the {@link HKXObject}'s class name.
	 * @return the relevant {@link HKXObject}.
	 * @throws ClassFileReadError if there was a problem reading the Class data from the program's resources.
	 * @throws InvalidTagXMLException if there was an error parsing the TagXML file.
	 */
	HKXObject handleObject(Node objectNode, String className) throws ClassFileReadError, InvalidTagXMLException {
		HKXDescriptor classDescriptor = descriptorFactory.get(className);
		// Create object 
		String objectName = objectNode == null ? "" : DOMUtils.getNodeAttr("name", objectNode);
		HKXObject result = new HKXObject(objectName, classDescriptor);
		
		if(objectNode == null)
			return result;
		
		// Fill object
		for(HKXMemberTemplate memberTemplate : classDescriptor.getMemberTemplates()) {
			HKXMember member = memberHandler.getMember(objectNode, memberTemplate);
			result.members().add(member);
		}
		
		return result;
	}
}
