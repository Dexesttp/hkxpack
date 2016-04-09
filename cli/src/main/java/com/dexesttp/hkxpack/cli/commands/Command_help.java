package com.dexesttp.hkxpack.cli.commands;

import java.io.PrintStream;

import com.dexesttp.hkxpack.cli.utils.StaticProperties;

/**
 * Displays the help of the Command Line Interface.
 */
public class Command_help implements Command {
	@Override
	/**
	 * {@inheritDoc}
	 */
	// TODO prettify help.
	public int execute(final String... parameters) {
		PrintStream out = System.out;
		out.println("HKXPack version " + StaticProperties.getVersionNumber() );
		out.println("Use : java -jar hkxpack-cli.jar <args>");
		out.println("Arguments :");
		out.println("\t"+"unpack"	+"\t" +"<filename>" + "\t\t" + "Extracts <filename>.hkx into <filename>.xml");
		out.println("\t\t\t" +"-o <outputfile>" + "\t" + "Set the output file");
		out.println("\t"+"pack"	+"\t" +"<filename>" + "\t\t" + "Compress <filename>.xml into <filename>.hkx");
		out.println("\t\t\t" +"-o <outputfile>" + "\t" + "Set the output file");
		out.println("\t"+"help"		+"\t\t\t\t"+ "Show this window");
		out.println();
		out.println("Report bugs or findings at github.com/dexesttp/hkxpack");
		return 0;
	}
}
