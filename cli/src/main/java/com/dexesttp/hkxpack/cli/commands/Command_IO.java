package com.dexesttp.hkxpack.cli.commands;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.dexesttp.hkxpack.cli.utils.ArgsParser;
import com.dexesttp.hkxpack.cli.utils.DirWalker;
import com.dexesttp.hkxpack.cli.utils.WrongSizeException;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.resources.DisplayProperties;

public abstract class Command_IO implements Command {
	private int nbConcurrentThreads = 32;
	
	public int execute(String... args) {
		ArgsParser parser = new ArgsParser();
		parser.addOption("-o", 1);
		parser.addOption("-t", 1);
		parser.addOption("-d", 0);
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
		
		DisplayProperties.displayDebugInfo = result.exists("-d");
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
		ExecutorService pool = Executors.newFixedThreadPool(nbConcurrentThreads);
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
			pool.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	protected abstract Runnable getThreadLambda(String inputFileName, String outputFileName, HKXDescriptorFactory descriptorFactory, HKXEnumResolver enumResolver);
	protected abstract String extractFileName(String ogName);
	protected abstract String[] getFileExtensions();
}
