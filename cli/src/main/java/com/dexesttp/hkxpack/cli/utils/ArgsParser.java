package com.dexesttp.hkxpack.cli.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArgsParser {
	private class OptionList extends HashMap<String, Integer> {
		private static final long serialVersionUID = 7529991519923957630L;
		
		void add(String option, int numberOfArgs) {
			this.put(option, numberOfArgs);
		}
		
		void add(String option) {
			this.put(option, -1);
		}
	}
	
	public class Options extends HashMap<String, List<String>> {
		private static final long serialVersionUID = -9078055964913056347L;
		
		public String get(String optionName, int position) {
			try {
				return this.get(optionName).get(position);
			} catch(Exception e) {
				return "";
			}
		}
		
		public boolean exists(String optionName) {
			return this.containsKey(optionName);
		}
	}
	
	private OptionList optionList = new OptionList();
	
	public void addOption(String optionName) {
		optionList.add(optionName);
	}
	
	public void addOption(String optionName, int optionSize) {
		optionList.add(optionName, optionSize);
	}
	
	public Options parse(String... args) throws WrongSizeException {
		Options res = new Options();
		String optionName = "";
		res.put(optionName, new ArrayList<>());
		int countdown = -1;
		for(String arg : args) {
			if(countdown == 0)
				optionName = "";
			countdown--;
			if(optionList.containsKey(arg)) {
				if(countdown >= 0)
					throw new WrongSizeException();
				optionName = arg;
				countdown = optionList.get(arg);
				if(!res.containsKey(optionName))
					res.put(optionName, new ArrayList<>());
			} else {
				res.get(optionName).add(arg);
			}
		}
		return res;
	}
}
