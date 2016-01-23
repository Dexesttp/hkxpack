package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ResolverList {
	private static ResolverList instance;
	private ResolverList() {
	}
	
	public static ResolverList getInstance() {
		if(instance == null)
			instance = new ResolverList();
		return instance;
	}
	
	private Map<String, Class> resolverList = new HashMap<String, Class>();
	
	{
		resolverList.put("Array", ArrayMemberResolver.class);
		resolverList.put("Direct", DirectMemberResolver.class);
		resolverList.put("Enum", EnumMemberResolver.class);
		resolverList.put("Ptr", PtrMemberResolver.class);
		resolverList.put("String", StringMemberResolver.class);
		resolverList.put("Struct", StructMemberResolver.class);
	}
	/**
	 * Base values are :
	 * Array - Direct - Enum - Ptr - String - Struct
	 * @param name
	 * @param resolver
	 */
	public void set(String name, Class resolverClass) {
		resolverList.put(name, resolverClass);
	}

	public Collection<Class> getAll() {
		return resolverList.values();
	}
}
