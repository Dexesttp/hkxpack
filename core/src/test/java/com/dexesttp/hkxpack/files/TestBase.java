package com.dexesttp.hkxpack.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * The content reading tests for all files based on the "base" file.
 */
@Ignore
public class TestBase {
	protected final static String BASE_FILE_RESOURCE_NAME = "/test-base";
	protected final transient HKXFile file;

	/**
	 * Creates a {@link TestBase}.
	 * 
	 * @param file the {@link HXKFile} to test
	 */
	protected TestBase(final HKXFile file) {
		this.file = file;
	}

	@Test
	/**
	 * Tests if there is the right number of objects in the base file (1)
	 */
	public void testIfThereIsTheRightNumberOfObjects() {
		assertEquals(1, file.getContentCollection().size());
	}

	@Test
	/**
	 * Tests if the the read object's Type is the right one (a STRUCT)
	 */
	public void testIfTheRightObjectIsPresent() {
		for (final HKXObject object : file.getContentCollection()) {
			assertEquals(HKXType.TYPE_STRUCT, object.getType());
		}
	}

	@Test
	/**
	 * Tests if the the read object's name is valid (not null)
	 */
	public void testTheObjectName() {
		for (final HKXObject object : file.getContentCollection()) {
			assertNotNull(object.getName());
		}
	}

	@Test
	/**
	 * Tests if the the read object's class name is the right one (hkBaseObject)
	 */
	public void testTheObjectClassName() {
		for (final HKXObject object : file.getContentCollection()) {
			assertEquals("hkBaseObject", object.getDescriptor().getName());
		}
	}

	@Test
	/**
	 * Tests if the the read object's content size is right (0)
	 */
	public void testTheObjectContentsSize() {
		for (final HKXObject object : file.getContentCollection()) {
			assertEquals(0, object.getMembersList().size());
		}
	}
}
