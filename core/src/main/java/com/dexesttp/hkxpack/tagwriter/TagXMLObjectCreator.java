package com.dexesttp.hkxpack.tagwriter;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.Flag;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.resources.DisplayProperties;

/**
 * Converts {@link HKXObject} instances to {@link Nodes}<br >
 * Please use {@link TagXMLDataCreator} as the handled class.
 */
class TagXMLObjectCreator {
	
	private Document document;
	private TagXMLMemberCreator memberCreator;

	/**
	 * Creates a {@link TagXMLObjectCreator} as a {@link HKXObject} creator instance.<br >
	 * This shoud only be done by a {@link TagXMLDataCreator}.
	 * @param tagXMLDataCreator the multipurpose creator.
	 */
	TagXMLObjectCreator(TagXMLDataCreator tagXMLDataCreator) {
		this.document = tagXMLDataCreator.document();
		this.memberCreator = tagXMLDataCreator.memberCreator();
	}
	
	/**
	 * Create a {@link Node} from a {@link HKXObject}.
	 * Please use {@link TagXMLDataCreator#create(com.dexesttp.hkxpack.data.HKXData)} to aply this behavior to a {@link HKXObject}.
	 * @param object the object to retrieve data from.
	 * @return the created node.
	 */
	Node create(HKXObject object) {
		Element res = document.createElement("hkobject");
		// Create base class node.
		if(!object.getName().isEmpty()) {
			res.setAttribute("class", object.getDescriptor().getName());
			res.setAttribute("name", object.getName());
			res.setAttribute("signature","0x" + Long.toHexString(object.getDescriptor().getSignature()));
		}
		List<HKXMember> members = object.members();
		List<HKXMemberTemplate> memberTemplates = object.getDescriptor().getMemberTemplates();
		for(int i = 0; i < members.size(); i++) {
			HKXMember member = members.get(i);
			Node memberNode = memberCreator.create(member);
			if(memberTemplates.get(i).flag == Flag.SERIALIZE_IGNORED && DisplayProperties.ignoreSerialized) {
				memberNode = document.createComment(" " + member.getName() + " SERIALIZE_IGNORED ");
			} else {
				memberNode = memberCreator.create(member);
			}
			res.appendChild(memberNode);
		}
		return res;
	}
}
