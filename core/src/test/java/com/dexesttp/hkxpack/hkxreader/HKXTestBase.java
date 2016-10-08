package com.dexesttp.hkxpack.hkxreader;

import org.junit.ClassRule;

import com.dexesttp.hkxpack.files.ReaderExternalResource;
import com.dexesttp.hkxpack.files.TestBase;

/**
 * {@link TestBase} extension for the HKX file.
 */
public class HKXTestBase extends TestBase {
	@ClassRule
	public static ReaderExternalResource resource = new HKXReaderExternalResource(BASE_FILE_RESOURCE_NAME);

	/**
	 * Creates the {@link HKXTestBase}.
	 */
	public HKXTestBase() {
		super(resource.file);
	}
}
