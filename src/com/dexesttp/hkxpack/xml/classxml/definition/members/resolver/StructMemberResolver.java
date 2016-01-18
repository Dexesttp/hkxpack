package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.DataReader;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLList;
import com.dexesttp.hkxpack.xml.classxml.ClassXMLReader;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.StructMember;

public enum StructMemberResolver {
	TYPE_STRUCT(8);
	
	private final int size;

	private StructMemberResolver(int size) {
		this.size = size;
	}
	
	public ResolvedMember resolve(String extClassName, String name, String baseClassName) throws IOException {
		return new StructMember(name, baseClassName) {
			final String className = extClassName;
			@Override
			public long getSize() {
				return size;
			}

			@Override
			public Resolver<Node> getResolver(HKXHandler hkxHandler) throws IOException, UnresolvedMemberException, UninitializedHKXException {
				ClassXMLList classXMLList = ClassXMLList.getInstance();
				ClassXML contentClass = classXMLList.get(className);
				if(contentClass == null) {
					classXMLList.addClass(ClassXMLReader.getClassFromFile(className, 0));
					classXMLList.resolve();
					contentClass = classXMLList.get(className);
				}
				final ClassXML contentClassFinal = contentClass;
				final Node outerStructRoot = hkxHandler.getDocument().createElement("hkobject");
				return new Resolver<Node>() {
					private final HKXHandler handler = hkxHandler;
					private final Node structRoot = outerStructRoot;
					private final ClassXML content = contentClassFinal;

					@Override
					public long getPos() {
						return 0;
					}

					@Override
					public Node solve(RandomAccessFile file)
							throws IOException, DOMException, UninitializedHKXException, UnresolvedMemberException {
						DataReader reader = handler.getDataReader();
						for(ClassXMLMember member : content.getMembers()) {
							Resolver<Node> resolver = member.getResolver(handler);
							if(resolver != null) {
								reader.setNext(resolver);
								Node childNode = reader.read();
								structRoot.appendChild(childNode);
							}
						}
						return structRoot;
					}
				};
			}
		};
	}
}
