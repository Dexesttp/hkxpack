package com.dexesttp.hkxpack.cli.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
	@SuppressWarnings("rawtypes")
	private Map<String, Class> commandParser = new HashMap<>();
	{
		commandParser.put("extract", Command_unpack.class);
		commandParser.put("unpack", Command_unpack.class);
		commandParser.put("compress", Command_pack.class);
		commandParser.put("pack", Command_pack.class);
		commandParser.put("help", Command_help.class);
	}
	
	@SuppressWarnings("rawtypes")
	public Command newInstance(String commandName) {
		Class commandClass = commandParser.get(commandName);
		try {
			return (Command) commandClass.newInstance();
		} catch (Exception e) {
			return new Command_quick();
		}
	}
}
