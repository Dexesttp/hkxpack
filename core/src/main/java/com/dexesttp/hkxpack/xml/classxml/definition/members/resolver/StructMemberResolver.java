package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

public enum StructMemberResolver implements BaseMemberResolver {
	TYPE_STRUCT(8);

	private StructMemberResolver(int size) {
	}

	@Override
	public int getSize() {
		return 0;
	}

	public void resolve(String name, String classname) {
		
	}
}
