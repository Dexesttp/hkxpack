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
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public class ArrayMemberReader extends BaseMemberReader {
	protected final BaseMemberReader content;

	public ArrayMemberReader(String name, int size, BaseMemberReader content) {
		super(name, size);
		this.content = content;
	}

	@Override
	public Node readDirect(Document document, byte[] toRead, DataInterface data, Data1Interface data1, Data2Interface data2) throws IOException, InvalidPositionException, UnsupportedCombinaisonException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException {
		byte[] lengthArray = {toRead[8], toRead[9], toRead[10], toRead[11]};
		int length = ByteUtils.getInt(lengthArray);
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		res.setAttribute("numelements", "" + (length));
		boolean isTextInternal = false;
		cleanText();
		if(length > 0) {
			// DEBUG
			if(DisplayProperties.displayEmbeddedData)
				res.setAttribute("elemSize", ""+content.getSize());
			if(DisplayProperties.displayDebugInfo)
				System.out.println("[MEM]\t[ARR]\t[LEN]\t" + length);
			DataInternal dataChunk = data1.readNext();
			long arrPos = dataChunk.to;
			for(int i = 0; i < length; i++) {
				Node internal = content.readIndirect(document, arrPos, data, data1, data2);
				if(internal.getNodeType() == Node.TEXT_NODE)
					isTextInternal = true;
				if(!isTextInternal)
					res.appendChild(internal);
				else
					addToText(internal.getTextContent());
				arrPos += content.getSize();
			}
			if(isTextInternal)
				res.appendChild(document.createTextNode(getText()));
		}
		else if(DisplayProperties.displayDebugInfo)
				System.out.println("[MEM]\t[ARR]\t\t[EMPTY]");
		return res;
	}

	// A Hack to not handle multiple text nodes.
	private String textContent = "";
	private String currentLine = "";
	private int LINE_SIZE_LIMIT = 64;

	private void cleanText() {
		textContent = "";
		currentLine = "";
	}
	
	private void addToText(String text) {
		if(currentLine.length() > LINE_SIZE_LIMIT) {
			textContent += "\n" + currentLine;
			currentLine = "";
		}
		if(!currentLine.isEmpty())
			currentLine += " ";
		currentLine += text;
	}

	private String getText() {
		if(!currentLine.isEmpty()) {
			if(!textContent.isEmpty())
				textContent += "\n";
			textContent += currentLine;
		}
		return textContent;
	}

	@Override
	public Node readIndirect(Document document, long arrPos, DataInterface data, Data1Interface data1,
			Data2Interface data2) throws UnsupportedCombinaisonException {
		throw new UnsupportedCombinaisonException();
	}
}
