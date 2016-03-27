package com.dexesttp.hkxpack.cli.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dexesttp.hkxpack.cli.utils.ArgsParser.Options;

public class ArgsParserTest {
	private ArgsParser argsParser;

	@Before
	public void setup() {
		argsParser = new ArgsParser();
		argsParser.addOption("-a");
		argsParser.addOption("-b", 0);
		argsParser.addOption("-c", 1);
	}

	@Test
	public void itShouldNotCatchSpecialOptionsIfThereIsNoArgument() throws Exception {
		Options result = argsParser.parse("");
		assertEquals(1, result.size());
	}

	@Test
	public void itShouldReturnAnEmptyStringForARequestThatDoesNotExist() throws Exception {
		Options result = argsParser.parse("");
		assertEquals("", result.get("", 1));
		assertEquals("", result.get("-a", 0));
		assertEquals("", result.get("-a", 1));
	}

	@Test
	public void itShouldOnlyCatchValuesInTheDefaultOptionIfThereIsNoOptionsInTheArguments() throws Exception {
		Options result = argsParser.parse("test", "test2");
		assertEquals(2, result.get("").size());
	}

	@Test
	public void itShouldCatchOneExtraOptionIfThereIsAnExtraOption() throws Exception {
		Options result = argsParser.parse("test", "-a");
		assertEquals(2, result.size());
	}

	@Test
	public void itShouldCatchOneExtraEmptyOptionIfThereIsAnExtraOptionButNothingBehindIt() throws Exception {
		Options result = argsParser.parse("test", "-a");
		assertNotNull(result.get("-a"));
		assertEquals(0, result.get("-a").size());
	}

	@Test
	public void itShouldNotCatchOneExtraEmptyOptionIfThereIsNoExtraOption() throws Exception {
		Options result = argsParser.parse("test", "-a");
		assertNull(result.get("-b"));
	}

	@Test
	public void itShouldShowTheOptionAsExistingIfTheOptionExists() throws Exception {
		Options result = argsParser.parse("test", "-a");
		assertTrue(result.exists("-a"));
	}

	@Test
	public void itShouldShowTheOptionAsNotExistingIfTheOptionDoesNotExists() throws Exception {
		Options result = argsParser.parse("test", "-a");
		assertFalse(result.exists("-b"));
	}

	@Test
	public void itShouldShowTheOptionAsNotExistingIfTheOptionIsNotDefined() throws Exception {
		Options result = argsParser.parse("test", "-a");
		assertFalse(result.exists("-d"));
	}

	@Test
	public void itShouldCatchArgumentsUntilTheNextOptionIfTheNextOptionIsGreedy() throws Exception {
		Options result = argsParser.parse("test", "-a", "test2", "test3");
		assertEquals(1, result.get("").size());
		assertEquals("test", result.get("", 0));
	}

	@Test
	public void itShouldCatchArgumentsAfterTheNextOptionIfTheNextOptionIsGreedy() throws Exception {
		Options result = argsParser.parse("test", "-a", "test2", "test3");
		assertEquals(2, result.get("-a").size());
		assertEquals("test2", result.get("-a", 0));
		assertEquals("test3", result.get("-a", 1));
	}

	@Test
	public void itShouldStopGreedyCatchingAtTheNextOption() throws Exception {
		Options result = argsParser.parse("test", "-a", "test2", "-b", "test3");
		assertEquals(1, result.get("-a").size());
		assertEquals("test2", result.get("-a", 0));
	}

	@Test
	public void itShouldResumeStandardCatchingAtTheEndOfASizedOption() throws Exception {
		Options result = argsParser.parse("test", "-a", "test2", "-b", "test3");
		assertEquals(2, result.get("").size());
		assertEquals("test", result.get("", 0));
		assertEquals("test3", result.get("", 1));
	}

	@Test
	public void itShouldCatchZeroArgumentsForAZeroSizedOption() throws Exception {
		Options result = argsParser.parse("test", "-b", "test2", "test3");
		assertEquals(0, result.get("-b").size());
	}

	@Test
	public void itShouldCatchAllArgumentsAsDefaultForAZeroSizedOption() throws Exception {
		Options result = argsParser.parse("test", "-b", "test2", "test3");
		assertEquals(3, result.get("").size());
		assertEquals("test", result.get("", 0));
		assertEquals("test2", result.get("", 1));
		assertEquals("test3", result.get("", 2));
	}

	@Test
	public void itShouldCatchTheRightNumberOfArgumentsForASizedOption() throws Exception {
		Options result = argsParser.parse("test", "-c", "test2", "test3");
		assertEquals(1, result.get("-c").size());
		assertEquals("test2", result.get("-c", 0));
	}

	@Test
	public void itShouldCatchTheRightNumberOfArgumentsAsDefaultForASizedOption() throws Exception {
		Options result = argsParser.parse("test", "-c", "test2", "test3");
		assertEquals(2, result.get("").size());
		assertEquals("test", result.get("", 0));
		assertEquals("test3", result.get("", 1));
	}

	@Test(expected = WrongSizeException.class)
	public void itShouldThrowAnExceptionIfThereWasNotEnoughArgumentForASizedOptionWithoutAnythingAfter() throws Exception {
		argsParser.parse("test", "-c");
	}

	@Test(expected = WrongSizeException.class)
	public void itShouldThrowAnExceptionIfThereWasNotEnoughArgumentForASizedOptionWithThingsAfter() throws Exception {
		argsParser.parse("test", "-c", "-b", "test2");
	}

	@Test(expected = WrongSizeException.class)
	public void itShouldThrowAnExceptionIfThereWasAnOtherAppearanceOfASizedOption() throws Exception {
		argsParser.parse("test", "-c", "test2", "-c", "test3");
	}
	
	@Test
	public void itShouldHandleAComplexCorrectCommandLine() throws Exception {
		Options result = argsParser.parse("test", "-a", "test2", "-b", "-c", "test3", "test4");
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
		assertEquals("test", result.get("", 0));
		assertEquals("test2", result.get("-a", 0));
		assertEquals("test3", result.get("-c", 0));
		assertEquals("test4", result.get("", 1));
	}
	
	@Test(expected = WrongSizeException.class)
	public void itShouldHandleAComplexIncorrectCommandLine() throws Exception {
		argsParser.parse("test", "-a", "test2", "-b", "test3", "test4", "-c");
	}
}
