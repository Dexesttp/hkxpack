package com.dexesttp.hkxpack.xml.classxml.definition.members;

public class MemberResolver {
	
	// TODO fix this ugly mess.
	public static ResolvedMember resolve(String name, String classname, String vtype, String vsubtype, String ctype, String etype) {
		return null;
	}
	
	/*
	 * Thanks to user2910265 for finally solving this thing
	 * http://stackoverflow.com/a/21214662
	 */
	public static <E extends Enum<E>> E getEnumVal(Class<E> _enumClass, String name) {
		try {
			return Enum.valueOf(_enumClass, name);
		}
		catch(Exception e) {
			return null;
		}
	}
}
