package com.dexesttp.hkxpack.hkx.classnames;

import java.util.LinkedHashMap;

public class ClassnamesData extends LinkedHashMap<Long, Classname> {
	private static final long serialVersionUID = 5525421716171216039L;
	
	public Classname put(long position, String name, byte[] id){
		return super.put(position, new Classname(name, id));
	}

	public boolean containsClass(String name) {
		for(Classname classname : this.values())
			if(classname.name.equals(name))
				return true;
		return false;
	}
}
