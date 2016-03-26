package com.dexesttp.hkxpack.hkxreader;

import java.io.File;
import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;

import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.utils.FileUtils;

public class HKXReaderTest {
	public static final String testBaseFileResource = "/test-base.hkx";
	private HKXDescriptorFactory descriptorFactory;
	private HKXEnumResolver enumResolver;

	@Before
	public void setup() throws Exception {
		this.enumResolver = new HKXEnumResolver();
		this.descriptorFactory = new HKXDescriptorFactory(enumResolver);
	}

	@Test
	public void baseFileReadingUsingByteBufferDoesntThrowAnException() throws Exception{
		ByteBuffer toRead = FileUtils.resourceToHKXByteBuffer(testBaseFileResource);
		HKXReader reader = new HKXReader(toRead, descriptorFactory, enumResolver);
		reader.read();
	}

	@Test
	public void baseFileReadingUsingFileDoesntThrowAnException() throws Exception{
		File toRead = FileUtils.resourceToTemporaryFile(testBaseFileResource);
		HKXReader reader = new HKXReader(toRead, descriptorFactory, enumResolver);
		reader.read();
	}
}
