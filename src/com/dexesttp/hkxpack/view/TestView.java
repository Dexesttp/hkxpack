package com.dexesttp.hkxpack.view;

import com.dexesttp.hkxpack.Main;
import com.dexesttp.hkxpack.resources.ClassFilesUtils;

public class TestView {
	public static void main(String[] args) {
		Main main = new Main();
		String fileName = "D:\\Documents\\SANDBOX\\FO4\\Idle.hkx";
		String outName = "D:\\Documents\\SANDBOX\\FO4\\OutFile.xml";
		ClassFilesUtils.setFolder("D:\\Documents\\SANDBOX\\FO4\\classDefs");
		ClassFilesUtils.initFolder();
		main.exec(fileName, outName);
	}
}
