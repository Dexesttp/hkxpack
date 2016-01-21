package com.dexesttp.hkxpack.hkx.classnames;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLReader;

public class ClassnamesData extends LinkedHashMap<Long, Classname> {
	private static final long serialVersionUID = 5525421716171216039L;
	
	public Classname put(long position, String name, byte[] id){
		try {
			ClassXMLReader.getClassFromFile(name, ByteUtils.getInt(id));
			return super.put(position, new Classname(name, id));
		}
		catch(IOException e) {
			System.err.println("Unable to import class : " + name);
			e.printStackTrace();
		}
		return null;
	}
}
