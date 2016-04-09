package com.dexesttp.hkxpack.tagreader.members;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.tagreader.TagXMLNodeHandler;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Handles reading an embedded {@link HKXObject} form a TagXML file.
 */
public class TagXMLEmbeddedObjectHandler implements TagXMLContentsHandler {
	private final transient TagXMLNodeHandler nodeHandler;

	/**
	 * Create a {@link TagXMLEmbeddedObjectHandler}.
	 * @param nodeHandler the {@link TagXMLNodeHandler} to use while reading this object's members.
	 */
	public TagXMLEmbeddedObjectHandler(final TagXMLNodeHandler nodeHandler) {
		this.nodeHandler = nodeHandler;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public HKXMember handleNode(final Node member, final HKXMemberTemplate memberTemplate) throws ClassFileReadException, InvalidTagXMLException {
		String target = memberTemplate.target;
		NodeList children = member.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node objectNode = children.item(i);
			if(objectNode.getNodeName().equals("hkobject")) {
				return handleNode(objectNode, target);
			}
		}
		throw new InvalidTagXMLException(SBundle.getString("error.tag.read.member") + memberTemplate.name);
	}

	HKXMember handleNode(final Node member, final String target) throws ClassFileReadException, InvalidTagXMLException {
		return nodeHandler.handleSubObject(member, target);
	}
}
