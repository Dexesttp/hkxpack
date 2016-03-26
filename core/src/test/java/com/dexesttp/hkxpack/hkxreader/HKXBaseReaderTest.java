package com.dexesttp.hkxpack.hkxreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.ByteBuffer;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.utils.FileUtils;

public class HKXBaseReaderTest {
	private static final String baseFileResourceName = "/test-base.hkx";

	private static HKXEnumResolver enumResolver;
	private static HKXDescriptorFactory descriptorFactory;
	private static HKXFile file;

	@BeforeClass
	public static void setup() throws Exception {
		ByteBuffer buffer = FileUtils.resourceToHKXByteBuffer(baseFileResourceName);
		enumResolver = new HKXEnumResolver();
		descriptorFactory = new HKXDescriptorFactory(enumResolver);
		HKXReader reader = new HKXReader(buffer, descriptorFactory, enumResolver);
		file = reader.read();
	}

	@Test
	public void testIfThereIsTheRightNumberOfObjects() {
		assertEquals(1, file.content().size());
	}

	@Test
	public void testIfTheRightObjectIsPresent() {
		for(HKXObject object : file.content())
			assertEquals(HKXType.TYPE_STRUCT, object.getType());
	}

	@Test
	public void testTheObjectName() {
		for(HKXObject object : file.content())
			assertNotNull(object.getName());
	}

	@Test
	public void testTheObjectClassName() {
		for(HKXObject object : file.content())
			assertEquals("hkBaseObject", object.getDescriptor().getName());
	}

	@Test
	public void testTheObjectContentsSize() {
		for(HKXObject object : file.content())
			assertEquals(0, object.members().size());
	}
}
