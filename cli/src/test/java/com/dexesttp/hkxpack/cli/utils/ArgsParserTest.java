package com.dexesttp.hkxpack.cli.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dexesttp.hkxpack.cli.utils.ArgsParser.Options;

/**
 * {@inheritDoc}
 */
public class ArgsParserTest {
	private static final String TEST = "test";
	private static final String TEST2 = "test2";
	private static final String TEST3 = "test3";
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
	public void itShouldNotCatchSpecialOptionsIfThereIsNoArgument() throws WrongSizeException {
		Options result = argsParser.parse("");
		assertEquals(1, result.size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldReturnAnEmptyStringForARequestThatDoesNotExist() throws WrongSizeException {
		Options result = argsParser.parse("");
		assertEquals("", result.get("", 1));
		assertEquals("", result.get("-a", 0));
		assertEquals("", result.get("-a", 1));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldOnlyCatchValuesInTheDefaultOptionIfThereIsNoOptionsInTheArguments() throws WrongSizeException {
		Options result = argsParser.parse(TEST, TEST2);
		assertEquals(2, result.get("").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchOneExtraOptionIfThereIsAnExtraOption() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a");
		assertEquals(2, result.size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchOneExtraEmptyOptionIfThereIsAnExtraOptionButNothingBehindIt() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a");
		assertNotNull(result.get("-a"));
		assertEquals(0, result.get("-a").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldNotCatchOneExtraEmptyOptionIfThereIsNoExtraOption() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a");
		assertNull(result.get("-b"));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldShowTheOptionAsExistingIfTheOptionExists() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a");
		assertTrue(result.exists("-a"));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldShowTheOptionAsNotExistingIfTheOptionDoesNotExists() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a");
		assertFalse(result.exists("-b"));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldShowTheOptionAsNotExistingIfTheOptionIsNotDefined() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a");
		assertFalse(result.exists("-d"));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchArgumentsUntilTheNextOptionIfTheNextOptionIsGreedy() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a", TEST2, TEST3);
		assertEquals(1, result.get("").size());
		assertEquals(TEST, result.get("", 0));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchArgumentsAfterTheNextOptionIfTheNextOptionIsGreedy() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a", TEST2, TEST3);
		assertEquals(2, result.get("-a").size());
		assertEquals(TEST2, result.get("-a", 0));
		assertEquals(TEST3, result.get("-a", 1));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldStopGreedyCatchingAtTheNextOption() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a", TEST2, "-b", TEST3);
		assertEquals(1, result.get("-a").size());
		assertEquals(TEST2, result.get("-a", 0));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldResumeStandardCatchingAtTheEndOfASizedOption() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a", TEST2, "-b", TEST3);
		assertEquals(2, result.get("").size());
		assertEquals(TEST, result.get("", 0));
		assertEquals(TEST3, result.get("", 1));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchZeroArgumentsForAZeroSizedOption() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-b", TEST2, TEST3);
		assertEquals(0, result.get("-b").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchAllArgumentsAsDefaultForAZeroSizedOption() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-b", TEST2, TEST3);
		assertEquals(3, result.get("").size());
		assertEquals(TEST, result.get("", 0));
		assertEquals(TEST2, result.get("", 1));
		assertEquals(TEST3, result.get("", 2));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHandleZeroSizedOptionsAtTheEndOfTheArgumentArray() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-b");
		assertEquals(0, result.get("-b").size());
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchTheRightNumberOfArgumentsForASizedOption() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-c", TEST2, TEST3);
		assertEquals(1, result.get("-c").size());
		assertEquals(TEST2, result.get("-c", 0));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldCatchTheRightNumberOfArgumentsAsDefaultForASizedOption() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-c", TEST2, TEST3);
		assertEquals(2, result.get("").size());
		assertEquals(TEST, result.get("", 0));
		assertEquals(TEST3, result.get("", 1));
	}

	@Test(expected = WrongSizeException.class)
	/**
	 * {@inheritDoc}
	 */
	public void itShouldThrowAnExceptionIfThereWasNotEnoughArgumentForASizedOptionWithoutAnythingAfter() throws WrongSizeException {
		argsParser.parse(TEST, "-c");
	}

	@Test(expected = WrongSizeException.class)
	/**
	 * {@inheritDoc}
	 */
	public void itShouldThrowAnExceptionIfThereWasNotEnoughArgumentForASizedOptionWithThingsAfter() throws WrongSizeException {
		argsParser.parse(TEST, "-c", "-b", TEST2);
	}

	@Test(expected = WrongSizeException.class)
	/**
	 * {@inheritDoc}
	 */
	public void itShouldThrowAnExceptionIfThereWasAnOtherAppearanceOfASizedOption() throws WrongSizeException {
		argsParser.parse(TEST, "-c", TEST2, "-c", TEST3);
	}
	
	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHandleAComplexCorrectCommandLine() throws WrongSizeException {
		Options result = argsParser.parse(TEST, "-a", TEST2, "-b", "-c", TEST3, "test4");
		// Existence
		assertTrue(result.exists("-a"));
		assertTrue(result.exists("-b"));
		assertTrue(result.exists("-c"));
		assertFalse(result.exists("-d"));
		// Sizes
		assertEquals(2, result.get("").size());
		assertEquals(1, result.get("-a").size());
		assertEquals(0, result.get("-b").size());
		assertEquals(1, result.get("-c").size());
		// Values
		assertEquals(TEST, result.get("", 0));
		assertEquals(TEST2, result.get("-a", 0));
		assertEquals(TEST3, result.get("-c", 0));
		assertEquals("test4", result.get("", 1));
	}
	
	@Test(expected = WrongSizeException.class)
	/**
	 * {@inheritDoc}
	 */
	public void itShouldHandleAComplexIncorrectCommandLine() throws WrongSizeException {
		argsParser.parse(TEST, "-a", TEST2, "-b", TEST3, "test4", "-c");
	}
}
