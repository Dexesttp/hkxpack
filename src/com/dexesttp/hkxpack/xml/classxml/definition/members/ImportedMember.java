package com.dexesttp.hkxpack.xml.classxml.definition.members;

public class ImportedMember extends ClassXMLMember {
	private final String vtype;
	private final String vsubtype;
	private final String ctype;

	public ImportedMember(String name, String vtype, String vsubtype, String ctype) {
		super(name);
		this.vtype = vtype;
		this.vsubtype = vsubtype;
		this.ctype = ctype;
	}

	public ResolvedMember resolve() {
		ResolvedMember res = new ResolvedMember(name);
		
		return null;
	}
}
