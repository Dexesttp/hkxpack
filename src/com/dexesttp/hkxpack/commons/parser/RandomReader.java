package com.dexesttp.hkxpack.commons.parser;

import java.io.IOException;

import com.dexesttp.hkxpack.commons.resolver.Definer;
import com.dexesttp.hkxpack.commons.resolver.Resolver;

public class RandomReader<T> extends AbstractReader<T> implements Definer<T> {
	Resolver<? extends T> resolver;
	
	@Override
	public T read() throws IOException {
		try {
			long newPos = position + resolver.getPos();
			file.seek(newPos);
			return resolver.solve(file);
		}
		catch(IOException e) {
			throw e;
		}
		catch(Exception e) {
			throw new IOException(e);
		}
	}

	@Override
	public void setNext(Resolver<? extends T> resolver) {
		this.resolver = resolver;
	}

}
