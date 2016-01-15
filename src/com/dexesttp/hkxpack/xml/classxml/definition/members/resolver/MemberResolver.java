package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;

// TODO fix this ugly mess.
public class MemberResolver {
	public static ResolvedMember resolve(String name, String vtype, String ctype, String vsubtype) {
		DirectMemberResolver resolver1 = getDirectMemberResolver(vtype);
		ArrayMemberResolver resolver2 = getArrayMemberResolver(vtype);
		PtrMemberResolver resolver3 = getPtrMemberResolver(vtype);
		if(resolver1 != null) {
			return resolver1.resolve(name);
		} else if(resolver2 != null){
			resolver1 = getDirectMemberResolver(vsubtype);
			resolver3 = getPtrMemberResolver(vsubtype);
			if(resolver1 != null) {
				return resolver2.resolve(resolver1.resolve(name), name);
			} else if(resolver3 != null) {
				resolver1 = getDirectMemberResolver(ctype);
				if(resolver1 != null)
					return resolver2.resolve(resolver3.resolve(resolver1.resolve(name),  name), name);
			}
		} else if(resolver3 != null) {
			resolver1 = getDirectMemberResolver(ctype);
			if(resolver1 != null)
				return resolver3.resolve(resolver1.resolve(name),  name);
		}
		throw new IllegalArgumentException(name + " : " + vtype + "//" + ctype + "//" + vsubtype);
	}
	
	public static DirectMemberResolver getDirectMemberResolver(String type) {
		try {
			return DirectMemberResolver.valueOf(type);
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}
	
	public static ArrayMemberResolver getArrayMemberResolver(String type) {
		try {
			return ArrayMemberResolver.valueOf(type);
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}
	
	public static PtrMemberResolver getPtrMemberResolver(String type) {
		try {
			return PtrMemberResolver.valueOf(type);
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}
}
