package com.dexesttp.hkxpack.tagreader;

import org.junit.ClassRule;

import com.dexesttp.hkxpack.files.ReaderExternalResource;
import com.dexesttp.hkxpack.files.TestBase;

public class TagXMLTestBase extends TestBase {
	@ClassRule
	public static ReaderExternalResource resource = new TagXMLReaderExternalResource(baseFileResourceName);

	public TagXMLTestBase() {
		super(resource.file);
	}
}
