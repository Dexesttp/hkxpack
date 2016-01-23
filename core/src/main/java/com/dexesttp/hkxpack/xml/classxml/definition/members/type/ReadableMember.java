package com.dexesttp.hkxpack.xml.classxml.definition.members.type;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.structs.DataInterface;
import com.dexesttp.hkxpack.hkx.structs.Member;
import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.reader.BaseMemberReader;
import com.dexesttp.hkxpack.xml.classxml.exceptions.NonResolvedClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownClassException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnknownEnumerationException;
import com.dexesttp.hkxpack.xml.classxml.exceptions.UnsupportedCombinaisonException;

public class ReadableMember extends ClassXMLMember {
	private final BaseMemberReader resolver;
	private final long offset;
	private final String flag;
	
	public ReadableMember(String name, String classname, long offset, String flag, BaseMemberReader reader) {
		super(name, classname);
		this.offset = offset;
		this.flag = flag;
		resolver = reader;
	}

	public Member getStruct() {
		return new Member(resolver.getSize(), offset);
	}

	public Node read(Document document, byte[] toRead, DataInterface data, Data1Interface data1, Data2Interface data2) throws IOException, InvalidPositionException, UnsupportedCombinaisonException, UnknownEnumerationException, NonResolvedClassException, UnknownClassException {
		if(flag.equals("SERIALIZE_IGNORED") && DisplayProperties.ignoreSerialized)
			return document.createComment(" " + name + " SERIALIZE_IGNORED ");
		try {
			return resolver.readDirect(document, toRead, data, data1, data2);
		} catch(InvalidPositionException e) {
			//e.printStackTrace();
			if(DisplayProperties.displayDebugInfo)
				System.err.println("[MEM]\t[ERR]\t\t" + e.getMessage());
			return document.createComment(" failed to read member " + name + " ");
		}
	}
}
