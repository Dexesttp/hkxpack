package com.dexesttp.hkxpack.hkx.logic;

import java.io.IOException;
import java.util.LinkedList;

import com.dexesttp.hkxpack.hkx.classes.ClassFlagAssociator;
import com.dexesttp.hkxpack.hkx.classes.ClassMapper;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;

public class InstanceListLogic {

	public LinkedList<ClassXML> resolve(HKXHandler handler) throws IOException, UninitializedHKXException {
		final ClassMapper mapper = handler.getMapper();
		final ClassFlagAssociator associator = handler.getAssociator();
		final LinkedList<ClassXML> instanceList = new LinkedList<>();
		for(Long classPos : associator.getNamesIterator()) {
			instanceList.add(ClassXMLList.getInstance().get(mapper.getName(classPos)));
		}
		return instanceList;
	}

}
