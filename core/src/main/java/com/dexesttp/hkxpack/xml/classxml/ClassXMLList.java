package com.dexesttp.hkxpack.xml.classxml;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	
	private Map<String, ImportedClass> importedClasses = new LinkedHashMap<>();
	private Map<String, ReadableClass> readableClasses = new HashMap<>();
	
	public void addClass(String name, ImportedClass importedClass) {
		if(importedClasses.containsKey(name) || readableClasses.containsKey(name))
			return;
		this.importedClasses.put(name, importedClass);
	}
	
	/**
	 * Resolve all current imported classes into readable classes.
	 * @throws IOException
	 * @throws NotKnownClassException 
	 * @throws NonResolvedClassException 
	 */
	public void resolve() throws IOException, NonResolvedClassException, NotKnownClassException {
		for(Map.Entry<String, ImportedClass> classObj : importedClasses.entrySet()) {
			readableClasses.put(classObj.getKey(), classObj.getValue().resolve());
		}
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
		if(!readableClasses.containsKey(name)) {
			if(importedClasses.containsKey(name))
				throw new NonResolvedClassException(name);
			throw new NotKnownClassException(name);
		}
		return readableClasses.get(name);
	}
}
