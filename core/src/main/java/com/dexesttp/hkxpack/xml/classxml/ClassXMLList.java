package com.dexesttp.hkxpack.xml.classxml;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.xml.classxml.definition.classes.BaseClass;
import com.dexesttp.hkxpack.xml.classxml.definition.classes.ImportedClass;
import com.dexesttp.hkxpack.xml.classxml.definition.classes.ReadableClass;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonImportedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public class ClassXMLList {
	private static ClassXMLList instance;
	
	private ClassXMLList() {
	}
	
	public static ClassXMLList getInstance() {
		if(instance == null)
			instance = new ClassXMLList();
		return instance;
	}
	

	private Queue<BaseClass> baseClasses = new LinkedList<>();
	
	/**
	 * Add a base class to the import queue.
	 * @param bClass
	 */
	public void addImport(BaseClass bClass) {
		if(DisplayProperties.displayClassImportsInfo)
			System.out.println("[CLA]\t[QUE]\t\t" + bClass.getClassName());
		baseClasses.offer(bClass);
	}

	/**
	 * Add a base class to the import queue.
	 * @param name
	 * @param classID
	 */
	public void addImport(String name, int classID) {
		addImport(new BaseClass(name, classID));
	}
	
	/**
	 * Import all base classes and their dependancies.
	 * @throws IOException
	 */
	public void importClasses() throws IOException {
		while(!baseClasses.isEmpty()) {
			BaseClass bClass = baseClasses.poll();
			ClassXMLReader.getClassFromFile(bClass.getClassName(), bClass.getClassID());
		}
	}
	

	private Map<String, ImportedClass> importedClasses = new LinkedHashMap<>();
	private Map<String, ReadableClass> readableClasses = new HashMap<>();
	
	/**
	 * Add an imorted class into the ClassXML Import list.
	 * @param name the name of the class
	 * @param importedClass the imported class itself.
	 */
	public void addClass(String name, ImportedClass importedClass) {
		if(importedClasses.containsKey(name) || readableClasses.containsKey(name))
			return;
		this.importedClasses.put(name, importedClass);
	}
	
	/**
	 * Resolve all current imported classes into readable classes.
	 * @throws IOException
	 * @throws UnknownClassException 
	 * @throws NonResolvedClassException 
	 * @throws UnknownEnumerationException 
	 * @throws NumberFormatException 
	 * @throws UnsupportedCombinaisonException 
	 * @throws NonImportedClassException 
	 */
	public void resolve() throws IOException, NonResolvedClassException, UnknownClassException, NumberFormatException, UnknownEnumerationException, UnsupportedCombinaisonException, NonImportedClassException {
		while(!importedClasses.isEmpty()) {
			Map.Entry<String, ImportedClass> classObj = importedClasses.entrySet().iterator().next();
			readableClasses.put(classObj.getKey(), classObj.getValue().resolve());
			importedClasses.remove(classObj.getKey());
		}
		importedClasses.clear();
	}
	
	/**
	 * Retrieve a previously resolved class
	 * @param name
	 * @return
	 * @throws NonResolvedClassException if the wanted class wasn't resolved yet.
	 * @throws UnknownClassException if the class wasn't even imported yet.
	 */
	public ReadableClass getReadableClass(String name) throws NonResolvedClassException, NonImportedClassException, UnknownClassException {
		if(!readableClasses.containsKey(name)) {
			if(importedClasses.containsKey(name))
				throw new NonResolvedClassException(name);
			for(BaseClass classInst : baseClasses)
				if(classInst.getClassName().equals(name))
					throw new NonImportedClassException(name);
			throw new UnknownClassException(name);
		}
		return readableClasses.get(name);
	}
	
	public ReadableClass getOrResolveReadableClass(String name) throws UnknownClassException, NumberFormatException, IOException, NonResolvedClassException, UnknownEnumerationException, UnsupportedCombinaisonException, NonImportedClassException {
		try {
			return getReadableClass(name);
		} catch (NonResolvedClassException e) {
			ImportedClass impCl = importedClasses.remove(name);
			ReadableClass resCl = impCl.resolve();
			readableClasses.put(name, resCl);
			return resCl;
		}
	}

	/**
	 * Returns 'true' if the given classname is either on the import queue, resolve queue or already resolved.
	 * @param name
	 * @return
	 */
	public boolean hasClass(String name) {
		if(readableClasses.containsKey(name))
			return true;
		if(importedClasses.containsKey(name))
			return true;
		for(BaseClass classInst : baseClasses)
			if(classInst.getClassName().equals(name))
				return true;
		return false;
	}
}
