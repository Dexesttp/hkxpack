package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

public enum PtrMemberResolver implements BaseMemberResolver {
	TYPE_POINTER,
	TYPE_FUNCTIONPOINTER;

	@Override
	public int getSize() {
		return 0;
	}
	
	public void resolve(String name, String classname) {
	}
}
