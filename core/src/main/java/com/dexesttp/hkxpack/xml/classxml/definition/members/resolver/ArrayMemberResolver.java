package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

public enum ArrayMemberResolver {
	TYPE_ARRAY(4),
	TYPE_SIMPLEARRAY(4),
	TYPE_INPLACEARRAY(4),
	TYPE_RELARRAY(4);
	
	private final int size;

	private ArrayMemberResolver(int size) {
		this.size = size;
	}
	
	public void resolve(String name, String classname) {
		System.out.println("Size : " + size);
	}
}
