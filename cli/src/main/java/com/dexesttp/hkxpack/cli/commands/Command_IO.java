package com.dexesttp.hkxpack.cli.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxpack.cli.utils.ArgsParser;
import com.dexesttp.hkxpack.cli.utils.DirWalker;
import com.dexesttp.hkxpack.cli.utils.WrongSizeException;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.resources.DisplayProperties;

public abstract class Command_IO implements Command {
	public int execute(String... args) {
		ArgsParser parser = new ArgsParser();
		parser.addOption("-o", 1);
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
		List<Thread> threads = new ArrayList<>();
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory;
		try {
			descriptorFactory = new HKXDescriptorFactory(enumResolver);
			for(DirWalker.Entry fileInDirectory : toConvert) {
				new File(fileInDirectory.getPath("out")).mkdirs();
				final String inputFileName = fileInDirectory.getFullName();
				final String outputFileName = fileInDirectory.getPath("out") + "/" + extractFileName(fileInDirectory.getName());
				Thread toRun = new Thread(getThreadLambda(inputFileName, outputFileName, descriptorFactory, enumResolver));
				threads.add(toRun);
				System.out.println(inputFileName);
				System.out.println("\t=> " + outputFileName);
			}

			for(Thread thread : threads)
				thread.start();

			for(Thread thread : threads)
				thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	
	protected abstract Runnable getThreadLambda(String inputFileName, String outputFileName, HKXDescriptorFactory descriptorFactory, HKXEnumResolver enumResolver);
	protected abstract String extractFileName(String ogName);
	protected abstract String[] getFileExtensions();
}
