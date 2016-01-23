package com.dexesttp.hkxpack.cli.commands;

public interface Command {
	/**
	 * Executes the command.
	 * @param parameters the command-line arguments.
	 * @return the execution state
	 */
	public int execute(String... parameters);
}
