package com.dexesttp.hkxpack.hkx.classnames;

public class Classname {
	public Classname(String name, byte[] id) {
		this.name = name;
		this.uuid = id.clone();
	}
	public String name;
	public byte[] uuid;
}
