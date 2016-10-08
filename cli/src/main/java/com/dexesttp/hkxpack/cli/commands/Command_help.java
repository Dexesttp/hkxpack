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
		boolean verbose = false;
		if(parameters.length >= 2 && parameters[1].equals("-v")) {
			verbose = true;
		}
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n");
		LOGGER.setLevel(Level.INFO);
		
		if(LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("HKXPack version " + StaticProperties.getVersionNumber() );
			LOGGER.info("Use : java -jar hkxpack-cli.jar <args>");
			LOGGER.info("Arguments :");
			LOGGER.info("\t" + "unpack"	+ "\t" + "<filename>\t" + "Extracts <filename>.hkx into <filename>.xml");
			LOGGER.info("\t" + "pack"	+ "\t" + "<filename>\t" + "Compress <filename>.xml into <filename>.hkx");
			LOGGER.info("\t" + "help"	+ "\t\t\t"+ "Show this window");
			LOGGER.info("Options :");
			LOGGER.info("\t" +"-q\t\t" + "Quiet output");
			LOGGER.info("\t" +"-v\t\t" + "Verbose output");
			LOGGER.info("\t" +"-o <outputfile>\t" + "Set the output file");
			LOGGER.info("Advanced options :");
			if(verbose) {
				LOGGER.info("\t" +"-d\t\t" + "Debug output");
				LOGGER.info("\t" +"-t <number>" + "\t" + "Set the maximum numbers of threads to use");
				LOGGER.info("\t" +"-b <number>" + "\t" + "Set the buffer size");
			}
			else {
				LOGGER.info("\t" +"Use the 'help -v' option to see advanced options");
			}
			LOGGER.info("");
			LOGGER.info("Report bugs or findings at github.com/dexesttp/hkxpack");
		}
		return 0;
	}
}
