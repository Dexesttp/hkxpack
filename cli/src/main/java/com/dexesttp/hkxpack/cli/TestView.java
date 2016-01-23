package com.dexesttp.hkxpack.cli;

import java.io.IOException;

import com.dexesttp.hkxpack.Main;
import com.dexesttp.hkxpack.cli.utils.RandomUtils;
import com.dexesttp.hkxpack.resources.ClassFilesUtils;
import com.dexesttp.hkxpack.resources.Properties;

public class TestView {
	public static void main(String[] args) {
		Main main = new Main();
		String fileName = "D:\\Documents\\SANDBOX\\FO4\\Locomotion.hkx";
		//String outName = "";
		String outName =  RandomUtils.makeFromFileName(fileName);
		Properties.displayDebugInfo = true;
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
