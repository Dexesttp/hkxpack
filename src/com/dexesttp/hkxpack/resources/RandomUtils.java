package com.dexesttp.hkxpack.resources;

public class RandomUtils {
	public static String makeFromFileName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf(".")) + ".xml";
	}
}
