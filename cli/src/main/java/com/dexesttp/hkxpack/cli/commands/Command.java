package com.dexesttp.hkxpack.cli.commands;

/**
 * A command is a routed behavior of the Command Line Interface. It should be returned only by a {@link CommandFactory}.
 */
public interface Command {
	/**
	 * Executes the command.
	 * @param parameters all the command-line arguments
	 * @return the execution result value
	 */
	public int execute(String... parameters);
}
