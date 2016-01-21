package com.dexesttp.hkxpack.hkx.classnames;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.dexesttp.hkxpack.xml.classxml.ClassXMLReader;

public class ClassnamesData extends LinkedHashMap<String, Integer> {
	private static final long serialVersionUID = 5525421716171216039L;
	
	@Override
	public Integer put(String name, Integer id){
		try {
			ClassXMLReader.getClassFromFile(name, id);
			return super.put(name, id);
		}
		catch(IOException e) {
			System.err.println("Unable to import class : " + name);
			e.printStackTrace();
		}
		return null;
	}
}
