package com.dexesttp.hkxpack.hkx.classes;

import java.util.HashMap;
import java.util.Map;

public class PointerResolver {
	private Map<Long, String> nameAssociator = new HashMap<>();
	private int currentID = 1;
	public String get(long position) {
		if(nameAssociator.containsKey(position))
			return nameAssociator.get(position);
		String newName = "#" + String.format("%04d", currentID);
		nameAssociator.put(position, newName);
		currentID++;
		return newName;
	}
	
}
