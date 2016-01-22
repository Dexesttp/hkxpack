package com.dexesttp.hkxpack.xml.classxml.definition.members;

import com.dexesttp.hkxpack.xml.classxml.definition.members.resolver.BaseMemberResolver;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolver.ResolverList;

public class MemberResolver {

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static Enum getEnum(String vtype) {
		Enum enumInst = null;
		for(Class resolverClass : ResolverList.getInstance().getAll()) {
			enumInst = getEnumVal(resolverClass, vtype);
			if(enumInst != null)
				break;
		}
		return enumInst;
	}

	@SuppressWarnings({"rawtypes"})
	public static ReadableMember resolve(String name, String classname, String flags, long offset, String vtype, String vsubtype, String ctype, String etype) {
		Enum enumInst = getEnum(vtype);
		((BaseMemberResolver) enumInst).setExtraData(vsubtype, ctype, etype);
		return new ReadableMember(name, classname, flags, offset, enumInst);
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
