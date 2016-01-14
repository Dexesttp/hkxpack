package com.dexesttp.hkxpack.commons.resolver;

public interface Resolver<T> {
	long getPos();

	long getLen();

	T solve(byte[] b);
}
