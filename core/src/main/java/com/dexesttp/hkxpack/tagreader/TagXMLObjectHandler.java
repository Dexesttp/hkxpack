package com.dexesttp.hkxpack.tagreader;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.resources.DOMUtils;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Handles a {@link Node} into a {@link HKXObject}.
 */
class TagXMLObjectHandler {
	private final transient HKXDescriptorFactory descriptorFactory;
	private final transient TagXMLMemberHandler memberHandler;

	/**
	 * Creates a {@link TagXMLObjectHandler}.
	 * @param descriptorFactory the {@link HKXDescriptorFactory} used to retrieve a hkclass descriptor.
	 * @param memberHandler the {@link TagXMLMemberHandler} used while parsing members.
	 */
	TagXMLObjectHandler(final HKXDescriptorFactory descriptorFactory, final TagXMLMemberHandler memberHandler) {
		this.descriptorFactory = descriptorFactory;
		this.memberHandler = memberHandler;
	}

	/**
	 * Handle an object {@link Node} into a {@link HKXObject}, given its classname.
	 * @param objectNode the {@link Node} to read the data from.
	 * @param className the {@link HKXObject}'s class name.
	 * @return the relevant {@link HKXObject}.
	 * @throws ClassFileReadException if there was a problem reading the Class data from the program's resources.
	 * @throws InvalidTagXMLException if there was an error parsing the TagXML file.
	 */
	HKXObject handleObject(final Node objectNode, final String className) throws ClassFileReadException, InvalidTagXMLException {
		HKXDescriptor classDescriptor = descriptorFactory.get(className);
		// Create object 
		String objectName = DOMUtils.getNodeAttr("name", objectNode);
		HKXObject result = new HKXObject(objectName, classDescriptor);
		
		// Fill object
		for(HKXMemberTemplate memberTemplate : classDescriptor.getMemberTemplates()) {
			HKXMember member = memberHandler.getMember(objectNode, memberTemplate);
			result.getMembersList().add(member);
		}
		
		return result;
	}
}
