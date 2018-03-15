package net.coolblossom.lycee.core.args.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Test;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.TestClassSimpleCase;

public class LyceeArgsMapExecutorTest {

	static int getIntField(final Object test, final String name) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		final Field field = test.getClass().getDeclaredField(name);
		field.setAccessible(true);
		return field.getInt(test);
	}

	@Test
	public void test_normal() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		final String[] args = { "--argInt", "1" };

		final TestClassSimpleCase test = LyceeArgsMapper.createAndMap(TestClassSimpleCase.class, args).execute();

		assertEquals(1, getIntField(test, "argInt"));

	}


	@Test
	public void test_nonargs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		final String[] args = {};
		try {
			final TestClassSimpleCase test = LyceeArgsMapper.createAndMap(TestClassSimpleCase.class, args).execute();
			fail();
		}catch(final LyceeRuntimeException e) {
			;
		}
	}

	@Test
	public void test_nullargs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		final String[] args = null;
		try {
			final TestClassSimpleCase test = LyceeArgsMapper.createAndMap(TestClassSimpleCase.class, args).execute();
			fail();
		}catch(final LyceeRuntimeException e) {
			;
		}
	}


}
