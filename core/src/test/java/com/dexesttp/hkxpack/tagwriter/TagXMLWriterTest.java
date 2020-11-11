package com.dexesttp.hkxpack.tagwriter;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;

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
 * Tests for a TagXMLWriter
 */
public class TagXMLWriterTest {
	public static final String TEST_BASE_OUTPUT_FILE = "test-base.xml";
	public static final String TEST_BASE_TARGET_RESOURCE = "/test-base.xml";
	private static HKXFile file;

	@BeforeClass
	/**
	 * Set up the test environnement
	 */
	public static void setupClass() throws ClassListReadException, ClassFileReadException {
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
		file = new HKXFile("hk-2014.1.0-r1", 11);
		file.getContentCollection().add(new HKXObject("#test", descriptorFactory.get("hkBaseObject")));
	}

	@Test
	/**
	 * Tests if the TagXMLWriter output file is equals to the target file.
	 */
	public void testWriteDefaultFileToPhysicalFile() throws Exception {
		File outputFile = File.createTempFile(TEST_BASE_OUTPUT_FILE, "");
		TagXMLWriter writer = new TagXMLWriter(outputFile);
		writer.write(file);
		assertArrayEquals(Files.toByteArray(outputFile), FileUtils.resourceToByteArray(TEST_BASE_TARGET_RESOURCE));
	}
}
