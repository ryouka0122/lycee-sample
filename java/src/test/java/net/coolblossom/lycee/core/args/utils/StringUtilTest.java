package net.coolblossom.lycee.core.args.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void test_isEmpty() {
		assertTrue(StringUtil.isEmpty(null));
		assertTrue(StringUtil.isEmpty(""));
		assertFalse(StringUtil.isEmpty(" "));
		assertFalse(StringUtil.isEmpty("　"));
		assertFalse(StringUtil.isEmpty("A"));
	}

	@Test
	public void test_equals() {
		assertTrue(StringUtil.equals(null, null));
		assertTrue(StringUtil.equals("", ""));
		assertTrue(StringUtil.equals("a", "a"));

		assertFalse(StringUtil.equals(null, ""));
		assertFalse(StringUtil.equals("", null));
		assertFalse(StringUtil.equals(null, "a"));
		assertFalse(StringUtil.equals("a", null));
		assertFalse(StringUtil.equals("a", ""));
		assertFalse(StringUtil.equals("", "a"));

		final String arg1 = "a";
		final String arg2 = "a";
		assertTrue(StringUtil.equals(arg1, arg2));
		assertTrue(StringUtil.equals(arg1, arg1));
	}

	@Test
	public void test_toString() {
		assertEquals("AAA", StringUtil.toString("AAA", "DEFAULT_VALUE"));
		assertEquals("", StringUtil.toString("", "DEFAULT_VALUE"));
		assertEquals("DEFAULT_VALUE", StringUtil.toString(null, "DEFAULT_VALUE"));
	}

}
