package com.dexesttp.hkxpack.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

public abstract class TestBase {
	protected final static String baseFileResourceName = "/test-base";
	protected final HKXFile file;

	public TestBase(HKXFile file) {
		this.file = file;
	}
	
	@Test
	public void testIfThereIsTheRightNumberOfObjects() {
		assertEquals(1, file.getContentCollection().size());
	}

	@Test
	public void testIfTheRightObjectIsPresent() {
		for(HKXObject object : file.getContentCollection())
			assertEquals(HKXType.TYPE_STRUCT, object.getType());
	}

	@Test
	public void testTheObjectName() {
		for(HKXObject object : file.getContentCollection())
			assertNotNull(object.getName());
	}

	@Test
	public void testTheObjectClassName() {
		for(HKXObject object : file.getContentCollection())
			assertEquals("hkBaseObject", object.getDescriptor().getName());
	}

	@Test
	public void testTheObjectContentsSize() {
		for(HKXObject object : file.getContentCollection())
			assertEquals(0, object.getMembersList().size());
	}
}
