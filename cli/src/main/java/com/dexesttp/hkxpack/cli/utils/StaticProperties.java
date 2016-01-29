package com.dexesttp.hkxpack.cli.utils;

public class StaticProperties {
	/**
	 * Versioning convention:
	 * 1st digit : main version identifier
	 * * Supposed to be used in case of a huge fonctionnality change.
	 * * A change shows a compatibility loss with previous versions.
	 * * 
	 * 2nd digit : version iteration
	 * * Supposed to be used when a new functionnality set is added.
	 * 3rd digit : version state
	 * * Even = stable, odd = unstable
	 * * Changed each time a functionnality is added for release.
	 * Hyphen : state identifier
	 * * Either alpha|beta|theta|gamma or any state identifier (unstable, snapshot, etc.. works)
	 * * Litteral representation of the 3rd digit.
	 * * Doesn't exist with stable releases (no -HOTFIX or similar, this goes into the merge data or changelog !)
	 */
	public static String version_number = "0.0.5-alpha";
}
