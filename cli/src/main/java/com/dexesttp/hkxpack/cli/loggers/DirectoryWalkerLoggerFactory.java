package com.dexesttp.hkxpack.cli.loggers;

import com.dexesttp.hkxpack.cli.utils.CLIProperties;
import com.dexesttp.hkxpack.resources.LoggerUtil;

/**
 * Creates a {@link DirectoryWalkerLogger} based on the relevant {@link CLIProperties}.
 */
public class DirectoryWalkerLoggerFactory {

	/**
	 * Retrieves a suitable {@link DirectoryWalkerLogger}.
	 * @param total the total number of files to walk through.
	 * @return a suitable {@link DirectoryWalkerLogger}.
	 */
	public DirectoryWalkerLogger newLogger(int total) {
		if(!CLIProperties.quiet)
			System.out.println("Detected " + total + " files to handle.");
		else {
			if(!CLIProperties.verbose)
				return (done) -> {
						System.out.println(
								"Handled " + done + " files ("
								+ ((float)done / (float) total) + "%)");
						handleErrors();
					};
			else
				return (done) -> {
					handleErrors();
				};
		}
		return (done) -> {};
	}

	private void handleErrors() {
		while(!LoggerUtil.getList().isEmpty()) {
			Throwable e = LoggerUtil.getList().remove(0);
			if(CLIProperties.debug)
				e.printStackTrace();
			else
				System.err.println(e.getMessage());
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
