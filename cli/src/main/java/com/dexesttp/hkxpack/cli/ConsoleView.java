package com.dexesttp.hkxpack.cli;

import java.io.IOException;

import com.dexesttp.hkxpack.Main;
import com.dexesttp.hkxpack.cli.utils.RandomUtils;
import com.dexesttp.hkxpack.resources.ClassFilesUtils;

public class ConsoleView {
	private static String version_number = "v0.0.1-alpha";

	public static void main(String[] args) {
		Main main = new Main();
		if(args.length < 1)
			showHelp();
		if(args[0].equals("-h"))
			showHelp();
		String fileName = args[0];
		String outName = "";
		try {
			outName = RandomUtils.makeFromFileName(fileName);
		}
		catch(Exception e) {
			System.err.println("Invalid filename !");
			System.exit(1);
		}
		try {
			ClassFilesUtils.initFolder();
		} catch (IOException e) {
			System.err.println("Error while reading class list.");
			e.printStackTrace();
			System.exit(1);
		}
		main.exec(fileName, outName);
	}

	private static void showHelp() {
		System.out.println("hkxpack version " + version_number );
		System.out.println("Use : java -jar hkpack.jar <filename>.hkx");
		System.out.println("Report bugs or findings at github.com/dexesttp/hkxpack");
		System.exit(0);
	}
}
