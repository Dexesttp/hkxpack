package com.dexesttp.hkxpack.commons.resolver;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.w3c.dom.DOMException;

import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;

public interface Resolver<T> {
	public long getPos();

	public T solve(RandomAccessFile file) throws IOException, DOMException, UninitializedHKXException, UnresolvedMemberException;
}
