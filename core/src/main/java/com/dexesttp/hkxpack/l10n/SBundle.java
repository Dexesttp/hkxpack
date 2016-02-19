package com.dexesttp.hkxpack.l10n;

import java.util.Locale;
import java.util.ResourceBundle;

public class SBundle {
	private static String baseName = "l10n/locale";
	private static ResourceBundle instance;

	private SBundle() {
		// NO OP
	}
	
	public static String getString(String s) {
		return getInstance().getString(s);
	}
	
	public static ResourceBundle getInstance() {
		if(instance == null)
			initInstance();
		return instance;
	}

	private static void initInstance() {
		instance = ResourceBundle.getBundle(baseName, Locale.ENGLISH);
	}
}
