package com.dexesttp.hkxpack.cli.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * Routes a command line interface into the relevant {@link Command}.
 */
public class CommandFactory {
	/**
	 * List of commands associated with the class to execute on read
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Class> commandParser = new HashMap<>();
	{
		commandParser.put("extract", Command_unpack.class);
		commandParser.put("unpack", Command_unpack.class);
		commandParser.put("compress", Command_pack.class);
		commandParser.put("pack", Command_pack.class);
		commandParser.put("help", Command_help.class);
	}

	/**
	 * Retrieves the relevant {@link Command}. 
	 * @param commandName the first argument passed to main.
	 * @return the relevant {@link Command}.
	 */
	public Command newInstance(String commandName) {
		@SuppressWarnings("rawtypes")
		Class commandClass = commandParser.get(commandName);
		try {
			return (Command) commandClass.newInstance();
		} catch (Exception e) {
			return new Command_quick();
		}
	}
}
