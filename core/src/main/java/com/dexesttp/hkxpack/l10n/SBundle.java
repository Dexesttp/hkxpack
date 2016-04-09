package com.dexesttp.hkxpack.l10n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Contains the ResourceBundle for localizaton stuff.
 */
public final class SBundle {
	private static String baseName = "l10n/locale";
	private static ResourceBundle instance;

	private SBundle() {
		// NO OP
	}
	
	/**
	 * Get a localized string.
	 * @param stringName the string name
	 * @return the localized string
	 */
	public static String getString(final String stringName) {
		return getInstance().getString(stringName);
	}
	
	/**
	 * Get the localized ResourceBundle.
	 * @return the localized {@link ResourceBundle}.
	 */
	public static ResourceBundle getInstance() {
		if(instance == null) {
			initInstance();
		}
		return instance;
	}

	private static void initInstance() {
		instance = ResourceBundle.getBundle(baseName, Locale.ENGLISH);
	}
}
