package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.classes.PointerResolver;
import com.dexesttp.hkxpack.hkx.definition.TripleLink;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.TripleLinkReader;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.PtrMember;

public enum PtrMemberResolver {
	TYPE_POINTER(8),
	TYPE_FUNCTIONPOINTER(8);
	
	private final int size;

	private PtrMemberResolver(int size) {
		this.size = size;
	}
	
	public ResolvedMember resolve(ResolvedMember resolvedMember, String name, String classname) {
		return new PtrMember<ResolvedMember>(name, classname) {
			@Override
			public long getSize() {
				return size;
			}

			@Override
			public Resolver<Node> getResolver(HKXHandler handler) throws IOException, UnresolvedMemberException, UninitializedHKXException {
				TripleLinkReader reader = handler.getExternalLinkReader();
				PointerResolver resolver = handler.getPtrResolver();
				TripleLink link = reader.read();
				if(link == null)
					return null;
				long position = ByteUtils.getLong(link.to);
				String refName = resolver.get(position);
				Element fixedNode = handler.getDocument().createElement("hkparam");
				Node textNode = handler.getDocument().createTextNode(refName);
				fixedNode.setAttribute("name", name);
				fixedNode.appendChild(textNode);
				return new Resolver<Node>() {
					private final Node intNode = fixedNode;

					@Override
					public long getPos() {
						return 0;
					}

					@Override
					public Node solve(RandomAccessFile file) throws IOException {
						// NO OP
						return intNode;
					}
				};
			}
		};
	}
}
