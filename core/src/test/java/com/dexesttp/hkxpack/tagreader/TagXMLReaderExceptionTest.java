package com.dexesttp.hkxpack.tagreader;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.utils.FileUtils;

public class TagXMLReaderExceptionTest {
	public static final String testBaseFileResource = "/test-base.xml";
	private HKXEnumResolver enumResolver;
	private HKXDescriptorFactory descriptorFactory;

	@Before
	public void setup() throws Exception {
		this.enumResolver = new HKXEnumResolver();
		this.descriptorFactory = new HKXDescriptorFactory(enumResolver);
	}

	@Test
	public void baseFileReadingUsingFileDoesntThrowAnException() throws Exception{
		File toRead = FileUtils.resourceToTemporaryFile(testBaseFileResource);
		TagXMLReader reader = new TagXMLReader(toRead, descriptorFactory);
		reader.read();
	}
}
