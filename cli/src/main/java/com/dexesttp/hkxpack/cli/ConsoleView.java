package com.dexesttp.hkxpack.cli;

import com.dexesttp.hkxpack.cli.commands.Command;
import com.dexesttp.hkxpack.cli.commands.CommandFactory;
import com.dexesttp.hkxpack.cli.commands.Command_help;

/**
 * Entry point for the Command Line Interface.
 */
public final class ConsoleView {
	private static final int MINIMUM_ARG_COUNT = 1;
	private ConsoleView() {
		// NO OP
	}
	
	/**
	 * Entry point for the console
	 * @param args the console arguments.
	 */
	public static void main(final String... args) {
		// Set the logging properties
		System.setProperty("java.util.logging.SimpleFormatter.format", 
            "[%4$s] %5$s%n");
		Command command;
		if(args.length < MINIMUM_ARG_COUNT) {
			command = new Command_help();
		}
		else {
			command = new CommandFactory().newInstance(args[0]);
		}
		command.execute(args);
	}
}
