package com.dexesttp.hkxpack.hkxwriter;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.nio.ByteBuffer;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadException;
import com.dexesttp.hkxpack.utils.FileUtils;
import com.google.common.io.Files;

/**
 * Tests for the {@link HKXWriter} class
 */
public class HKXWriterTest {
	public static final String TEST_BASE_OUTPUT_NAME = "test-base.hkx";
	public static final String TEST_BASE_REXOURCE_TARGET = "/test-base.hkx";
	private static HKXEnumResolver enumResolver;
	private static HKXFile file;
	
	@BeforeClass
	/**
	 * Set up the {@link HKXWriter} test
	 */
	public static void setupClass() throws ClassListReadException, ClassFileReadException {
		enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
		file = new HKXFile("hk-2014.1.0-r1", 11);
		file.getContentCollection().add(new HKXObject("#test", descriptorFactory.get("hkBaseObject")));
	}
	
	@Test
	/**
	 * Writes a default file to a test file, and compare its contents to the target.
	 */
	public void testWriteDefaultFileToPhysicalFile() throws Exception {
		File outputFile = File.createTempFile(TEST_BASE_OUTPUT_NAME, "");
		HKXWriter writer = new HKXWriter(outputFile, enumResolver);
		writer.write(file);
		assertArrayEquals(
				Files.toByteArray(outputFile),
				FileUtils.resourceToByteArray(TEST_BASE_REXOURCE_TARGET));
	}

	@Test
	/**
	 * Writes a default file to a test ByteBuffer, and compare its contents to the target.
	 */
	public void testWriteDefaultFileToByteBuffer() throws Exception {
		ByteBuffer outputBuffer = ByteBuffer.allocate(10000);
		HKXWriter writer = new HKXWriter(outputBuffer, enumResolver);
		writer.write(file);
		byte[] outArray = new byte[outputBuffer.limit()];
		outputBuffer.get(outArray);
		assertArrayEquals(
				outArray,
				FileUtils.resourceToByteArray(TEST_BASE_REXOURCE_TARGET));
	}
}
