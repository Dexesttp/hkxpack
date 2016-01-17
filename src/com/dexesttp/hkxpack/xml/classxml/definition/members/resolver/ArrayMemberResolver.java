package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.definition.DoubleLink;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.DataReader;
import com.dexesttp.hkxpack.hkx.reader.InternalLinkReader;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.ArrayMember;

public enum ArrayMemberResolver {
	TYPE_ARRAY(4),
	TYPE_SIMPLEARRAY(4),
	TYPE_INPLACEARRAY(4);
	
	private final int size;

	private ArrayMemberResolver(int size) {
		this.size = size;
	}
	
	public ResolvedMember resolve(ResolvedMember resolvedMember, String name, String classname) {
		return new ArrayMember<ResolvedMember> (name, classname){
			@Override
			public long getSize() {
				return size;
			}

			@Override
			public Resolver<Node> getResolver(HKXHandler hkxHandler) throws IOException, UnresolvedMemberException, UninitializedHKXException {
				InternalLinkReader reader = hkxHandler.getInternalLinkReader();
				DoubleLink link = reader.read();
				if(link == null)
					return null;
				long outerArraySizePtr = ByteUtils.getLong(link.from);
				Element outerArrayRoot = hkxHandler.getDocument().createElement("hkparam");
				outerArrayRoot.setAttribute("name", name);
				return new Resolver<Node>() {
					private final HKXHandler handler = hkxHandler;
					private final ResolvedMember resolver = resolvedMember;
					private final long arraySizePtr = outerArraySizePtr;
					private final Element arrayRoot = outerArrayRoot;
					@Override
					public long getPos() {
						return arraySizePtr;
					}

					@Override
					public Node solve(RandomAccessFile file) throws IOException, DOMException, UninitializedHKXException, UnresolvedMemberException {
						final DataReader reader = handler.getDataReader();
						byte[] voidData = new byte[8];
						byte[] arraySizeData = new byte[4];
						byte[] arraySizeDataWithWeird80AtTheEnd = new byte[4];
						file.read(voidData);
						file.read(arraySizeData);
						file.read(arraySizeDataWithWeird80AtTheEnd);
						long arraySize = ByteUtils.getInt(arraySizeData);
						arrayRoot.setAttribute("numelements", ""+arraySize);
						for(int i= 0; i < arraySize; i++) {
							Resolver<Node> nodeResolver = resolver.getResolver(handler);
							if(nodeResolver != null) {
								reader.setNext(nodeResolver);
								Node arrayNode = reader.read();
								arrayRoot.appendChild(arrayNode);
							}
						}
						return arrayRoot;
					}
					
				};
			}
		};
	}
}
