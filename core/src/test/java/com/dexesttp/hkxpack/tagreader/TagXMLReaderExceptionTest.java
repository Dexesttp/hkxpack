package com.dexesttp.hkxpack.tagreader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;
import com.dexesttp.hkxpack.utils.FileUtils;

/**
 * Tests for exception throwing in the TagXMLReader class
 */
public class TagXMLReaderExceptionTest {
	public static final String TEST_BASE_RESOURCE = "/test-base.xml";
	private transient HKXDescriptorFactory descriptorFactory;

	@Before
	/**
	 * Set up the TagXMLReader tests
	 */
	public void setUp() throws Exception {
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		this.descriptorFactory = new HKXDescriptorFactory(enumResolver);
	}

	@Test
	/**
	 * Test reading the file.
	 * 
	 * @throws Exception
	 */
	public void baseFileReadingUsingFileDoesntThrowAnException()
			throws IOException, ParserConfigurationException, SAXException, InvalidTagXMLException {
		File toRead = FileUtils.resourceToTemporaryFile(TEST_BASE_RESOURCE);
		TagXMLReader reader = new TagXMLReader(toRead, descriptorFactory);
		reader.read();
	}
}
