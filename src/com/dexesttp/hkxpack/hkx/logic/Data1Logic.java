package com.dexesttp.hkxpack.hkx.logic;

import java.io.IOException;
import java.util.List;

import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.DataReader;
import com.dexesttp.hkxpack.hkx.reader.InternalLinkReader;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;

public class Data1Logic {

	protected InternalLinkReader reader;

	public Data1Logic(InternalLinkReader data1reader) {
		this.reader = data1reader;
	}

	public void resolve(HKXHandler handler) throws UninitializedHKXException, IOException {
		DataReader reader = handler.getDataReader();
		List<ClassXML> instances = handler.getInstanceList();
		// TODO just... do it !
	}
}
