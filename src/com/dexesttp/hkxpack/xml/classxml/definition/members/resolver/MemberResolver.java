package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;

public class MemberResolver {
	
	// TODO fix this ugly mess.
	public static ResolvedMember resolve(String name, String classname, String vtype, String vsubtype, String ctype, String etype) throws IOException {
		DirectMemberResolver resolver1 = getEnumVal(DirectMemberResolver.class, vtype);
		ArrayMemberResolver resolver2 = getEnumVal(ArrayMemberResolver.class, vtype);
		PtrMemberResolver resolver3 = getEnumVal(PtrMemberResolver.class, vtype);
		StructMemberResolver resolver4 = getEnumVal(StructMemberResolver.class, vtype);
		EnumMemberResolver resolver5 = getEnumVal(EnumMemberResolver.class, vtype);
		if(resolver1 != null) {
			//basic joe.
			return resolver1.resolve(name, classname);
		} else if(resolver2 != null){
			// Array joe.
			resolver1 = getEnumVal(DirectMemberResolver.class, vsubtype);
			resolver3 = getEnumVal(PtrMemberResolver.class, vsubtype);
			resolver4 = getEnumVal(StructMemberResolver.class, vsubtype);
			if(resolver1 != null) {
				// Basic array joe
				return resolver2.resolve(resolver1.resolve(name, classname), name, classname);
			} else if(resolver3 != null) {
				// Ptr array joe - some extra code fro void* compatibility.
				return resolver2.resolve(resolver3.resolve(DirectMemberResolver.TYPE_VOID.resolve(name, classname),  name, classname), name, classname);
			} else if(resolver4 != null) {
				// Struct array joe
				return resolver2.resolve(resolver4.resolve(ctype,  name, classname), name, classname);
			}
		} else if(resolver3 != null) {
			// Ptr joe.
			resolver1 = getEnumVal(DirectMemberResolver.class, vsubtype);
			resolver4 = getEnumVal(StructMemberResolver.class, vsubtype);
			if(resolver1 != null)
				// Ptr on basic joe.
				return resolver3.resolve(resolver1.resolve(name, classname),  name, classname);
			else if(resolver4 != null)
				// Ptr on struct joe.
				return resolver3.resolve(resolver4.resolve(ctype, name, classname),  name, classname);
		} else if(resolver4 != null) {
			// Struct joe.
			return resolver4.resolve(ctype, name, classname);
		} else if(resolver5 != null) {
			// Enum joe.
			resolver1 = getEnumVal(DirectMemberResolver.class, vsubtype);
			if(resolver1 != null)
				// Still enum joe, as the enum is basic.
				return resolver5.resolve(resolver1.resolve(name, classname), etype, name, classname);
		}
		throw new IllegalArgumentException(name + " : " + vtype + "//" + vsubtype+ " - " + ctype + "|" + etype);
	}
	
	/*
	 * Thanks to user2910265 for finally solving this thing
	 * http://stackoverflow.com/a/21214662
	 */
	private static <E extends Enum<E>> E getEnumVal(Class<E> _enumClass, String name) {
		try {
			return Enum.valueOf(_enumClass, name);
		}
		catch(Exception e) {
			return null;
		}
	}
}
