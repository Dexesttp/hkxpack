package com.dexesttp.hkxpack.cli.utils;

public class RandomUtils {
	public static String makeFromFileName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf(".")) + ".xml";
	}
	public static String unmakeFromFileName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf(".")) + ".hkx";
	}
}
