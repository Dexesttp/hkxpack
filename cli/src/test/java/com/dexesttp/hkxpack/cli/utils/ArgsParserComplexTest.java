package com.dexesttp.hkxpack.cli.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dexesttp.hkxpack.cli.utils.ArgsParser.Options;

/**
 * Tests a complex {@link ArgsParser} test case over several tests.
 */
public class ArgsParserComplexTest {
	private transient Options result;

	@Before
	/**
	 * {@inheritDoc}
	 */
	public void setUp() throws WrongSizeException {
		ArgsParser argsParser = new ArgsParser();
		argsParser.addOption("-a");
		argsParser.addOption("-b", 0);
		argsParser.addOption("-c", 1);
		result = argsParser.parse(ArgsParserTest.TEST, "-a", ArgsParserTest.TEST2, "-b", "-c", ArgsParserTest.TEST3, ArgsParserTest.TEST4);
	}

	
	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHaveAllDefinedOptions() throws WrongSizeException {
		// Existence
		assertTrue(result.exists("-a"));
		assertTrue(result.exists("-b"));
		assertTrue(result.exists("-c"));
	}
	
	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldNotHaveOptionD() throws WrongSizeException {
		// Existence
		assertFalse(result.exists("-d"));
	}
	
	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHaveTheRightBaseSize() throws WrongSizeException {
		// Sizes
		assertEquals(2, result.get("").size());
	}
	
	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHaveTheRightASize() throws WrongSizeException {
		// Sizes
		assertEquals(1, result.get("-a").size());
	}
	
	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHaveTheRightBSize() throws WrongSizeException {
		// Sizes
		assertEquals(0, result.get("-b").size());
	}
	
	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHaveTheRightCSize() throws WrongSizeException {
		// Sizes
		assertEquals(1, result.get("-c").size());
	}
	
	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHaveTheRightAValue() throws WrongSizeException {
		// Values
		assertEquals(ArgsParserTest.TEST2, result.get("-a", 0));
	}
	
	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHaveTheRightCValue() throws WrongSizeException {
		// Values
		assertEquals(ArgsParserTest.TEST3, result.get("-c", 0));
	}
	
	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHaveTheRightBaseValues() throws WrongSizeException {
		// Values
		assertEquals(ArgsParserTest.TEST, result.get("", 0));
		assertEquals(ArgsParserTest.TEST4, result.get("", 1));
	}
}
