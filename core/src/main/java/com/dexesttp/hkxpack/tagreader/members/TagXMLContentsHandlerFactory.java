package com.dexesttp.hkxpack.tagreader.members;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.tagreader.TagXMLNodeHandler;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

public class TagXMLContentsHandlerFactory {
	private final TagXMLNodeHandler nodeHandler;

	public TagXMLContentsHandlerFactory(TagXMLNodeHandler nodeHandler) {
		this.nodeHandler = nodeHandler;
	}
	
	public TagXMLContentsHandler getHandler(HKXType type) {
		switch(type.getFamily()) {
			case DIRECT:
				return new TagXMLDirectHandler();
			case ENUM:
				return new TagXMLContentsHandler() {
					@Override
					public HKXMember handleNode(Node member, HKXMemberTemplate memberTemplate) {
						HKXDirectMember<String> directMember = new HKXDirectMember<>(memberTemplate.name, memberTemplate.vtype);
						directMember.set(member.getTextContent());
						return directMember;
					}
				};
			case STRING:
				return new TagXMLContentsHandler() {
					@Override
					public HKXMember handleNode(Node member, HKXMemberTemplate memberTemplate) {
						HKXStringMember stringMember = new HKXStringMember(memberTemplate.name, memberTemplate.vtype);
						stringMember.set(member.getTextContent());
						return stringMember;
					}
				};
			case POINTER:
				return new TagXMLContentsHandler() {
					@Override
					public HKXMember handleNode(Node member, HKXMemberTemplate memberTemplate) {
						HKXPointerMember pointerMember = new HKXPointerMember(memberTemplate.name, memberTemplate.vtype, memberTemplate.vsubtype, memberTemplate.target);
						pointerMember.set(member.getTextContent());
						return pointerMember;
					}
				};
			case COMPLEX:
				return new TagXMLComplexHandler();
			case ARRAY:
				return new TagXMLArrayHandler(nodeHandler);
			case OBJECT:
				return new TagXMLContentsHandler() {
					@Override
					public HKXMember handleNode(Node memberNode, HKXMemberTemplate memberTemplate) throws ClassFileReadError, InvalidTagXMLException {
						NodeList children = memberNode.getChildNodes();
						for(int i = 0; i < children.getLength(); i++) {
							Node objectNode = children.item(i);
							if(objectNode.getNodeName().equals("hkobject")) {
								return nodeHandler.handleSubObject(objectNode, memberTemplate.target);
							}
						}
						throw new InvalidTagXMLException(SBundle.getString("error.tag.read.sobject") + memberTemplate.target);
					};
				};
			default :
				return null;
		}
	}
}
