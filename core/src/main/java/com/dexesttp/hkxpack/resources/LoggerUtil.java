package com.dexesttp.hkxpack.resources;

import java.util.ArrayList;
import java.util.List;

public class LoggerUtil {
	private static List<String> errLogs = new ArrayList<>();
	public static void error(String operation, String datatype, String extras, String message) {
		String opString = operation.isEmpty() ? "\t" : ("[" + operation.substring(0, operation.length() > 5 ? 5 : operation.length()) + "]");
		String dataString = datatype.isEmpty() ? "\t" : ("[" + datatype.substring(0, datatype.length() > 5 ? 5 : datatype.length()) + "]");
		String extraString = extras.isEmpty() ? "\t" : ("[" + extras.substring(0, extras.length() > 5 ? 5 : extras.length()) + "]");
		errLogs.add(opString + "\t" +
				dataString + "\t" +
				extraString + "\t" +
				message);
	}
	
	private static List<String> infoLogs = new ArrayList<>();
	public static void info(String operation, String datatype, String extras, String message) {
		String opString = operation.isEmpty() ? "\t" : ("[" + operation.substring(0, operation.length() > 5 ? 5 : operation.length()) + "]");
		String dataString = datatype.isEmpty() ? "\t" : ("[" + datatype.substring(0, datatype.length() > 5 ? 5 : datatype.length()) + "]");
		String extraString = extras.isEmpty() ? "\t" : ("[" + extras.substring(0, extras.length() > 5 ? 5 : extras.length()) + "]");
		infoLogs.add(opString + "\t" +
				dataString + "\t" +
				extraString + "\t" +
				message);
	}
	
	public static void output() {
		for(String log : errLogs) {
			System.err.println(log);
		}
		if(DisplayProperties.displayDebugInfo)
			for(String log : infoLogs) {
				System.out.println(log);
			}
	}
}
