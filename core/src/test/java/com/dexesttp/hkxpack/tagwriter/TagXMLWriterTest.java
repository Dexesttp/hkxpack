package com.dexesttp.hkxpack.tagwriter;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.utils.FileUtils;
import com.google.common.io.Files;

public class TagXMLWriterTest {
	public static final String testBaseOutputName = "test-base.xml";
	public static final String testFileToObtain = "/test-base.xml";
	private static HKXFile file;
	
	@BeforeClass
	public static void setup() throws Exception {
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
		file = new HKXFile("hk-2014.1.0-r1", 11);
		file.getContentCollection().add(new HKXObject("#test", descriptorFactory.get("hkBaseObject")));
	}
	
	@Test
	public void testWriteDefaultFileToPhysicalFile() throws Exception {
		File outputFile = File.createTempFile(testBaseOutputName, "");
		TagXMLWriter writer = new TagXMLWriter(outputFile);
		writer.write(file);
		assertArrayEquals(
				Files.toByteArray(outputFile),
				FileUtils.resourceToByteArray(testFileToObtain));
	}
}
