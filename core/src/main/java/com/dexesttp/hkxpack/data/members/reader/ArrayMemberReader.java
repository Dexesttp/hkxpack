package com.dexesttp.hkxpack.data.members.reader;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.hkx.HKXConnector;
import com.dexesttp.hkxpack.hkx.data.DataInternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidArrayException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.LoggerUtil;

/**
 * An Array in HKX is a 12-to-16-byte structure organized as such :
 * <<PTR_SIZE> bytes of 0-padding> <4 bytes : content length> <3 bytes : repeat of content length> 0x80
 * The assumed reason is because the 0-padding is a placeholder for the actual Array contents pointer in memory.
 * The repeat of the content length and the 0x80 is, however, a mystery.
 * The 0x80 presence is used by hkxpack as a validity control for the array.
 * The array contents pointer can be found as a Data1 value, but only if the array isn't empty.
 */
public class ArrayMemberReader extends MemberReader {
	private MemberReader internal;
	private int PTR_SIZE;

	public ArrayMemberReader(String name, long offset, MemberReader internal, int PTR_SIZE) {
		super(name, offset);
		this.internal = internal;
		this.PTR_SIZE = PTR_SIZE;
	}

	@Override
	public long getSize() {
		return PTR_SIZE + 8;
	}

	@Override
	public Node read(long position, Document document, HKXConnector connector) throws IOException, InvalidPositionException, InvalidArrayException {
		// Read the array descriptor.
		RandomAccessFile file = connector.data.setup(position + offset);
		byte[] arrDescriptor = new byte[(int) getSize()];
		file.read(arrDescriptor);
		//Retrieve the array size.
		int arrSize = ByteUtils.getInt(new byte[]{
				arrDescriptor[PTR_SIZE],
				arrDescriptor[PTR_SIZE+1],
				arrDescriptor[PTR_SIZE+2],
				arrDescriptor[PTR_SIZE+3]
		});
		// ERROR : check array validity [0x80 at the end]
		if(arrDescriptor[PTR_SIZE+7] != -128) {
			LoggerUtil.error("MEMBR", "ARRAY", "DESC", "Descriptor doesn't end in 0x80 ! @" + (position + offset));
			throw new InvalidArrayException();
		}
		// DEBUG : Display array size
		if(arrDescriptor[PTR_SIZE+7] != -128)
			LoggerUtil.info("MEMBR", "ARRAY", "SIZE", ""+arrSize);
		
		// Create output node
		Element res = document.createElement("hkparam");
		res.setAttribute("name", name);
		res.setAttribute("numelements", ""+arrSize);
		// If size > 0, read array connector from data1
		if(arrSize > 0) {
			// Hack, see below
			boolean isTextInternal = false;
			cleanText();
			// Read connector data.
			DataInternal dataChunk = connector.data1.readNext();
			long arrPos = dataChunk.to;
			// Iterate over array content.
			for(int i = 0; i < arrSize; i++) {
				// Let the member handle the contents based on the curent gives position.
				Node contentNode = internal.readInternal(arrPos, document, connector);
				if(contentNode.getNodeType() == Node.TEXT_NODE)
					isTextInternal = true;
				// Hack thing.
				if(!isTextInternal)
					res.appendChild(contentNode);
				else
					addToText(contentNode.getTextContent());
				// Go to the next element of the array
				arrPos += internal.getSize();
			}
			// Also the hack thing.
			if(isTextInternal)
				res.appendChild(document.createTextNode(getText()));
		}
		
		// Return the node
		return res;
	}

	// A Hack to not handle multiple text nodes.
	// TODO cleanup this hack
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
	public Node readInternal(long position, Document document, HKXConnector connector)
			throws IOException, InvalidPositionException, InvalidArrayException {
		return null;
	}
}
