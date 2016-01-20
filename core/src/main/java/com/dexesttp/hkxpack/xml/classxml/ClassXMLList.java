package com.dexesttp.hkxpack.xml.classxml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxpack.xml.classxml.definition.classes.ImportedClass;
import com.dexesttp.hkxpack.xml.classxml.definition.classes.ReadableClass;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NotKnownClassException;

public class ClassXMLList {
	private static ClassXMLList instance;
	
	private ClassXMLList() {
	}
	
	public static ClassXMLList getInstance() {
		if(instance == null)
			instance = new ClassXMLList();
		return instance;
	}
	
	private Map<String, ImportedClass> importedClasses = new HashMap<>();
	private Map<String, ReadableClass> readableClasses = new HashMap<>();
	
	public void addClass(String name, ImportedClass importedClass) {
		if(importedClasses.containsKey(name) || readableClasses.containsKey(name))
			return;
		this.importedClasses.put(name, importedClass);
	}
	
	/**
	 * Resolve all current imported classes into readable classes.
	 * @throws IOException
	 */
	public void resolve() throws IOException {
		for(Map.Entry<String, ImportedClass> classObj : importedClasses.entrySet())
			readableClasses.put(classObj.getKey(), classObj.getValue().resolve());
		importedClasses.clear();
	}
	
	/**
	 * Retrieve a previously resolved class
	 * @param name
	 * @return
	 * @throws NonResolvedClassException if the wanted class wasn't resolved yet.
	 * @throws NotKnownClassException if the class wasn't even imported yet.
	 */
	public ReadableClass getReadableClass(String name) throws NonResolvedClassException, NotKnownClassException {
		if(importedClasses.containsKey(name))
			throw new NonResolvedClassException(name);
		if(!readableClasses.containsKey(name))
			throw new NotKnownClassException(name);
		return readableClasses.get(name);
	}
}
