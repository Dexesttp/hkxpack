package com.dexesttp.hkxpack.tagwriter;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;

/**
 * Handles the conversion between a {@link HKXDirectMember} member and its content as a {@link String}.
 */
class TagXMLDirectMemberHandler {
	public static final int VECTOR4_LENGTH = 4;
	public static final int QSTRANSFORM_LENGTH = 12;
	/**
	 * Converts a {@link HKXDirectMember} into a {@link String}.
	 * @param member the {@link HKXDirectMember} to convert
	 * @return a {@link String} containign the value of the {@link HKXDirectMember}.
	 */
	@SuppressWarnings("unchecked")
	String getStringValue(final HKXDirectMember<?> member) {
		if(member.get() instanceof Double[]) {
			Double[] contents = (Double[]) member.get();
			if(contents.length <= VECTOR4_LENGTH) {
				StringBuffer contentsAccu = new StringBuffer();
				contentsAccu.append('(');
				for(int i = 0; i < contents.length; i++) {
					contentsAccu.append(contents[i]).append(' ');
				}
				return contentsAccu.substring(0, contentsAccu.length() - 1) + ")";
			} else {
				if(contents.length == QSTRANSFORM_LENGTH) {
					return "(" + contents[0] + " " + contents[1] + " " + contents[2] + " " + contents[3] + ")" +
							"(" + contents[4] + " " + contents[5] + " " + contents[6]+ " " + contents[7] + ")" +
							"(" + contents[8] + " " + contents[9] + " " + contents[10] + " " + contents[11] + ")";
				}
				else {
					return "(" + contents[0] + " " + contents[1] + " " + contents[2] + " " + contents[3] + ")" +
							"(" + contents[4] + " " + contents[5] + " " + contents[6] + " " + contents[7] + ")" +
							"(" + contents[8] + " " + contents[9] + " " + contents[10] + " " + contents[11] + ")" +
							"(" + contents[12] + " " + contents[13] + " " + contents[14] + " " + contents[15] + ")";
				}
			}
		}
		if(member.get() instanceof Character) {
			return Integer.toString((int) ((char) ((HKXDirectMember<Character>) member).get()));
		}
		return member.get().toString();
	}
}
