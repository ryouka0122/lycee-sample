package net.coolblossom.lycee.core.args.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ObjectUtilTest {

	@Test
	public void test_orNullDefault() {
		assertEquals("A", ObjectUtil.orNullDefault("A", () -> "B"));
		assertEquals("", ObjectUtil.orNullDefault("", () -> "B"));
		assertEquals("B", ObjectUtil.orNullDefault(null, () -> "B"));
	}

	@Test(expected=NullPointerException.class)
	public void test_orNullDefault_Exception() {
		ObjectUtil.orNullDefault(null, () -> null);
	}

}
