package com.dexesttp.hkxpack.tagreader.members;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
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
	 * @throws InvalidTagXMLException if there was an error in the given TagXML.
	 * @throws ClassFileReadError if there was an error retrieving a ClassFile.
	 */
	public HKXMember getMember(Node objectNode, HKXMemberTemplate memberTemplate) throws InvalidTagXMLException, ClassFileReadError {
		// Get the right node.
		Node member = getMemberNode(objectNode, memberTemplate.name);
		
		// Get the contents from the node
		switch(memberTemplate.vtype.getFamily()) {
			case DIRECT:
				return directHandler.handleNode(member, memberTemplate);
			case ENUM:
				HKXDirectMember<String> enumMember = new HKXDirectMember<>(memberTemplate.name, memberTemplate.vtype);
				enumMember.set(member.getTextContent());
				return enumMember;
			case STRING:
				HKXStringMember stringMember = new HKXStringMember(memberTemplate.name, memberTemplate.vtype);
				stringMember.set(member.getTextContent());
				return stringMember;
			case POINTER:
				HKXPointerMember pointerMember = new HKXPointerMember(memberTemplate.name, memberTemplate.vtype, memberTemplate.vsubtype, memberTemplate.target);
				pointerMember.set(member.getTextContent());
				return pointerMember;
			case COMPLEX:
				return complexMember.handleNode(member, memberTemplate);
			case ARRAY:
				return arrayHandler.handleNode(member, memberTemplate);
			case OBJECT:
				return handleObject(objectNode, memberTemplate);
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

	private HKXMember handleObject(Node memberNode, HKXMemberTemplate memberTemplate) throws ClassFileReadError, InvalidTagXMLException {
		NodeList children = memberNode.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node objectNode = children.item(i);
			if(objectNode.getNodeName().equals("hkobject")) {
				return nodeHandler.handleSubObject(objectNode, memberTemplate.target);
			}
		}
		throw new InvalidTagXMLException(SBundle.getString("error.tag.read.sobject") + memberTemplate.target);
	}
}
