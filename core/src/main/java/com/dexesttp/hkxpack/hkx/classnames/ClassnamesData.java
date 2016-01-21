package com.dexesttp.hkxpack.hkx.classnames;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.dexesttp.hkxpack.xml.classxml.ClassXMLReader;

public class ClassnamesData extends LinkedHashMap<Integer, String> {
	private static final long serialVersionUID = 5525421716171216039L;
	
	@Override
	public String put(Integer id, String name){
		try {
			ClassXMLReader.getClassFromFile(name, id);
			return super.put(id, name);
		}
		catch(IOException e) {
			System.err.println("Unable to import class : " + name);
			e.printStackTrace();
		}
		return null;
	}
}
