package com.dexesttp.hkxpack.cli;

import java.util.logging.Logger;

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
final class TestView {
	public static final Logger LOGGER = Logger.getLogger(TestView.class.getName());
	private static final String ROOT_NAME = "D:\\Documents\\SANDBOX\\FO4\\hkx_files\\";
	private static final String TEST_FILE_NAME = "skeleton";

	/**
	 * Testing entry point
	 * @param args
	 */
	public static void main(final String... args) {
		Read.exec(ROOT_NAME, TEST_FILE_NAME);
		Write.exec(ROOT_NAME, TEST_FILE_NAME);
		Read.exec(ROOT_NAME, TEST_FILE_NAME + "-new");
		for(Throwable e: LoggerUtil.getList()) {
			LOGGER.throwing(TestView.class.getName(), "main", e);
		}
	}

	private TestView() {
		// NO OP
	}
}
