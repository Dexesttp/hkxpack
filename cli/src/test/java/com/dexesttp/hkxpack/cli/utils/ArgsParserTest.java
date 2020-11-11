package com.dexesttp.hkxpack.cli.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ArgsParserEmptyTest.class, ArgsParserExistTest.class, ArgsParserSizeTest.class,
		ArgsParserValuesTest.class, ArgsParserExceptionTest.class, ArgsParserComplexTest.class, })
/**
 * {@inheritDoc}
 */
public class ArgsParserTest {
	public static final String TEST = "test";
	public static final String TEST2 = "test2";
	public static final String TEST3 = "test3";
	public static final String TEST4 = "test4";
}
