package com.dexesttp.hkxpack.tagreader;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexesttp.hkxpack.data.members.HKXFailedMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.enums.Flag;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.resources.DOMUtils;
import com.dexesttp.hkxpack.resources.LoggerUtil;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;
import com.dexesttp.hkxpack.tagreader.members.TagXMLContentsHandler;
import com.dexesttp.hkxpack.tagreader.members.TagXMLContentsHandlerFactory;
import com.dexesttp.hkxpack.tagreader.serialized.TagXMLSerializedHandler;
import com.dexesttp.hkxpack.tagreader.serialized.TagXMLSerializedHandlerFactory;

/**
 * Handle a {@link Node} described by a {@link HKXDescriptor}'s
 * {@link HKXMemberTemplate} into a full {@link HKXMember}.
 */
class TagXMLMemberHandler {
	private final transient TagXMLContentsHandlerFactory contentsFactory;
	private final transient TagXMLSerializedHandlerFactory serializedHandlerFactory;

	/**
	 * Creates a {@link TagXMLMemberHandler}.
	 * 
	 * @param tagXMLNodeHandler the node handler to use.
	 * @param descriptorFactory the descriptor factory to use.
	 */
	public TagXMLMemberHandler(final TagXMLNodeHandler tagXMLNodeHandler,
			final HKXDescriptorFactory descriptorFactory) {
		TagXMLNodeHandler nodeHandler = tagXMLNodeHandler;
		this.contentsFactory = new TagXMLContentsHandlerFactory(nodeHandler);
		this.serializedHandlerFactory = new TagXMLSerializedHandlerFactory(descriptorFactory);
	}

	/**
	 * Creates a {@link HKXMember} from a {@link Node}.
	 * 
	 * @param objectNode     the {@link Node} to read the data from.
	 * @param memberTemplate the {@link Node}'s description, as a
	 *                       {@link HKXMemberTemplate}.
	 * @return the resulting {@link HKXMember}.
	 * @throws InvalidTagXMLException if there was an error in the given TagXML.
	 * @throws ClassFileReadException if there was an error retrieving a ClassFile.
	 */
	HKXMember getMember(final Node objectNode, final HKXMemberTemplate memberTemplate)
			throws InvalidTagXMLException, ClassFileReadException {
		// Get the right node.
		Node member = getMemberNode(objectNode, memberTemplate.name);
		if (member == null) {
			if (memberTemplate.flag == Flag.SERIALIZE_IGNORED) {
				TagXMLSerializedHandler serializedHandler = serializedHandlerFactory
						.getSerializedHandler(memberTemplate.vtype);
				return serializedHandler.handleMember(memberTemplate);
			} else {
				LoggerUtil.add(
						new InvalidTagXMLException(SBundle.getString("error.tag.read.member") + memberTemplate.name));
				return new HKXFailedMember(memberTemplate.name, memberTemplate.vtype,
						SBundle.getString("error.tag.read.member") + memberTemplate.name);
			}
		} else {
			// Get the right handler
			TagXMLContentsHandler handler = contentsFactory.getHandler(memberTemplate.vtype);
			// Get the contents from the node
			return handler.handleNode(member, memberTemplate);
		}
	}

	private Node getMemberNode(final Node objectNode, final String name) throws InvalidTagXMLException {
		NodeList children = objectNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeName().equals("hkparam") && DOMUtils.getNodeAttr("name", child).equals(name)) {
				return child;
			}
		}
		return null;
	}
}
