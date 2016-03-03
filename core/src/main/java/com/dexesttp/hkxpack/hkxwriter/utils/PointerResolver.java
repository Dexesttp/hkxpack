package com.dexesttp.hkxpack.hkxwriter.utils;

import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxpack.hkx.data.DataExternal;

public class PointerResolver {
	private Map<String, Long> map = new HashMap<>();
	
	public void add(String name, long position) {
		map.put(name, position);
	}
	
	public DataExternal resolve(PointerObject object) {
		if(!map.keySet().contains(object.to))
			return null;
		DataExternal res = new DataExternal();
		res.section = 0x02;
		res.from = object.from;
		res.to = map.get(object.to);
		return res;
	}
}
