package com.dexesttp.hkxpack.cli.utils;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the behavior of thrown exceptions from {@link ArgsParser}.
 */
public class ArgsParserExceptionTest {
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

	@Test(expected = WrongSizeException.class)
	/**
	 * {@inheritDoc}
	 */
	public void itShouldThrowAnExceptionIfThereWasNotEnoughArgumentForASizedOptionWithoutAnythingAfter() throws WrongSizeException {
		argsParser.parse(ArgsParserTest.TEST, "-c");
	}

	@Test(expected = WrongSizeException.class)
	/**
	 * {@inheritDoc}
	 */
	public void itShouldThrowAnExceptionIfThereWasNotEnoughArgumentForASizedOptionWithThingsAfter() throws WrongSizeException {
		argsParser.parse(ArgsParserTest.TEST, "-c", "-b", ArgsParserTest.TEST2);
	}

	@Test(expected = WrongSizeException.class)
	/**
	 * {@inheritDoc}
	 */
	public void itShouldThrowAnExceptionIfThereWasAnOtherAppearanceOfASizedOption() throws WrongSizeException {
		argsParser.parse(ArgsParserTest.TEST, "-c", ArgsParserTest.TEST2, "-c", ArgsParserTest.TEST3);
	}
	
	@Test(expected = WrongSizeException.class)
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHandleAComplexIncorrectCommandLine() throws WrongSizeException {
		argsParser.parse(ArgsParserTest.TEST, "-a", ArgsParserTest.TEST2, "-b", ArgsParserTest.TEST3, ArgsParserTest.TEST4, "-c");
	}
}
