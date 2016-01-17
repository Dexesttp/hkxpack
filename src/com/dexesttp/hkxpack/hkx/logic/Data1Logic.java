package com.dexesttp.hkxpack.hkx.logic;

import java.io.IOException;
import java.util.LinkedList;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.InternalLinkReader;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;

public class Data1Logic {

	protected InternalLinkReader reader;

	public Data1Logic(InternalLinkReader data1reader) {
		this.reader = data1reader;
	}

	public void resolve(HKXHandler handler) throws UninitializedHKXException, IOException, UnresolvedMemberException {
		final LinkedList<ClassXML> instances = handler.getInstanceList();
		ClassXML instance;
		while((instance = instances.poll()) != null) {
			for(ClassXMLMember member : instance.getMembers()) {
				Resolver<Node> resolver = member.getResolver(handler);
				//reader.setNext(member.getResolver(handler));
				//reader.read();
			}
		}
	}
}
