package net.coolblossom.lycee.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;

import net.coolblossom.lycee.core.args.LyceeArgs;
import net.coolblossom.lycee.core.args.descriptors.MapDescriptor;
import net.coolblossom.lycee.core.args.enums.LyceeDateFormat;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.TestClassMapCase;

/**
 * <b>カバレッジ用テストケース</b>
 * <p>
 * 通常のテストケースとは違い、カバレッジを満たすことを目的としたテストケースです。
 * </p>
 * @author ryouka
 *
 */
public class JunkTest {

	@Test
	public void test_public_constructor() {
		// for LyceeArgsMapper#LyceeArgsMapper()
		new LyceeArgs();
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_MapDescriptor() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final TestClassMapCase testClass = new TestClassMapCase();
		final Field field = TestClassMapCase.SingleMapCase.class.getDeclaredField("argStrMap");
		field.setAccessible(true);
		final MapDescriptor desc = new MapDescriptor(field, String.class);
		assertTrue(desc.matches(""));
		assertTrue(desc.matches("A"));
		assertTrue(desc.matches("1"));
		desc.setValue(testClass, "value");
	}

	@Test
	public void test_LyceeDateFormat() {
		final LyceeDateFormat ldf = LyceeDateFormat.COMPACT_YYYY_MM_DD;
		assertEquals("yyyyMMdd", ldf.getFormat());
		assertEquals("yyyyMMdd", ldf.toString());
	}

	@Test
	public void test_LyceeRuntimeException() {
		final String message = "Error Message";
		final LyceeRuntimeException e = new LyceeRuntimeException(message);
		assertEquals(message, e.getMessage());
	}


}
