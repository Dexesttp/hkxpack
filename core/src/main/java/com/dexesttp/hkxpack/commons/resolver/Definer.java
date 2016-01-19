package com.dexesttp.hkxpack.commons.resolver;

public interface Definer<T> {
	/**
	 * Set the resolver for the next defined item.
	 * @param resolver
	 */
	public void setNext(Resolver<? extends T> resolver);
}
