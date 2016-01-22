package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.RandomAccessFile;
import java.util.function.Function;

import com.dexesttp.hkxpack.resources.ByteUtils;

public enum StringMemberResolver implements BaseMemberResolver {
	TYPE_CSTRING((file) -> {
		try {
			return ByteUtils.readString(file);
		} catch(Exception e) {
			return "";
		}
	}),
	TYPE_STRINGPTR((file) -> {
		try {
			return ByteUtils.readString(file);
		} catch(Exception e) {
			return "";
		}
	});
	
	@Override
	public int getSize() {
		return 0;
	}
	
	private final Function<RandomAccessFile, String> action;
	private StringMemberResolver(Function<RandomAccessFile, String> action) {
		this.action = action;
	}
	public void resolve(String name, String classname) {
		// TODO implement String resolution.
	}

}
