package com.dexesttp.hkxpack.cli.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.dexesttp.hkxpack.cli.utils.ArgsParser.Options;

/**
 * Tests on an {@link ArgsParser}'s output values.
 */
public class ArgsParserValuesTest {
	private transient ArgsParser argsParser;

	@Before
	/**
	 * {@inheritDoc}
	 */
	public void setUp() {
		argsParser = new ArgsParser();
		argsParser.addOption("-a");
		argsParser.addOption("-b", 0);
		argsParser.addOption("-c", 1);
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldStopGreedyCatchingAtTheNextOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a", ArgsParserTest.TEST2, "-b", ArgsParserTest.TEST3);
		assertEquals(ArgsParserTest.TEST2, result.get("-a", 0));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchArgumentsUntilTheNextOptionIfTheNextOptionIsGreedy() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a", ArgsParserTest.TEST2, ArgsParserTest.TEST3);
		assertEquals(ArgsParserTest.TEST, result.get("", 0));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchArgumentsAfterTheNextOptionIfTheNextOptionIsGreedy() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a", ArgsParserTest.TEST2, ArgsParserTest.TEST3);
		assertEquals(ArgsParserTest.TEST2, result.get("-a", 0));
		assertEquals(ArgsParserTest.TEST3, result.get("-a", 1));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldResumeStandardCatchingAtTheEndOfASizedOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a", ArgsParserTest.TEST2, "-b", ArgsParserTest.TEST3);
		assertEquals(ArgsParserTest.TEST, result.get("", 0));
		assertEquals(ArgsParserTest.TEST3, result.get("", 1));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchAllArgumentsAsDefaultForAZeroSizedOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-b", ArgsParserTest.TEST2, ArgsParserTest.TEST3);
		assertEquals(ArgsParserTest.TEST, result.get("", 0));
		assertEquals(ArgsParserTest.TEST2, result.get("", 1));
		assertEquals(ArgsParserTest.TEST3, result.get("", 2));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchTheRightValuesOfArgumentsForASizedOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-c", ArgsParserTest.TEST2, ArgsParserTest.TEST3);
		assertEquals(ArgsParserTest.TEST2, result.get("-c", 0));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchTheRightValuesOfArgumentsAsDefaultForASizedOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-c", ArgsParserTest.TEST2, ArgsParserTest.TEST3);
		assertEquals(ArgsParserTest.TEST, result.get("", 0));
		assertEquals(ArgsParserTest.TEST3, result.get("", 1));
	}
}
