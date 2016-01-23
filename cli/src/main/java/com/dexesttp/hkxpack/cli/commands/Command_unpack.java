package com.dexesttp.hkxpack.cli.commands;

import java.io.IOException;

import com.dexesttp.hkxpack.Main;
import com.dexesttp.hkxpack.cli.utils.RandomUtils;
import com.dexesttp.hkxpack.resources.ClassFilesUtils;

public class Command_unpack implements Command{
	@Override
	public int execute(String... parameters) {
		Main main = new Main();
		if(parameters.length < 2) {
			System.err.println("No filenamme given.");
			return 1;
		}
		String fileName = parameters[1];
		String outName = "";
		if(parameters[2].equals("-o")) {
			try {
				outName = RandomUtils.makeFromFileName(fileName);
			}
			catch(Exception e) {
				System.err.println("Invalid filename !");
				return 1;
			}
		} else {
			if(parameters.length < 3) {
				System.err.println("No output filename given.");
				return 1;
			}
			outName = parameters[3];
		}
		try {
			ClassFilesUtils.initFolder();
		} catch (IOException e) {
			System.err.println("Error while reading class list.");
			e.printStackTrace();
			return 1;
		}
		main.exec(fileName, outName);
		return 0;
	}
}
