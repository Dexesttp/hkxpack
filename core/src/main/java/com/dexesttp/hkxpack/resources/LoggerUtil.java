package com.dexesttp.hkxpack.resources;

import java.util.ArrayList;
import java.util.List;

public class LoggerUtil {
	// Log types
	
	private static List<String> errLogs = new ArrayList<>();
	public static void error(String operation, String datatype, String extras, String message) {
		String out = formatString(operation, datatype, extras, message);
		errLogs.add(out);
	}

	private static List<String> infoLogs = new ArrayList<>();
	public static void info(String operation, String datatype, String extras, String message) {
		String out = formatString(operation, datatype, extras, message);
		if(DisplayProperties.displayDebugInfo)
			infoLogs.add(out);
	}
	public static void file(String operation, String datatype, String extras, String message) {
		String out = formatString(operation, datatype, extras, message);
		if(DisplayProperties.displayFileDebugInfo)
			infoLogs.add(out);
	}
	
	private static List<String> classLogs = new ArrayList<>();
	public static void classLog(String message) {
		String out = formatString("CLA", "QUERY", "", message);
		if(DisplayProperties.displayClassImportsInfo)
			classLogs.add(out);
	}
	
	private static List<String> typesLogs = new ArrayList<>();
	public static void type(String message) {
		String out = formatString("TYPE", "", "", message);
		if(DisplayProperties.displayReadTypesInfo)
			typesLogs.add(out);
	}
	
	// String formatter
	
	private static String formatString(String operation, String datatype, String extras, String message) {
		String opString = operation.isEmpty() ? "" : ("[" + operation.substring(0, operation.length() > 5 ? 5 : operation.length()) + "]");
		String dataString = datatype.isEmpty() ? "" : ("[" + datatype.substring(0, datatype.length() > 5 ? 5 : datatype.length()) + "]");
		String extraString = extras.isEmpty() ? "" : ("[" + extras.substring(0, extras.length() > 5 ? 5 : extras.length()) + "]");
		return opString + "\t" + dataString + "\t" + extraString + "\t" + message;
	}
	
	public static void output() {
		for(String log : classLogs) {
			System.out.println(log);
		}
		for(String log : infoLogs) {
			System.out.println(log);
		}
		for(String log : typesLogs) {
			System.out.println(log);
		}
		for(String log : errLogs) {
			System.err.println(log);
		}
	}
}
