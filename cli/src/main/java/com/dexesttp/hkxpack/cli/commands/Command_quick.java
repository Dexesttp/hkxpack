package com.dexesttp.hkxpack.cli.commands;

import java.io.File;

import com.dexesttp.hkxpack.cli.utils.RandomUtils;

/**
 * Routes an unknown command to either a help, pack or unpack command.
 */
public class Command_quick implements Command {
	
	@Override
	public int execute(String... parameters) {
		Command command = null;
		if(parameters.length > 1) {
			return (new Command_help()).execute(parameters);
		}
		String inName = parameters[0];
		File inFile = new File(inName);
		String name = inFile.getName();
		String command_arg = "";
		String outFile = "";
	    try {
	    	if(!inFile.isFile())
	    		throw new Exception("The given parameter isn't a HKX or XML file.");
	    	String ext = name.substring(name.lastIndexOf(".") + 1);
	    	if(ext.equals("hkx")) {
	    		command = new Command_unpack();
	    		command_arg = "unpack";
				outFile = RandomUtils.makeFromFileName(inName);
	    	} else if(ext.equals("xml")) {
	    		command = new Command_pack();
	    		command_arg = "pack";
				outFile = RandomUtils.unmakeFromFileName(inName);
	    	} else
	    		throw new Exception("Unsupported file type.");
	    } catch (Exception e) {
	    	System.err.println(e.getMessage());
	        return 1;
	    }
	    return command.execute(command_arg, inName, "-o", outFile);
	}

}
