package com.dexesttp.hkxpack.hkxreader;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;

import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadException;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.utils.FileUtils;

/**
 * Basic "don't throw an exception" tests for the {@link HKXReader} class.
 */
public class HKXReaderExceptionTest {
	public static final String TEST_BASE_FILE_RESOURCE = "/test-base.hkx";
	private transient HKXDescriptorFactory descriptorFactory;
	private transient HKXEnumResolver enumResolver;

	@Before
	/**
	 * Setup the test environnement
	 */
	public void setUp() throws ClassListReadException {
		this.enumResolver = new HKXEnumResolver();
		this.descriptorFactory = new HKXDescriptorFactory(enumResolver);
	}

	@Test
	/**
	 * Tests for reading from a {@link ByteBuffer}
	 */
	public void baseFileReadingUsingByteBufferDoesntThrowAnException() throws IOException, InvalidPositionException {
		ByteBuffer toRead = FileUtils.resourceToHKXByteBuffer(TEST_BASE_FILE_RESOURCE);
		HKXReader reader = new HKXReader(toRead, descriptorFactory, enumResolver);
		reader.read();
	}

	@Test
	/**
	 * Tests from reading from a {@link File}
	 */
	public void baseFileReadingUsingFileDoesntThrowAnException() throws IOException, InvalidPositionException {
		File toRead = FileUtils.resourceToTemporaryFile(TEST_BASE_FILE_RESOURCE);
		HKXReader reader = new HKXReader(toRead, descriptorFactory, enumResolver);
		reader.read();
	}

	@Test
	/**
	 * Tests from reading from a {@link File}
	 */
	public void baseFileReadingUsingUnknownClassThrowsAnException() throws IOException, InvalidPositionException {
		// TODO create a proper test with a file containing an unknown class file.
		ByteBuffer toRead = FileUtils.resourceToHKXByteBuffer(TEST_BASE_FILE_RESOURCE);
		HKXReader reader = new HKXReader(toRead, descriptorFactory, enumResolver);
		try {
			reader.read();
			// fail("Method did not throw exception.");
		} catch (ClassFileReadException e) {
			return;
		} catch (Throwable t) {
			fail("Expected ClassFileRead exception, got " + t.getMessage());
		}
	}
}
