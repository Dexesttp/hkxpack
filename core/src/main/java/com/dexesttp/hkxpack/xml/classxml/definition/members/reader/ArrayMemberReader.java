package com.dexesttp.hkxpack.xml.classxml.definition.members.reader;

import java.io.IOException;
import java.util.Arrays;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NotKnownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public class ArrayMemberReader extends BaseMemberReader {
	protected final BaseMemberReader content;
	
	public ArrayMemberReader(String name, long size, BaseMemberReader content) {
		super(name, size);
		this.content = content;
	}
	
	@Override
	public Node readDirect(Document document, byte[] toRead, DataInterface data, Data1Interface data1, Data2Interface data2) throws IOException, InvalidPositionException, UnsupportedCombinaisonException, UnknownEnumerationException, NonResolvedClassException, NotKnownClassException {
		byte[] lengthArray = {toRead[8], toRead[9], toRead[10], toRead[11]};
		System.out.println(Arrays.toString(toRead));
		int length = ByteUtils.getInt(lengthArray);
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		res.setAttribute("numelements", "" + (length));
		if(length > 0) {
			DataInternal dataChunk = data1.readNext();
			long arrPos = dataChunk.to;
			for(int i = 0; i < length; i++) {
				System.out.println("Content : " + content.toString());
				Node internal = content.readIndirect(document, arrPos, data, data1, data2);
				res.appendChild(internal);
				arrPos += content.getSize();
			}
		}
		return res;
	}

	@Override
	public Node readIndirect(Document document, long arrPos, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws UnsupportedCombinaisonException {
		throw new UnsupportedCombinaisonException();
	}
}
