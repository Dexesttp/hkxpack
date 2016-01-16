package com.dexesttp.hkxpack.commons.parser;

import java.io.IOException;

/**
 * Read several constant format data chunks from the given file.
 * Implement readData to read the structure (the file is at the right position already).
 * Implement getEntitySize (after resolution of T) to renseign the T structure size in bytes.
 * @author DexesTTP
 */
public abstract class FixedReader<T> extends AbstractReader<T> {
	protected long arrayPos = 0;

	@Override
	public T read() throws IOException {
		long resolvedPos = position + arrayPos * getEntitySize();
		if(resolvedPos > position + size - getEntitySize())
			return null;
		arrayPos++;
		file.seek(resolvedPos);
		return readData();
	}

	protected abstract T readData() throws IOException;

	protected abstract long getEntitySize();
}
