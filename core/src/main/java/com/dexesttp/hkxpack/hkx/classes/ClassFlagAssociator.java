package com.dexesttp.hkxpack.hkx.classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ClassFlagAssociator {
	private Map<Long, Long> content = new HashMap<>();
	
	public void add(long flagPos, long classPos) {
		content.put(flagPos,  classPos);
	}
	
	public long getFlag(long classPos) {
		for(Entry<Long, Long> entry : content.entrySet()) {
			if(entry.getValue().equals(classPos))
				return entry.getKey();
		}
		return -1;
	}
	
	public long getClass(long flagPos) {
		return content.get(flagPos);
	}

	public Collection<Long> getNamesIterator() {
		return content.values();
	}

}
