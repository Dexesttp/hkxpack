package com.dexesttp.hkxpack.cli.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.dexesttp.hkxpack.cli.utils.ArgsParser.Options;

/**
 * Tests on an {@link ArgsParser}'s analysis size.
 */
public class ArgsParserSizeTest {
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
	public void itShouldOnlyCatchValuesInTheDefaultOptionIfThereIsNoOptionsInTheArguments() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, ArgsParserTest.TEST2);
		assertEquals(2, result.get("").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchOneExtraOptionIfThereIsAnExtraOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a");
		assertEquals(2, result.size());
	}


	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchOneExtraEmptyOptionIfThereIsAnExtraOptionButNothingBehindIt() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a");
		assertEquals(0, result.get("-a").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchArgumentsUntilTheNextOptionIfTheNextOptionIsGreedy() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a", ArgsParserTest.TEST2, ArgsParserTest.TEST3);
		assertEquals(1, result.get("").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchArgumentsAfterTheNextOptionIfTheNextOptionIsGreedy() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a", ArgsParserTest.TEST2, ArgsParserTest.TEST3);
		assertEquals(2, result.get("-a").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldStopGreedyCatchingAtTheNextOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a", ArgsParserTest.TEST2, "-b", ArgsParserTest.TEST3);
		assertEquals(2, result.get("").size());
		assertEquals(1, result.get("-a").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchAllArgumentsAsDefaultForAZeroSizedOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-b", ArgsParserTest.TEST2, ArgsParserTest.TEST3);
		assertEquals(3, result.get("").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHandleZeroSizedOptionsAtTheEndOfTheArgumentArray() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-b");
		assertEquals(0, result.get("-b").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchTheRightNumberOfArgumentsForASizedOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-c", ArgsParserTest.TEST2, ArgsParserTest.TEST3);
		assertEquals(2, result.get("").size());
		assertEquals(1, result.get("-c").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchZeroArgumentsForAZeroSizedOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-b", ArgsParserTest.TEST2, ArgsParserTest.TEST3);
		assertEquals(0, result.get("-b").size());
	}
}
