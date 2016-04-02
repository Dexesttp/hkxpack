package com.dexesttp.hkxpack.resources;

import java.util.ArrayList;
import java.util.List;

public class LoggerUtil {
	private static List<Throwable> eList = new ArrayList<>();

	public static void add(Throwable e) {
		eList.add(e);
	}

	public static void addNewException(String message) {
		eList.add(new Exception(message));
	}

	public static List<Throwable> getList() {
		return eList;
	}
}
