package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * ResolverList is the data type handler.
 * It stores all resolver types in a hashmap, allowing for easy manipulation of handled types.
 * It also incidentally is a singleton, allowing access from anywhere in the code.
 */
@SuppressWarnings("rawtypes")
public class ResolverList {
	private Map<String, Class> resolverList = new HashMap<String, Class>();
	
	{
		resolverList.put("Array", ArrayMemberResolver.class);
		resolverList.put("Direct", DirectMemberResolver.class);
		resolverList.put("Enum", EnumMemberResolver.class);
		resolverList.put("Ptr", PtrMemberResolver.class);
		resolverList.put("String", StringMemberResolver.class);
		resolverList.put("Struct", StructMemberResolver.class);
	}
	
	private static ResolverList instance;
	private ResolverList() {
	}
	public static ResolverList getInstance() {
		if(instance == null)
			instance = new ResolverList();
		return instance;
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
