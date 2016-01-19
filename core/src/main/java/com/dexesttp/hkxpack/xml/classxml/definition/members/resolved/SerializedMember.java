package com.dexesttp.hkxpack.xml.classxml.definition.members.resolved;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;

public class SerializedMember extends ResolvedMember  {

	public SerializedMember(String name, String classname) {
		super(name, classname);
	}

	@Override
	public long getSize() {
		return 0;
	}

	@Override
	public Resolver<Node> getResolver(HKXHandler handler)
			throws IOException, UnresolvedMemberException, UninitializedHKXException {
		Document doc = handler.getDocument();
		Node fixedNode = doc.createComment(" " + name + " SERIALIZE_IGNORED ");
		return new Resolver<Node>() {
			private final Node intNode = fixedNode;
			@Override
			public long getPos() {
				return 0;
			}
			@Override
			public Node solve(RandomAccessFile file) throws IOException {
				return intNode;
			}
			
		};
	}

}
