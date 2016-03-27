package com.dexesttp.hkxpack.cli.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Parse arguments into {@link Options}.<br />
 * {@link #addOption(String, int)} adds an option catching a specific number of arguments.<br />
 * {@link #addOption(String)} adds an option catcing all arguments following it.<br />
 * {@link #parse(String...)} parse arguments into {@link Options}.
 */
public class ArgsParser {

	/**
	 * Contains a list of {@link Options} associated with their argument catcher size.
	 */
	private class OptionList extends HashMap<String, Integer> {
		private static final long serialVersionUID = 7529991519923957630L;
		
		void add(String option, int numberOfArgs) {
			this.put(option, numberOfArgs);
		}
		
		void add(String option) {
			this.put(option, -1);
		}
	}
	
	/**
	 * Contains the result of {@link ArgsParser#parse(String...)}.<br />
	 * use {@link #get(String, int)} to get the n-th argument of a given option<br />
	 * use {@link #exists(String)} to check if an option was detected.
	 */
	public class Options extends HashMap<String, List<String>> {
		private static final long serialVersionUID = -9078055964913056347L;
		
		/**
		 * Retrieve the option-catched argument described by {@code position}.
		 * <p>
		 * If there's no argument or the option doesn't exist, return {@literal ""}.
		 * @param optionName the option name to retrieve from
		 * @param position the position of the argument to retrieve
		 * @return the relevant argument, or {@literal ""}
		 */
		public String get(String optionName, int position) {
			try {
				return this.get(optionName).get(position);
			} catch(Exception e) {
				return "";
			}
		}
		
		/**
		 * Returns true if the given {@code optionName} exists.
		 * @param optionName the option to test the existence of.
		 */
		public boolean exists(String optionName) {
			return this.containsKey(optionName);
		}
	}
	
	private OptionList optionList = new OptionList();
	
	/**
	 * Adds an option catching an unlimited number of arguments.
	 * @param optionName the name of the argument to add.
	 */
	public void addOption(String optionName) {
		optionList.add(optionName);
	}
	
	/**
	 * Adds an option catching exacty {@code optionSize} arguments.
	 * @param optionName the name of the option to add
	 * @param optionSize the number of arguments to catch
	 */
	public void addOption(String optionName, int optionSize) {
		optionList.add(optionName, optionSize);
	}
	
	/**
	 * Parse arguments into {@link Options}.
	 * @param args the arguments to parse.
	 * @return the {@link Options} object.
	 * @throws WrongSizeException
	 */
	public Options parse(String... args) throws WrongSizeException {
		Options res = new Options();
		String optionName = "";
		res.put(optionName, new ArrayList<>());
		int countdown = -1;
		for(String arg : args) {
			if(countdown == 0)
				optionName = "";
			if(optionList.containsKey(arg)) {
				optionName = arg;
				if(countdown > 0)
					throw new WrongSizeException(optionName);
				else if(!res.containsKey(optionName))
					res.put(optionName, new ArrayList<>());
				else if(res.get(optionName).size() == optionList.get(arg))
					throw new WrongSizeException(optionName);
				countdown = optionList.get(arg) - res.get(optionName).size();
			} else {
				countdown--;
				res.get(optionName).add(arg);
			}
		}
		if(countdown > 0)
			throw new WrongSizeException(optionName);
		return res;
	}
}
