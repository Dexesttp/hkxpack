package com.dexesttp.hkxpack.cli.commands;

import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.dexesttp.hkxpack.cli.loggers.DirectoryWalkerLoggerFactory;
import com.dexesttp.hkxpack.cli.loggers.DirectoryWalkerLoggerFactory.DirectoryWalkerLogger;
import com.dexesttp.hkxpack.cli.utils.ArgsParser;
import com.dexesttp.hkxpack.cli.utils.CLIProperties;
import com.dexesttp.hkxpack.cli.utils.DirWalker;
import com.dexesttp.hkxpack.cli.utils.WrongSizeException;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadError;
import com.dexesttp.hkxpack.hkxwriter.HKXWriter;

/**
 * Abstract command to handle routing between a single file and multiple files from a directory. <br />
 * <p>
 * The new entry point is {@link #execution_core(String, String, HKXEnumResolver, HKXDescriptorFactory)}. This should contain the code to handle a single file. <br />
 * The subclass should also implement {@link #extractFileName(String)}, a routine to convert the input file name to a suitable output one if needed. <br />
 * Finally, the subclass should implement {@link #getFileExtensions()}, to return a list of accepted extensions for imput file detected when walking through directories.
 */
public abstract class Command_IO implements Command {
	private int nbConcurrentThreads = 32;
	protected int bufferSize = HKXWriter.DEFAULT_BUFFER_CAPACITY;

	@Override
	public int execute(String... args) {
		// Options handling
		ArgsParser parser = new ArgsParser();
		parser.addOption("-o", 1);
		parser.addOption("-t", 1);
		parser.addOption("-b", 1);
		parser.addOption("-d", 0);
		parser.addOption("-q", 0);
		parser.addOption("-v", 0);
		ArgsParser.Options result;
		try {
			result = parser.parse(args);
		} catch (WrongSizeException e1) {
			e1.printStackTrace();
			return 1;
		}
		String fileName = result.get("", 1);
		String outName = result.get("-o", 0);
		if(result.exists("-t"))
			nbConcurrentThreads = Integer.parseInt(result.get("-t", 0));
		if(result.exists("-b"))
			bufferSize = Integer.parseInt(result.get("-b", 0));
		
		CLIProperties.debug = result.exists("-d");
		CLIProperties.quiet = result.exists("-q");
		CLIProperties.verbose = result.exists("-v");
		
		// Routing
		File fileIn = new File(fileName);
		if(fileIn.isDirectory())
			return execute_multi(fileIn, outName);
		else
			return execute_single(fileName, outName);
	}

	/**
	 * Executes the code for a single imput file to a single output file.
	 * @param fileName the input file name.
	 * @param outName the output file name.
	 * @return the execution result value.
	 */
	private int execute_single(String fileName, String outName) {
		if(outName == "")
			outName = extractFileName(fileName);
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory;
		try {
			descriptorFactory = new HKXDescriptorFactory(enumResolver);
			execution_core(fileName, outName, enumResolver, descriptorFactory);
		} catch(Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	/**
	 * Executes the code for a directory
	 * @param inDir the input directory {@link File}.
	 * @param outDir the output directory name.
	 * @return the execution result value.
	 * @see DirWalker
	 */
	private int execute_multi(File inDir, String outDir) {
		// Create output directory.
		if(outDir.isEmpty())
			outDir = "out";
		
		// Walk through the directory
		DirWalker walker = new DirWalker(getFileExtensions());
		List<DirWalker.Entry> toConvert = walker.walk(inDir);
		
		// Create logger
		DirectoryWalkerLogger logger = new DirectoryWalkerLoggerFactory().newLogger(toConvert.size());
		
		// Create the thread pool.
		ThreadPoolExecutor pool = new ThreadPoolExecutor(
				nbConcurrentThreads, nbConcurrentThreads,
				0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		
		// Initailize the factorized tools.
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory;
		try {
			descriptorFactory = new HKXDescriptorFactory(enumResolver);
		} catch (ClassListReadError e) {
			e.printStackTrace();
			return 1;
		}
		
		// Populate the thread pool using the directory entries.
		for(DirWalker.Entry fileInDirectory : toConvert) {
			new File(fileInDirectory.getPath("out")).mkdirs();
			final String inputFileName = fileInDirectory.getFullName();
			final String outputFileName = fileInDirectory.getPath("out") + "/" + extractFileName(fileInDirectory.getName());
			pool.execute(() -> {
				execution_catcher(inputFileName, outputFileName, enumResolver, descriptorFactory);
			});
		}
		
		// Handle pool termination, as well as logging of pool execution progress (every 30 seconds)
		pool.shutdown();
		try {
			long numberOfHandledTasks = 0;
			while(!pool.awaitTermination(30, TimeUnit.SECONDS)) {
				if(pool.getCompletedTaskCount() > numberOfHandledTasks)
					numberOfHandledTasks = pool.getCompletedTaskCount();
				logger.log(numberOfHandledTasks);
			}
		} catch (InterruptedException e) {
			// Force shutdown of all tasks.
			pool.shutdownNow();
		}
		return 0;
	}

	/**
	 * Wrapper for the subclass' {@link #execution_core(String, String, HKXEnumResolver, HKXDescriptorFactory)} method.<br />
	 * Cathes all errors and displays them to the out and err streams.
	 * @param inputFileName passed to the execution core
	 * @param outputFileName passed to the execution core
	 * @param enumResolver passed to the execution core
	 * @param descriptorFactory pased to the execution core
	 */
	protected void execution_catcher(String inputFileName, String outputFileName,
			HKXEnumResolver enumResolver, HKXDescriptorFactory descriptorFactory) {
		try {
			execution_core(inputFileName, outputFileName, enumResolver, descriptorFactory);
		} catch (Exception e) {
			System.out.println("Error reading file : " + inputFileName);
			if(CLIProperties.debug)
				e.printStackTrace();
			else if(!CLIProperties.quiet)
				System.err.println(e.getMessage());
		} finally {
			if(CLIProperties.verbose) {
				System.out.println(inputFileName);
				System.out.println("\t=> " + outputFileName);
			}
		}
	}

	/**
	 * Handles a single file
	 * @param inputFileName the input file name
	 * @param outputFileName the output file name
	 * @param enumResolver the {@link HKXEnumResolver} to use.
	 * @param descriptorFactory the {@link HKXDescriptorFactory} to use.
	 * @throws Exception if there was an issue while handling the file
	 */
	protected abstract void execution_core(String inputFileName, String outputFileName,
			HKXEnumResolver enumResolver, HKXDescriptorFactory descriptorFactory)
					throws Exception;

	/**
	 * Extracts a suitable output name from an input file name.
	 * @param ogName the original input file name
	 * @return a suitable output name.
	 */
	protected abstract String extractFileName(String ogName);

	/**
	 * Retrieves a list of file extensions to select while crawling through a directory.
	 * @return the file extensions as a {@link String} list.
	 */
	protected abstract String[] getFileExtensions();
}
