package com.dexesttp.hkxpack.commons.parser;

import java.io.IOException;

/**
 * Read a constant structure.
 * Implement readData to read the structure (the file is at the right position already).
 * @author DexesTTP
 * @param <T> the return type
 */
public abstract class ConstantReader<T> extends AbstractReader<T> {
	@Override
	public T read() throws IOException {
		file.seek(position);
		return readData();
	}

	protected abstract T readData() throws IOException;
}
