package com.dexesttp.hkxpack.resources;

import java.util.HashMap;
import java.util.Map;

public class PointerNameGiver {
	private static PointerNameGiver instance;

	private PointerNameGiver() {
	}

	public static PointerNameGiver getInstance() {
		if(instance == null)
			instance = new PointerNameGiver();
		return instance;
	}

	public static void resetInstance() {
		instance = new PointerNameGiver();
	}
	
	private Map<Long, String> names = new HashMap<>();
	public String getName(long position) {
		if(!names.containsKey(position)) {
			String newName = nextName();
			LoggerUtil.info("RES", "NAME", "CRE", "{" + position + "}\t[VAL]\t" + newName);
			names.put(position, newName);
			return newName;
		}
		LoggerUtil.info("RES","NAME","REQ", "@{" + position + "}");
		return names.get(position);
	}

	private int nameID = 90;
	private String nextName() {
		return "#" + nameID++;
	}
}
