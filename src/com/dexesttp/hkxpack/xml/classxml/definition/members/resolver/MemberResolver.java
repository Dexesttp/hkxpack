package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;

public class MemberResolver {
	
	// TODO fix this ugly mess.
	public static ResolvedMember resolve(String name, String vtype, String ctype, String vsubtype) {
		DirectMemberResolver resolver1 = getEnumVal(DirectMemberResolver.class, vtype);
		ArrayMemberResolver resolver2 = getEnumVal(ArrayMemberResolver.class, vtype);
		PtrMemberResolver resolver3 = getEnumVal(PtrMemberResolver.class, vtype);
		if(resolver1 != null) {
			return resolver1.resolve(name);
		} else if(resolver2 != null){
			resolver1 = getEnumVal(DirectMemberResolver.class, vsubtype);
			resolver3 = getEnumVal(PtrMemberResolver.class, vsubtype);
			if(resolver1 != null) {
				return resolver2.resolve(resolver1.resolve(name), name);
			} else if(resolver3 != null) {
				resolver1 = getEnumVal(DirectMemberResolver.class, ctype);
				if(resolver1 != null)
					return resolver2.resolve(resolver3.resolve(resolver1.resolve(name),  name), name);
			}
		} else if(resolver3 != null) {
			resolver1 = getEnumVal(DirectMemberResolver.class, ctype);
			if(resolver1 != null)
				return resolver3.resolve(resolver1.resolve(name),  name);
		}
		throw new IllegalArgumentException(name + " : " + vtype + "//" + ctype + "//" + vsubtype);
	}
	
	/*
	 * Thanks to user2910265 for finally solving this thing
	 * http://stackoverflow.com/a/21214662
	 */
	private static <E extends Enum<E>> E getEnumVal(Class<E> _enumClass, String name) {
		try {
			return Enum.valueOf(_enumClass, name);
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}
}
