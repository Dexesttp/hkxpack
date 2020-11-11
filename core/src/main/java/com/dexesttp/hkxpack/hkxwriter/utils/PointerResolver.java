package com.dexesttp.hkxpack.hkxwriter.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.dexesttp.hkxpack.hkx.data.DataExternal;

/**
 * Resolves a pointer into an address
 */
public class PointerResolver {
	private final transient Map<String, Long> map = new HashMap<>();

	/**
	 * Add a new known pointer.
	 * 
	 * @param name     the name of the object.
	 * @param position the position the object was written at.
	 */
	public void add(final String name, final long position) {
		map.put(name, position);
	}

	/**
	 * Resolves a pointer.
	 * 
	 * @param object the {@link PointerObject} to resolve.
	 * @return the resolved and filled {@link DataExternal}, or "null" if the
	 *         resolution failed.
	 */
	public Optional<DataExternal> resolve(final PointerObject object) {
		if (!map.keySet().contains(object.to)) {
			return Optional.empty();
		}
		DataExternal res = new DataExternal();
		res.section = 0x02;
		res.from = object.from;
		res.to = map.get(object.to);
		return Optional.of(res);
	}
}
