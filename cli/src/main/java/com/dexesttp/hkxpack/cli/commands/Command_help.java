package com.dexesttp.hkxpack.cli.commands;

import com.dexesttp.hkxpack.cli.utils.StaticProperties;

/**
 * Displays the help of the Command Line Interface.
 */
public class Command_help implements Command {
	// TODO prettify help.
	@Override
	public int execute(String... parameters) {
		System.out.println("HKXPack version " + StaticProperties.getVersionNumber() );
		System.out.println("Use : java -jar hkxpack-cli.jar <args>");
		System.out.println("Arguments :");
		System.out.println("\t"+"unpack"	+"\t" +"<filename>" + "\t\t" + "Extracts <filename>.hkx into <filename>.xml");
		System.out.println("\t\t\t" +"-o <outputfile>" + "\t" + "Set the output file");
		System.out.println("\t"+"pack"	+"\t" +"<filename>" + "\t\t" + "Compress <filename>.xml into <filename>.hkx");
		System.out.println("\t\t\t" +"-o <outputfile>" + "\t" + "Set the output file");
		System.out.println("\t"+"help"		+"\t\t\t\t"+ "Show this window");
		System.out.println();
		System.out.println("Report bugs or findings at github.com/dexesttp/hkxpack");
		return 0;
	}
}
