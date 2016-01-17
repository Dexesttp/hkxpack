package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.function.Function;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.definition.DoubleLink;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.InternalLinkReader;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.EnumMember;

public enum EnumMemberResolver {
	TYPE_ENUM(8, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_FLAGS(8, (value) -> {return ""+ByteUtils.getInt(value);});
	
	private final int size;
	private final Function<byte[], String> action;

	private EnumMemberResolver(int size, Function<byte[], String> action) {
		this.size = size;
		this.action = action;
	}
	
	public ResolvedMember resolve(ResolvedMember resolved, String ename, String name) {
		return new EnumMember(name) {
			@Override
			public long getSize() {
				return size;
			}
			
			@Override
			public Resolver<Node> getResolver(HKXHandler handler) throws IOException, UnresolvedMemberException, UninitializedHKXException {
				InternalLinkReader links = handler.getInternalLinkReader();
				DoubleLink link = links.read();
				if(link == null)
					return null;
				return new Resolver<Node>() {
					final Document document = handler.getDocument();
					final long pos = ByteUtils.getLong(link.from);
					@Override
					public long getPos() {
						return pos;
					}

					@Override
					public Node solve(RandomAccessFile file) throws IOException {
						final byte[] bytes = new byte[size];
						file.read(bytes);
						String value = action.apply(bytes);
						Element node = document.createElement("hkparam");
						node.setAttribute("name", name);
						node.setAttribute("type", "enum");
						node.appendChild(document.createTextNode(value));
						return node;
					}
				};
			}

		};
	}
}
