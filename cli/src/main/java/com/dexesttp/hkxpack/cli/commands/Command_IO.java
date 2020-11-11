package com.dexesttp.hkxpack.cli.commands;

import java.io.File;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.cli.ConsoleView;
import com.dexesttp.hkxpack.cli.loggers.DirectoryWalkerLoggerFactory;
import com.dexesttp.hkxpack.cli.loggers.DirectoryWalkerLoggerFactory.DirectoryWalkerLogger;
import com.dexesttp.hkxpack.cli.utils.ArgsParser;
import com.dexesttp.hkxpack.cli.utils.DirWalker;
import com.dexesttp.hkxpack.cli.utils.DirWalker.Entry;
import com.dexesttp.hkxpack.cli.utils.FileNameCreationException;
import com.dexesttp.hkxpack.cli.utils.WrongSizeException;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkxwriter.HKXWriter;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Abstract command to handle routing between a single file and multiple files
 * from a directory. <br />
 * <p>
 * The new entry point is
 * {@link #executionCore(String, String, HKXEnumResolver, HKXDescriptorFactory)}.
 * This should contain the code to handle a single file. <br />
 * The subclass should also implement {@link #extractFileName(String)}, a
 * routine to convert the input file name to a suitable output one if needed.
 * <br />
 * Finally, the subclass should implement {@link #getFileExtensions()}, to
 * return a list of accepted extensions for imput file detected when walking
 * through directories.
 */
public abstract class Command_IO implements Command {
	private static final Logger LOGGER = Logger.getLogger(ConsoleView.class.getName());
	private transient int nbConcurrentThreads = 32;
	protected transient int bufferSize = HKXWriter.DEFAULT_BUFFER_CAPACITY;

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int execute(final String... args) {
		// Initialize logger handler
		// From http://stackoverflow.com/a/6315736/6335555
		LOGGER.setUseParentHandlers(false);
		Handler consoleHandler = new ConsoleHandler();
		LOGGER.addHandler(consoleHandler);

		// Options handling
		ArgsParser parser = new ArgsParser();
		parser.addOption("-o", 1);
		parser.addOption("-t", 1);
		parser.addOption("-b", 1);
		parser.addOption("-q", 0);
		parser.addOption("-v", 0);
		parser.addOption("-d", 0);
		ArgsParser.Options result;
		try {
			result = parser.parse(args);
		} catch (WrongSizeException e) {
			consoleHandler.setLevel(Level.ALL);
			LOGGER.setLevel(Level.ALL);
			LOGGER.severe("Could not parse args properly.");
			LOGGER.severe(e.getMessage());
			LOGGER.throwing(this.getClass().getName(), "ArgsParser", e);
			return 1;
		}

		// Logging levels
		if (result.exists("-d")) {
			consoleHandler.setLevel(Level.ALL);
			LOGGER.setLevel(Level.ALL);
			LOGGER.finer("Debug mode selected.");
		} else if (result.exists("-v")) {
			consoleHandler.setLevel(Level.FINE);
			LOGGER.setLevel(Level.FINE);
		} else if (result.exists("-q")) {
			consoleHandler.setLevel(Level.SEVERE);
			LOGGER.setLevel(Level.SEVERE);
		} else {
			consoleHandler.setLevel(Level.INFO);
			LOGGER.setLevel(Level.INFO);
		}

		String fileName = result.get("", 1);
		String outName = result.get("-o", 0);
		if (result.exists("-t")) {
			nbConcurrentThreads = Integer.parseInt(result.get("-t", 0));
		}
		if (result.exists("-b")) {
			bufferSize = Integer.parseInt(result.get("-b", 0));
		}

		// Routing
		File fileIn = new File(fileName);
		if (fileIn.isDirectory()) {
			return executeMulti(fileIn, outName);
		} else {
			return executeSingle(fileName, outName);
		}
	}

	/**
	 * Executes the code for a single imput file to a single output file.
	 * 
	 * @param fileName       the input file name.
	 * @param outputFileName the output file name.
	 * @return the execution result value.
	 * @throws FileNameCreationException
	 */
	private int executeSingle(final String fileName, final String outputFileName) {
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory;
		try {
			String actualOutputName = outputFileName.isEmpty() ? extractFileName(fileName) : outputFileName;
			descriptorFactory = new HKXDescriptorFactory(enumResolver);
			executionCore(fileName, actualOutputName, enumResolver, descriptorFactory);
		} catch (BufferUnderflowException e) {
			if (LOGGER.isLoggable(Level.SEVERE)) {
				LOGGER.severe("There was an error handling the file.");
				LOGGER.severe("The file " + fileName
						+ " ended before the processor had the needed data. The file might be corrupted or the parser might not be able to handle this kind of file.");
			}
			LOGGER.log(Level.FINER, e.getMessage(), e);
			return 1;
		} catch (ParserConfigurationException | SAXException | IOException | InvalidTagXMLException
				| UnsupportedVersionError | InvalidPositionException | TransformerException
				| FileNameCreationException e) {
			if (LOGGER.isLoggable(Level.SEVERE)) {
				LOGGER.severe("There was an error handling the file.");
				LOGGER.severe(e.getMessage());
			}
			LOGGER.log(Level.FINER, e.getMessage(), e);
			return 1;
		}
		return 0;
	}

	/**
	 * Executes the code for a directory
	 * 
	 * @param inputDir  the input directory {@link File}.
	 * @param outputDir the output directory name.
	 * @return the execution result value.
	 * @throws FileNameCreationException
	 * @see DirWalker
	 */
	private int executeMulti(final File inputDir, final String outputDir) {

		// Initailize the factorized tools.
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory;
		try {
			descriptorFactory = new HKXDescriptorFactory(enumResolver);
		} catch (ClassListReadException e) {
			LOGGER.log(Level.FINER, e.getMessage(), e);
			return 1;
		}

		// Create the thread pool.
		ThreadPoolExecutor pool = new ThreadPoolExecutor(nbConcurrentThreads, nbConcurrentThreads, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

		// Walk through the directory
		DirWalker walker = new DirWalker(getFileExtensions());

		// Create logger
		List<DirWalker.Entry> toConvert = walker.walk(inputDir);
		DirectoryWalkerLogger directoryLogger = new DirectoryWalkerLoggerFactory().newLogger(toConvert.size());

		// Create output directory path
		String actualOutputDirectory = outputDir.isEmpty() ? "out" : outputDir;

		// Populate the thread pool using the directory entries.
		for (DirWalker.Entry fileInDirectory : toConvert) {
			makeDirs(fileInDirectory, actualOutputDirectory);
			final String inputFileName = fileInDirectory.getFullName();
			String outputFileName;
			try {
				outputFileName = fileInDirectory.getPath(actualOutputDirectory) + "/"
						+ extractFileName(fileInDirectory.getName());
				pool.execute(() -> {
					executionCatcher(inputFileName, outputFileName, enumResolver, descriptorFactory);
				});
			} catch (FileNameCreationException e) {
				LOGGER.log(Level.FINER, e.getMessage(), e);
			}
		}

		// Handle pool termination, as well as logging of pool execution progress (every
		// 30 seconds)
		pool.shutdown();
		try {
			long numberOfHandledTasks = 0;
			while (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
				if (pool.getCompletedTaskCount() > numberOfHandledTasks) {
					numberOfHandledTasks = pool.getCompletedTaskCount();
				}
				directoryLogger.log(numberOfHandledTasks);
			}
		} catch (InterruptedException e) {
			// Force shutdown of all tasks.
			pool.shutdownNow();
		}
		return 0;
	}

	private void makeDirs(final Entry fileInDirectory, final String actualOutputDirectory) {
		new File(fileInDirectory.getPath(actualOutputDirectory)).mkdirs();
	}

	/**
	 * Wrapper for the subclass'
	 * {@link #executionCore(String, String, HKXEnumResolver, HKXDescriptorFactory)}
	 * method.<br />
	 * Cathes all errors and displays them to the out and err streams.
	 * 
	 * @param inputFileName     passed to the execution core
	 * @param outputFileName    passed to the execution core
	 * @param enumResolver      passed to the execution core
	 * @param descriptorFactory pased to the execution core
	 */
	protected void executionCatcher(final String inputFileName, final String outputFileName,
			final HKXEnumResolver enumResolver, final HKXDescriptorFactory descriptorFactory) {
		try {
			executionCore(inputFileName, outputFileName, enumResolver, descriptorFactory);
		} catch (BufferUnderflowException e) {
			if (LOGGER.isLoggable(Level.SEVERE)) {
				LOGGER.severe("Error reading file : " + inputFileName);
				LOGGER.severe("The file " + inputFileName
						+ " ended before the processor had the needed data. The file might be corrupted or the parser might not be able to handle this kind of file.");
			}
			LOGGER.log(Level.FINER, e.getMessage(), e);
		} catch (ParserConfigurationException | SAXException | IOException | InvalidTagXMLException
				| UnsupportedVersionError | InvalidPositionException | TransformerException e) {
			if (LOGGER.isLoggable(Level.SEVERE)) {
				LOGGER.severe("Error reading file : " + inputFileName);
				LOGGER.severe(e.getMessage());
			}
			LOGGER.log(Level.FINER, e.getMessage(), e);
		} finally {
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.fine(inputFileName + "\t=> " + outputFileName);
			}
		}
	}

	/**
	 * Handles a single file
	 * 
	 * @param inputFileName     the input file name
	 * @param outputFileName    the output file name
	 * @param enumResolver      the {@link HKXEnumResolver} to use.
	 * @param descriptorFactory the {@link HKXDescriptorFactory} to use.
	 * @throws Exception if there was an issue while handling the file
	 */
	protected abstract void executionCore(final String inputFileName, final String outputFileName,
			final HKXEnumResolver enumResolver, final HKXDescriptorFactory descriptorFactory)
			throws ParserConfigurationException, SAXException, IOException, InvalidTagXMLException,
			UnsupportedVersionError, InvalidPositionException, TransformerException;

	/**
	 * Extracts a suitable output name from an input file name.
	 * 
	 * @param ogName the original input file name
	 * @return a suitable output name.
	 * @throws FileNameCreationException if the file name couldn't be converted
	 */
	protected abstract String extractFileName(String ogName) throws FileNameCreationException;

	/**
	 * Retrieves a list of file extensions to select while crawling through a
	 * directory.
	 * 
	 * @return the file extensions as a {@link String} list.
	 */
	protected abstract String[] getFileExtensions();
}
