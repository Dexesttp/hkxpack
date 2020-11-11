package com.dexesttp.hkxpack.cli.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.dexesttp.hkxpack.cli.utils.ArgsParser.Options;

/**
 * Tests for the {@link ArgsParser}, on empty argument list
 */
public class ArgsParserEmptyTest {
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
	public void itShouldReturnAnEmptyStringForARequestThatDoesNotExist1() throws WrongSizeException {
		Options result = argsParser.parse("");
		assertEquals("", result.get("", 1));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldReturnAnEmptyStringForARequestThatDoesNotExist2() throws WrongSizeException {
		Options result = argsParser.parse("");
		assertEquals("", result.get("-a", 0));
	}

	@Test
	/**
	 * {@inheritDoc}
	 */
	public void itShouldReturnAnEmptyStringForARequestThatDoesNotExist3() throws WrongSizeException {
		Options result = argsParser.parse("");
		assertEquals("", result.get("-a", 1));
	}

}
