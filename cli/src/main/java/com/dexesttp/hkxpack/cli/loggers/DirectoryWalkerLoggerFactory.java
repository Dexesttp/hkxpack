package com.dexesttp.hkxpack.cli.loggers;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dexesttp.hkxpack.cli.ConsoleView;
import com.dexesttp.hkxpack.cli.utils.CLIProperties;
import com.dexesttp.hkxpack.resources.LoggerUtil;

/**
 * Creates a {@link DirectoryWalkerLogger} based on the relevant {@link CLIProperties}.
 */
public class DirectoryWalkerLoggerFactory {
	private static final Logger LOGGER = Logger.getLogger(ConsoleView.class.getName());

	/**
	 * Retrieves a suitable {@link DirectoryWalkerLogger}.
	 * @param total the total number of files to walk through.
	 * @return a suitable {@link DirectoryWalkerLogger}.
	 */
	public DirectoryWalkerLogger newLogger(final int total) {
		if(!CLIProperties.quiet && LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Detected " + total + " files to handle.");
		}
		else {
			if(!CLIProperties.verbose && LOGGER.isLoggable(Level.INFO)) {
				return (done) -> {
						LOGGER.info(
								"Handled " + done + " files ("
								+ ((float)done / (float) total) + "%)");
						handleErrors();
					};
			}
			else {
				return (done) -> {
					handleErrors();
				};
			}
		}
		return (done) -> {};
	}

	/**
	 * Handles the {@link LoggerUtil} logging.
	 */
	private void handleErrors() {
		while(!LoggerUtil.getList().isEmpty()) {
			Throwable e = LoggerUtil.getList().remove(0);
			LOGGER.throwing(this.getClass().getName(), "handleErrors", e);
		}
	}
	
	/**
	 * Logs a Directory walking progress.
	 */
	public interface DirectoryWalkerLogger {
		/**
		 * Logs the progress of the walk.
		 * @param done the number of files already walked through.
		 */
		void log(long done);
	}
}
