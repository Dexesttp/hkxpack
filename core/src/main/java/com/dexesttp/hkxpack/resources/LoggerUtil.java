package com.dexesttp.hkxpack.resources;

import java.util.ArrayList;
import java.util.List;

public class LoggerUtil {
	private static List<Throwable> eList = new ArrayList<>();
	// Log types
	public static void add(Throwable e) {
		eList.add(e);
	}
	
	public static List<Throwable> getList() {
		return eList;
	}
}
