package com.dexesttp.hkxpack.hkxreader;

import org.junit.ClassRule;

import com.dexesttp.hkxpack.files.ReaderExternalResource;
import com.dexesttp.hkxpack.files.TestBase;

public class HKXTestBase extends TestBase {
	@ClassRule
	public static ReaderExternalResource resource = new HKXReaderExternalResource(baseFileResourceName);

	public HKXTestBase() {
		super(resource.file);
	}
}
