package com.dexesttp.hkxpack.hkxwriter;

import java.io.File;
import java.nio.ByteBuffer;

import static org.junit.Assert.assertArrayEquals;
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
	private static HKXDescriptorFactory descriptorFactory;
	private static HKXWriter writer;
	private static HKXFile file;
	
	@BeforeClass
	public static void setup() throws Exception {
		enumResolver = new HKXEnumResolver();
		descriptorFactory = new HKXDescriptorFactory(enumResolver);
		file = new HKXFile("hk-2014.1.0-r1", 11);
		file.content().add(new HKXObject("#test", descriptorFactory.get("hkBaseObject")));
	}
	
	@Test
	public void testWriteDefaultFileToPhysicalFile() throws Exception {
		File outputFile = File.createTempFile(testBaseOutputName, "");
		writer = new HKXWriter(outputFile, enumResolver);
		writer.write(file);
		assertArrayEquals(
				Files.toByteArray(outputFile),
				Files.toByteArray(FileUtils.resourceToTemporaryFile(testFileToObtain)));
	}

	@Test
	public void testWriteDefaultFileToByteBuffer() throws Exception {
		ByteBuffer outputBuffer = ByteBuffer.allocate(10000);
		writer = new HKXWriter(outputBuffer, enumResolver);
		writer.write(file);
		byte[] outArray = new byte[outputBuffer.limit()];
		outputBuffer.get(outArray);
		assertArrayEquals(
				outArray,
				FileUtils.resourceToHKXByteBuffer(testFileToObtain).array());
	}
}
