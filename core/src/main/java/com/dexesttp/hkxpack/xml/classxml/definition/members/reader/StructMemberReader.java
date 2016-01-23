package com.dexesttp.hkxpack.xml.classxml.definition.members.reader;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.hkx.structs.Struct;
import com.dexesttp.hkxpack.xml.classxml.definition.classes.ReadableClass;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public class StructMemberReader extends BaseMemberReader {
	private final ReadableClass classInst;

	public StructMemberReader(String name, int size, ReadableClass classInst) {
		super(name, size);
		this.classInst = classInst;
	}

	@Override
	public Node readDirect(Document document, byte[] toRead, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws IOException, InvalidPositionException, UnsupportedCombinaisonException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException {
		Element root = document.createElement("hkparam");
		root.setAttribute("name", name);
		// Get the struct position
		DataInternal pos = data1.readNext();
		// Read the struct.
		Struct currentStruct = classInst.getStruct();
		data.read(pos.to, currentStruct);
		// Resolve the struct to a Node using data/data1/data2.
		Node node = classInst.resolve(document, "", currentStruct, data, data1, data2, true);
		root.appendChild(node);
		return root;
	}

	@Override
	public Node readIndirect(Document document, long arrPos, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws UnsupportedCombinaisonException, IOException, InvalidPositionException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException {
		// Read the struct.
		Struct currentStruct = classInst.getStruct();
		data.read(arrPos, currentStruct);
		// Resolve the struct to a Node using data/data1/data2.
		Node node = classInst.resolve(document, "", currentStruct, data, data1, data2, true);
		return node;
	}
}
