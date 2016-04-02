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
import com.dexesttp.hkxpack.utils.FileUtils;
import com.google.common.io.Files;

public class HKXWriterTest {
	public static final String testBaseOutputName = "test-base.hkx";
	public static final String testFileToObtain = "/test-base.hkx";
	private static HKXEnumResolver enumResolver;
	private static HKXFile file;
	
	@BeforeClass
	public static void setup() throws Exception {
		enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
		file = new HKXFile("hk-2014.1.0-r1", 11);
		file.getContentCollection().add(new HKXObject("#test", descriptorFactory.get("hkBaseObject")));
	}
	
	@Test
	public void testWriteDefaultFileToPhysicalFile() throws Exception {
		File outputFile = File.createTempFile(testBaseOutputName, "");
		HKXWriter writer = new HKXWriter(outputFile, enumResolver);
		writer.write(file);
		assertArrayEquals(
				Files.toByteArray(outputFile),
				FileUtils.resourceToByteArray(testFileToObtain));
	}

	@Test
	public void testWriteDefaultFileToByteBuffer() throws Exception {
		ByteBuffer outputBuffer = ByteBuffer.allocate(10000);
		HKXWriter writer = new HKXWriter(outputBuffer, enumResolver);
		writer.write(file);
		byte[] outArray = new byte[outputBuffer.limit()];
		outputBuffer.get(outArray);
		assertArrayEquals(
				outArray,
				FileUtils.resourceToByteArray(testFileToObtain));
	}
}
