package com.dexesttp.hkxpack.tagwriter;

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
	@SuppressWarnings("unchecked")
	String getStringValue(HKXDirectMember<?> member) {
		if(member.get() instanceof Double[]) {
			Double[] contents = (Double[]) member.get();
			if(contents.length <= 4) {
				String contentsAccu = "(";
				for(int i = 0; i < contents.length; i++) {
					contentsAccu += "" + contents[i] + " ";
				}
				return contentsAccu.substring(0, contentsAccu.length() - 1) + ")";
			} else {
				return "(" + contents[0] + " " + contents[1] + " " + contents[2] + " " + ")" +
						"(" + contents[3] + " " + contents[4] + " " + contents[5]+ " " + contents[6] + ")" +
						"(" + contents[7] + " " + contents[8] + " " + contents[9] + " " + ")";
			}
		}
		if(member.get() instanceof Character)
			return "" + (int) ((char) ((HKXDirectMember<Character>) member).get());
		return "" + member.get();
	}
}
