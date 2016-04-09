package com.dexesttp.hkxpack.resources.byteutils;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * Tests for the {@link SLongByteUtils} class
 */
public class SLongByteUtilsTest {
	@Test
	/**
	 * Test
	 */
	public void getSLongSize2WorksFor0() {
		assertArrayEquals("SLong(2) : 0 => 0",
				new byte[]{0, 0},
				SLongByteUtils.fromLong(0, 2));
	}

	@Test
	/**
	 * Test
	 */
	public void getSLongSize2WorksFor1() {
		assertArrayEquals("SLong(2) : 1 => 1",
				new byte[]{1, 0},
				SLongByteUtils.fromLong(1, 2));
	}

	@Test
	/**
	 * Test
	 */
	public void getSLongSize2WorksForMinus1() {
		assertArrayEquals("SLong(2) : -1 => -1",
				new byte[]{-1, -1},
				SLongByteUtils.fromLong(-1, 2));
	}

	@Test
	/**
	 * Test
	 */
	public void getSILongSize2WorksForMaxValue() {
		assertArrayEquals("SLong(2) : 32767 => 32767",
				new byte[]{-1, 127},
				SLongByteUtils.fromLong(32767, 2));
	}

	@Test
	/**
	 * Test
	 */
	public void getSIntSize2WorksForMinValue() {
		assertArrayEquals("SLong(2) : -32768 => -32768",
				new byte[]{0, -128},
				SLongByteUtils.fromLong(-32768, 2));
	}
}
