package com.dexesttp.hkxpack.tagreader;

import org.junit.ClassRule;

import com.dexesttp.hkxpack.files.ReaderExternalResource;
import com.dexesttp.hkxpack.files.TestBase;

/**
 * Tests for the TagXML version of the test base
 */
public class TagXMLTestBase extends TestBase {
	@ClassRule
	public static ReaderExternalResource resource = new TagXMLReaderExternalResource(BASE_FILE_RESOURCE_NAME);

	/**
	 * Creates a {@link TagXMLTestBase}
	 */
	public TagXMLTestBase() {
		super(resource.file);
	}
}
