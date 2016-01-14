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
			byte[] b = new byte[(int) resolver.getLen()];
			file.seek(newPos);
			file.read(b);
			return resolver.solve(b);
		}
		catch(IOException e) {
			throw e;
		}
		catch(Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void setNext(Resolver<? extends T> resolver) {
		this.resolver = resolver;
	}

}
