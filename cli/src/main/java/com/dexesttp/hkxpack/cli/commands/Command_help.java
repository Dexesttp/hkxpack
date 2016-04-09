package com.dexesttp.hkxpack.cli.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dexesttp.hkxpack.cli.ConsoleView;
import com.dexesttp.hkxpack.cli.utils.StaticProperties;

/**
 * Displays the help of the Command Line Interface.
 */
public class Command_help implements Command {
	private static final Logger LOGGER = Logger.getLogger(ConsoleView.class.getName());
	@Override
	/**
	 * {@inheritDoc}
	 */
	// TODO prettify help.
	public int execute(final String... parameters) {
		if(LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("HKXPack version " + StaticProperties.getVersionNumber() );
			LOGGER.info("Use : java -jar hkxpack-cli.jar <args>");
			LOGGER.info("Arguments :");
			LOGGER.info("\t"+"unpack"	+"\t" +"<filename>" + "\t\t" + "Extracts <filename>.hkx into <filename>.xml");
			LOGGER.info("\t\t\t" +"-o <outputfile>" + "\t" + "Set the output file");
			LOGGER.info("\t"+"pack"	+"\t" +"<filename>" + "\t\t" + "Compress <filename>.xml into <filename>.hkx");
			LOGGER.info("\t\t\t" +"-o <outputfile>" + "\t" + "Set the output file");
			LOGGER.info("\t"+"help"		+"\t\t\t\t"+ "Show this window");
			LOGGER.info("");
			LOGGER.info("Report bugs or findings at github.com/dexesttp/hkxpack");
		}
		return 0;
	}
}
