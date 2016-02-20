package com.dexesttp.hkxpack.tagxml;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;

/**
 * Handles the conversion between a {@link HKXDirectMember} member and its content as a {@link String}.
 */
class TagXMLDirectMemberHandler {
	/**
	 * Converts a {@link HKXDirectMember} into a {@link String}.
	 * @param member the {@link HKXDirectMember} to convert
	 * @return a {@link String} containign the value of the {@link HKXDirectMember}.
	 */
	String getStringValue(HKXDirectMember<?> member) {
		if(member.get() instanceof Double[]) {
			Double[] contents = (Double[]) member.get();
			String contentsAccu = "(";
			for(int i = 0; i < contents.length; i++) {
				contentsAccu += "" + contents[i] + " ";
			}
			return contentsAccu.substring(0, contentsAccu.length() - 1) + ")";
		}
		if(member.get() instanceof Character)
			return "" + (int) ((char) member.get());
		return "" + member.get();
	}
}
