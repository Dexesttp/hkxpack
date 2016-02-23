package com.dexesttp.hkxpack.tagreader.members;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.resources.DOMUtils;
import com.dexesttp.hkxpack.tagreader.TagXMLNodeHandler;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Handle a {@link Node} described by a {@link HKXDescriptor}'s {@link HKXMemberTemplate} into a full {@link HKXMember}.
 */
public class TagXMLMemberHandler {

	private final TagXMLNodeHandler nodeHandler;
	private final TagXMLArrayHandler arrayHandler;
	private final TagXMLDirectHandler directHandler;
	private final TagXMLComplexHandler complexMember;

	public TagXMLMemberHandler(TagXMLNodeHandler tagXMLNodeHandler) {
		this.nodeHandler = tagXMLNodeHandler;
		this.arrayHandler = new TagXMLArrayHandler(nodeHandler);
		this.directHandler = new TagXMLDirectHandler();
		this.complexMember = new TagXMLComplexHandler();
	}

	/**
	 * Creates a {@link HKXMember} from a {@link Node}.
	 * @param objectNode the {@link Node} to read the data from.
	 * @param memberTemplate the {@link Node}'s description, as a {@link HKXMemberTemplate}.
	 * @return the resulting {@link HKXMember}.
	 * @throws InvalidTagXMLException
	 */
	public HKXMember getMember(Node objectNode, HKXMemberTemplate memberTemplate) throws InvalidTagXMLException {
		// Get the right node.
		Node member = getMemberNode(objectNode, memberTemplate.name);
		
		// Get the contents from the node
		switch(memberTemplate.vtype.getFamily()) {
			case DIRECT:
				return directHandler.handleNode(member, memberTemplate);
			case ENUM:
			case STRING:
			case POINTER:
				// Handle string-based
				return null;
			case COMPLEX:
				return complexMember.handleNode(member, memberTemplate);
			case ARRAY:
				return arrayHandler.handleNode(member, memberTemplate);
			case OBJECT:
				// Handle object
				return null;
			default :
				return null;
		}
	}

	private Node getMemberNode(Node objectNode, String name) throws InvalidTagXMLException {
		NodeList children = objectNode.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals("hkparam")) {
				if(DOMUtils.getNodeAttr("name", child).equals(name))
					return child;
			}
		}
		throw new InvalidTagXMLException(SBundle.getString("error.tag.read.member") + name);
	}
}
