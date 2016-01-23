package com.dexesttp.hkxpack.hkx.classnames;

import java.util.LinkedHashMap;

import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;

public class ClassnamesData extends LinkedHashMap<Long, Classname> {
	private static final long serialVersionUID = 5525421716171216039L;
	
	public Classname put(long position, String name, byte[] id){
		ClassXMLList.getInstance().addImport(name, ByteUtils.getInt(id));
		return super.put(position, new Classname(name, id));
	}
}
