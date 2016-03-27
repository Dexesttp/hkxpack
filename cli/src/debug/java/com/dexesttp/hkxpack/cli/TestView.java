package com.dexesttp.hkxpack.cli;

import com.dexesttp.hkxpack.cli.components.HKXTest;
import com.dexesttp.hkxpack.cli.components.Read;
import com.dexesttp.hkxpack.cli.components.Write;
import com.dexesttp.hkxpack.cli.components.XMLTest;
import com.dexesttp.hkxpack.resources.LoggerUtil;

/**
 * Testing interface, used to perform live tests with Eclipse.
 * <p>
 * @see HKXTest#exec(String, String)
 * @see XMLTest#exec(String, String)
 * @see Read#exec(String, String)
 * @see Write#exec(String, String)
 */
class TestView {
	private static final String rootName = "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\";
	private static final String testName = "skeleton";

	public static void main(String[] args) {
		Read.exec(rootName, testName);
		Write.exec(rootName, testName);
		Read.exec(rootName, testName + "-new");
		for(Throwable e: LoggerUtil.getList())
			e.printStackTrace();
	}
}
