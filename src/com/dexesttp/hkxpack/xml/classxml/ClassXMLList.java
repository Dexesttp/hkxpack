package com.dexesttp.hkxpack.xml.classxml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;
import com.dexesttp.hkxpack.xml.classxml.definition.ImportedClass;
import com.dexesttp.hkxpack.xml.classxml.definition.ResolvedClass;

public class ClassXMLList {
	private static ClassXMLList instance;

	private ArrayList<String> toRead = new ArrayList<>();
	private ArrayList<ImportedClass> toResolve = new ArrayList<>();
	private Map<String, ResolvedClass> classMap = new HashMap<>();
	
	private ClassXMLList() {
	}
	
	public static ClassXMLList getInstance() {
		if(instance == null)
			instance = new ClassXMLList();
		return instance;
	}
	
	public void addClass(String classname) {
		toRead.add(classname);
	}
	
	public void resolve() {
		for(ImportedClass classObject : toResolve) {
			classMap.put(classObject.getName(), classObject.resolve());
		}
	}
	
	public ClassXML get(String classname) {
		return classMap.get(classname);
	}
}
