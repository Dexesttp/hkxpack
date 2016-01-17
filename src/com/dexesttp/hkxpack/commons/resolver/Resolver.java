package com.dexesttp.hkxpack.commons.resolver;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface Resolver<T> {
	public long getPos();

	public T solve(RandomAccessFile file) throws IOException;
}
