package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.function.Function;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.PtrMember;

public enum PtrMemberResolver {
	TYPE_POINTER(8, (file) -> {
			return null;
		}),
	TYPE_FUNCTIONPOINTER(8, (file) -> {
			return "This have never happened. Contact the dev please.";
		}),
	TYPE_STRINGPTR(8, (file) -> {
			return null;
		}),
	// TODO check that <everyday i'm shuffling>
	TYPE_QSTRANSFORM(96, (file) -> {
			return null;
		});
	
	private final int size;
	private final Function<RandomAccessFile, String> action;

	private PtrMemberResolver(int size, Function<RandomAccessFile, String> action) {
		this.size = size;
		this.action = action;
	}

	private PtrMemberResolver(Function<RandomAccessFile, String> action) {
		size = 0;
		this.action = action;
	}
	
	public ResolvedMember resolve(ResolvedMember resolvedMember, String name) {
		return new PtrMember<ResolvedMember>(name) {
			@Override
			public long getSize() {
				return size;
			}

			@Override
			public Resolver<Node> getResolver(HKXHandler handler) throws IOException, UnresolvedMemberException, UninitializedHKXException {
				return null;
			}
		};
	}
}
