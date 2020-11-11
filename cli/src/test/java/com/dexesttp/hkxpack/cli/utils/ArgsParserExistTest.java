package com.dexesttp.hkxpack.cli.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dexesttp.hkxpack.cli.utils.ArgsParser.Options;

/**
 * Tests the behavior of {@link ArgsParser.Options#exists(String)}
 */
public class ArgsParserExistTest {
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
	public void itShouldNotCatchOneExtraEmptyOptionIfThereIsNoExtraOption() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a");
		assertNull(result.get("-b"));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldShowTheOptionAsExistingIfTheOptionExists() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a");
		assertTrue(result.exists("-a"));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldShowTheOptionAsNotExistingIfTheOptionDoesNotExists() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a");
		assertFalse(result.exists("-b"));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldShowTheOptionAsNotExistingIfTheOptionIsNotDefined() throws WrongSizeException {
		Options result = argsParser.parse(ArgsParserTest.TEST, "-a");
		assertFalse(result.exists("-d"));
	}
}
