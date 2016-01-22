package com.dexesttp.hkxpack.xml.classxml.definition.classes;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.hkx.structs.Struct;

public class ReadableClass extends ClassXML {
	protected final String classname;
	protected final int classID;
	
	public ReadableClass(String classname, int classID) {
		this.classname = classname;
		this.classID = classID;
	}

	@Override
	public String getClassName() {
		return classname;
	}

	@Override
	public int getClassID() {
		return classID;
	}

	public Struct getStruct() {
		// TODO this thing right here.
		return null;
	}

	public Node resolve(Struct currentStruct, DataInterface data, Data1Interface data1, Data2Interface data2) {
		// TODO this other thing right there.
		return null;
	}
}
