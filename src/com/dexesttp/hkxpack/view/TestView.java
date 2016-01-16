package com.dexesttp.hkxpack.view;

import com.dexesttp.hkxpack.Main;
import com.dexesttp.hkxpack.resources.FileUtils;

public class TestView {
	public static void main(String[] args) {
		Main main = new Main();
		String fileName = "D:\\Documents\\SANDBOX\\FO4\\BloatflyRootBehavior.hkx";
		FileUtils.setFolder("D:\\Documents\\SANDBOX\\FO4\\classDefs");
		FileUtils.initFolder();
		main.exec(fileName);
	}
}
