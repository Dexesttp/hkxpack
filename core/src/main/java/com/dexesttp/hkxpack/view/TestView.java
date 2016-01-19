package com.dexesttp.hkxpack.view;

import java.io.IOException;

import com.dexesttp.hkxpack.Main;
import com.dexesttp.hkxpack.resources.ClassFilesUtils;
import com.dexesttp.hkxpack.resources.RandomUtils;

public class TestView {
	public static void main(String[] args) {
		Main main = new Main();
		String fileName = "D:\\Documents\\SANDBOX\\FO4\\BloatflyRootBehavior.hkx";
		String outName =  RandomUtils.makeFromFileName(fileName);
		try {
			ClassFilesUtils.initFolder();
		} catch (IOException e) {
			System.err.println("Error while reading class list.");
			e.printStackTrace();
			System.exit(1);
		}
		main.exec(fileName, outName);
	}
}
