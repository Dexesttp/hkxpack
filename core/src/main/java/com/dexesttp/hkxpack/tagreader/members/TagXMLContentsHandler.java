package com.dexesttp.hkxpack.tagreader.members;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;

/**
 * Handle the contents of a {@link Node} into a {@link HKXMember}
 */
interface TagXMLContentsHandler {
	/**
	 * Creates a {@link HKXMember} based on a {@link Node} and its contents description as a {@link HKXMemberTemplate}
	 * @param member the {@link Node} to read.
	 * @param memberTemplate the {@link HKXMemberTemplate} describing the {@link Node}'s contents
	 * @return a {@link HKXMember} containing all relevant data.
	 */
	public HKXMember handleNode(Node member, HKXMemberTemplate memberTemplate);
}
