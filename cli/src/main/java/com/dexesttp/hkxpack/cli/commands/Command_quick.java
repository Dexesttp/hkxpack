package com.dexesttp.hkxpack.cli.commands;

import java.io.File;
import java.util.logging.Logger;

import com.dexesttp.hkxpack.cli.ConsoleView;

/**
 * Routes an unknown command to either a help, pack or unpack command.
 */
public class Command_quick implements Command {
	private static final Logger LOGGER = Logger.getLogger(ConsoleView.class.getName());
	private static final int MINIMUM_PARAMETERS_COUNT = 1;

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int execute(final String... parameters) {
		if (parameters.length > MINIMUM_PARAMETERS_COUNT) {
			return new Command_help().execute(parameters);
		}
		Command command = null;
		String inName = parameters[0];
		File inFile = new File(inName);
		String name = inFile.getName();
		String commandArg = "";
		try {
			if (!inFile.isFile()) {
				throw new IllegalArgumentException("The given parameter isn't a HKX or XML file.");
			}
			String ext = name.substring(name.lastIndexOf('.') + 1);
			if (ext.equals("hkx")) {
				command = new Command_unpack();
				commandArg = "unpack";
			} else if (ext.equals("xml")) {
				command = new Command_pack();
				commandArg = "pack";
			} else {
				throw new IllegalArgumentException("Unsupported file type.");
			}
		} catch (IllegalArgumentException e) {
			LOGGER.throwing(this.getClass().getName(), "execute", e);
			return 1;
		}
		return command.execute(commandArg, inName);
	}

}
