package com.dexesttp.hkxpack.cli;

import com.dexesttp.hkxpack.Main;
import com.dexesttp.hkxpack.cli.utils.RandomUtils;
import com.dexesttp.hkxpack.resources.DisplayProperties;

public class TestView {
	public static void main(String[] args) {
		read(args);
	}
	
	public static void read(String[] args) {
		Main main = new Main();
		String fileName = "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\DeathChest01.hkx";
		String outName =  RandomUtils.makeFromFileName(fileName);
		DisplayProperties.displayDebugInfo = true;
		DisplayProperties.displayFileDebugInfo = true;
		DisplayProperties.displayReadTypesInfo = true;
		DisplayProperties.displayClassImportsInfo = true;
		DisplayProperties.displayEmbeddedData = true;
		main.read(fileName, outName);
	}
	
	public static void write(String[] args) {
		Main main = new Main();
		String fileName = "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\Idle.xml";
		String outName = "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\Idle-out.hkx";
		//String outName = "";
		DisplayProperties.displayDebugInfo = true;
		DisplayProperties.displayFileDebugInfo = true;
		DisplayProperties.displayReadTypesInfo = true;
		DisplayProperties.displayClassImportsInfo = true;
		DisplayProperties.displayEmbeddedData = true;
		
		main.write(fileName, outName);
	}
}
