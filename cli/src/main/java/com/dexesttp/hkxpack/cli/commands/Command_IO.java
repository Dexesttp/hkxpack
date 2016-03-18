package com.dexesttp.hkxpack.cli.commands;

import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.dexesttp.hkxpack.cli.utils.ArgsParser;
import com.dexesttp.hkxpack.cli.utils.CLIProperties;
import com.dexesttp.hkxpack.cli.utils.DirWalker;
import com.dexesttp.hkxpack.cli.utils.WrongSizeException;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.resources.LoggerUtil;

public abstract class Command_IO implements Command {
	private int nbConcurrentThreads = 32;
	
	public int execute(String... args) {
		// Options handling
		ArgsParser parser = new ArgsParser();
		parser.addOption("-o", 1);
		parser.addOption("-t", 1);
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
	
	public int execute_single(String fileName, String outName) {
		if(outName == "")
			outName = extractFileName(fileName);
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory;
		try {
			descriptorFactory = new HKXDescriptorFactory(enumResolver);
			getThreadLambda(fileName, outName, descriptorFactory, enumResolver).run();
		} catch(Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	
	private int execute_multi(File inDir, String outDir) {
		if(outDir.isEmpty())
			outDir = "out";
		DirWalker walker = new DirWalker(getFileExtensions());
		List<DirWalker.Entry> toConvert = walker.walk(inDir);
		
		if(!CLIProperties.quiet)
			System.out.println("Detected " + toConvert.size() + " files to handle.");
		
		ThreadPoolExecutor pool = new ThreadPoolExecutor(
				nbConcurrentThreads, nbConcurrentThreads,
				0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory;
		try {
			descriptorFactory = new HKXDescriptorFactory(enumResolver);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		
		for(DirWalker.Entry fileInDirectory : toConvert) {
			new File(fileInDirectory.getPath("out")).mkdirs();
			final String inputFileName = fileInDirectory.getFullName();
			final String outputFileName = fileInDirectory.getPath("out") + "/" + extractFileName(fileInDirectory.getName());
			pool.execute(getThreadLambda(inputFileName, outputFileName, descriptorFactory, enumResolver));
		}
		
		try {
			long numberOfHandledTasks = 0;
			while(!pool.awaitTermination(30, TimeUnit.SECONDS)) {
				if(pool.getCompletedTaskCount() > numberOfHandledTasks)
					numberOfHandledTasks = pool.getCompletedTaskCount();
				if(!CLIProperties.quiet) {
					if(!CLIProperties.verbose)
						System.out.println("Handled " + numberOfHandledTasks + " files.");
					while(!LoggerUtil.getList().isEmpty()) {
						Throwable e = LoggerUtil.getList().remove(0);
						if(CLIProperties.debug)
							e.printStackTrace();
						else
							System.err.println(e.getMessage());
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	protected abstract Runnable getThreadLambda(String inputFileName, String outputFileName, HKXDescriptorFactory descriptorFactory, HKXEnumResolver enumResolver);
	protected abstract String extractFileName(String ogName);
	protected abstract String[] getFileExtensions();
}
