package com.dexesttp.hkxpack.resources.byteutils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the {@link ULongByteUtils} class
 */
public class ULongByteUtilsTest {
	@Test
	/**
	 * Test
	 */
	public void getUIntSize2ShouldWorkFor0() {
		assertArrayEquals("Long(2) : 0 => 0", new byte[] { 0, 0 }, ULongByteUtils.fromLong(0, 2));
	}

	@Test
	/**
	 * Test
	 */
	public void getUIntSize2ShouldWorkFor1() {
		assertArrayEquals("Long(2) : 1 => 1", new byte[] { 1, 0 }, ULongByteUtils.fromLong(1, 2));
	}

	@Test
	/**
	 * Test
	 */
	public void getUIntSize2ShouldWorkForMaxValue() {
		assertArrayEquals("Long(2) : 65535 => 65535", new byte[] { -1, -1 }, ULongByteUtils.fromLong(65535, 2));
	}

	@Test
	/**
	 * Test
	 */
	public void getUIntSize2ShouldWorkFor30() {
		assertArrayEquals("Long(2) : 30 => 30", new byte[] { 30, 0 }, ULongByteUtils.fromLong(30, 2));
	}
}
