package com.dexesttp.hkxpack.cli.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Contains a list of static final properties.
 */
public final class StaticProperties {
	private static final String VERSION_NUMBER;

	static {
		Properties prop = new Properties();
		String vNum = "";
		try {
			InputStream in = StaticProperties.class
					.getResourceAsStream("/META-INF/maven/com.dexesttp.hkxpack/cli/pom.properties");
			prop.load(in);
			in.close();
			vNum = prop.getProperty("version");
		} catch (IOException e) {
			vNum = "error loading version number";
		}
		VERSION_NUMBER = vNum;
	}

	private StaticProperties() {
		// NO OP
	}

	/**
	 * <pre>
	 * Versioning convention:
	 * 1st digit : main version identifier
	 * * Supposed to be used in case of a huge fonctionnality change.
	 * * A change shows a compatibility loss with previous versions.
	 * 
	 * 2nd digit : version iteration
	 * * Supposed to be used when a new functionnality set is added.
	 * 
	 * 3rd digit : version state
	 * * Even = stable, odd = unstable
	 * * Changed each time a functionnality is added for release.
	 * 
	 * Hyphen : state identifier
	 * * Either alpha|beta|theta|gamma or any state identifier (unstable, snapshot, etc.. works)
	 * * Linguistic representation of the 1st, 2nd and 3rd digit.
	 * * Doesn't exist with stable releases (no -HOTFIX or similar, this goes into the merge data or changelog !)
	 * </pre>
	 */
	public static String getVersionNumber() {
		return VERSION_NUMBER;
	}
}
