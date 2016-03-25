package com.dexesttp.hkxpack.cli;

import com.dexesttp.hkxpack.cli.commands.Command;
import com.dexesttp.hkxpack.cli.commands.CommandFactory;
import com.dexesttp.hkxpack.cli.commands.Command_help;

/**
 * Entry point for the Command Line Interface.
 */
public class ConsoleView {
	public static void main(String[] args) {
		Command command;
		if(args.length < 1)
			command = new Command_help();
		else
			command = new CommandFactory().newInstance(args[0]);
		command.execute(args);
	}
}
