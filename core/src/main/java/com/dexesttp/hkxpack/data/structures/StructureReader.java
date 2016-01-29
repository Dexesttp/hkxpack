package com.dexesttp.hkxpack.data.structures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.members.MemberReader;
import com.dexesttp.hkxpack.hkx.HKXConnector;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidArrayException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.resources.LoggerUtil;

public class StructureReader extends Structure {
	private final List<MemberReader> contents = new ArrayList<>();
	private final String classname;
	private final int signature;
	
	public StructureReader(String classname, int signature) {
		this.classname = classname;
		this.signature = signature;
	}
	
	public void addMember(MemberReader member) {
		contents.add(member);
		updateSize(member);
	}

	private void updateSize(MemberReader member) {
		setSize((int) (member.getOffset() + member.getSize()));
	}

	public Node read(long position, Document document, HKXConnector connector, String name) throws IOException {
		Element res = document.createElement("hkobject");
		if(!name.isEmpty()) {
			LoggerUtil.info("STRUCT", "CLASS", "OPEN", classname);
			res.setAttribute("name", name);
			res.setAttribute("class", classname);
			res.setAttribute("signature", "0x" + Integer.toHexString(signature));
		}
		for(MemberReader memReader : contents) {
			try {
				Node memberNode = memReader.read(position, document, connector);
				if(memberNode != null)
					res.appendChild(memberNode);
				else
					LoggerUtil.error("CLASS", "MEMBR", "NULL", "Received Null member");
			} catch (InvalidPositionException e) {
				res.appendChild(document.createComment(" could not read member : " + memReader.getName() + " /// INVALID DAT POS "));
				LoggerUtil.error("CLASS", "MEMBR", "READ", "Invalid data pos : " + e.getMessage());
			} catch (InvalidArrayException e) {
				res.appendChild(document.createComment(" could not read member : " + memReader.getName() + " /// INVALID ARRAY "));
				LoggerUtil.error("CLASS", "MEMBR", "ARR", "Invalid array : " + e.getMessage());
			}
		}
		return res;
	}
}
