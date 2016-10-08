package com.dexesttp.hkxpack.tagreader;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.resources.DOMUtils;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Handles general node data retrieval, discrimining between root {@link HKXObject}, embedded {@link HKXObject} and {@link HKXMember}s.
 */
public class TagXMLNodeHandler {
	private final transient TagXMLObjectHandler objectHandler;

	TagXMLNodeHandler(final HKXDescriptorFactory descriptorFactory) {
		TagXMLMemberHandler memberHandler = new TagXMLMemberHandler(this, descriptorFactory);
		this.objectHandler = new TagXMLObjectHandler(descriptorFactory, memberHandler);
	}

	/**
	 * Handles an object {@link Node} into a {@link HKXObject}.
	 * @param objectNode the {@link Node} to handle.
	 * @return the relevant {@link HKXObject}.
	 * @throws ClassFileReadException if there was a problem reading the Class data from the program's resources.
	 * @throws InvalidTagXMLException if there was an error parsing the TagXML file.
	 */
	HKXObject handleObject(final Node objectNode) throws ClassFileReadException, InvalidTagXMLException {
		// Retrieve descriptor
		String className = DOMUtils.getNodeAttr("class", objectNode);
		return objectHandler.handleObject(objectNode, className);
	}
	
	/**
	 * Handles a subobject (an object with no name nor class, but the className is given).
	 * @param objectNode the {@link Node} to read the object from.
	 * @param className the class name of the object.
	 * @return the relevant {@link HKXObject}.
	 * @throws ClassFileReadException if there was a problem reading the Class data from the program's resources.
	 * @throws InvalidTagXMLException if there was an error parsing the TagXML file.
	 */
	public HKXObject handleSubObject(final Node objectNode, final String className) throws ClassFileReadException, InvalidTagXMLException {
		return objectHandler.handleObject(objectNode, className);
	}
}
