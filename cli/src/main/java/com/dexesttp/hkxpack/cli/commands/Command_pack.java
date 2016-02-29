package com.dexesttp.hkxpack.cli.commands;

import com.dexesttp.hkxpack.Main;
import com.dexesttp.hkxpack.cli.utils.RandomUtils;

public class Command_pack implements Command{
	@Override
	public int execute(String... parameters) {
		Main main = new Main();
		if(parameters.length < 2) {
			System.err.println("No filename given.");
			return 1;
		}
		String fileName = parameters[1];
		String outName = "";
		if(parameters.length <= 2 || parameters[2].equals("-o")) {
			try {
				outName = RandomUtils.unmakeFromFileName(fileName);
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
		main.write(fileName, outName);
		return 0;
	}
}
