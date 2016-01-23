package com.dexesttp.hkxpack.xml.classxml.definition.members.reader;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.resources.PointerNameGiver;
import com.dexesttp.hkxpack.resources.Properties;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public class PtrMemberReader extends BaseMemberReader {
	public PtrMemberReader(String name, int size) {
		super(name, size);
	}

	@Override
	public Node readDirect(Document document, byte[] toRead, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws IOException, InvalidPositionException, UnsupportedCombinaisonException {
		if(Properties.displayDebugInfo)
			System.out.println("[MEM]\t[PTR]\t[DIR]\t" + name);
		DataExternal ptrAddr = data2.readNext();
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		Node txt = document.createTextNode(PointerNameGiver.getInstance().getName(ptrAddr.to));
		res.appendChild(txt);
		return res;
	}

	@Override
	public Node readIndirect(Document document, long arrPos, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws UnsupportedCombinaisonException, IOException, InvalidPositionException {
		if(Properties.displayDebugInfo)
			System.out.println("[MEM]\t[PTR]\t[INDIR]\t" + arrPos);
		DataExternal ptrAddr = data2.readNext();
		Node txt = document.createTextNode(PointerNameGiver.getInstance().getName(ptrAddr.to));
		return txt;
	}
}
