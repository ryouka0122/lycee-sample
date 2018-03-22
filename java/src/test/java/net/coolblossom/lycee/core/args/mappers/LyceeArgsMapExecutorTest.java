package net.coolblossom.lycee.core.args.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.TestClassHelper;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.TestClassAnnotation;
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

	@Test(expected=LyceeRuntimeException.class)
	public void test_nonargs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		final String[] args = {};
		LyceeArgsMapper.createAndMap(TestClassSimpleCase.class, args).execute();
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_nullargs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		final String[] args = null;
		LyceeArgsMapper.createAndMap(TestClassSimpleCase.class, args).execute();
	}


	static class TestCaseConfiguratedAnnotation {
		String field;
		Consumer<String> expected;
		String[] args;
		public TestCaseConfiguratedAnnotation(final String field, final Consumer<String> expected, final String ...args) {
			this.field = field;
			this.expected = expected;
			this.args = args;
		}
	}

	private Consumer<String> isNull() {
		return (v) -> assertNull(v);
	}

	private Consumer<String> isValue(final String expected) {
		return (v) -> assertEquals(expected, v);
	}

	@Test
	public void test_configuratedAnnotation_field() {
		Stream.of(
				// argStr1のテスト（デグレ観点）
				new TestCaseConfiguratedAnnotation("argStr1", isValue("1"), "--argStr1", "1")

				// argStr2のテスト
				,new TestCaseConfiguratedAnnotation("argStr2", isNull()    , "--argStr2", "2")
				,new TestCaseConfiguratedAnnotation("argStr2", isValue("2"), "--arg2"   , "2")

				// argStr3のテスト
				,new TestCaseConfiguratedAnnotation("argStr3", isValue("3"), "--argStr3", "3")
				,new TestCaseConfiguratedAnnotation("argStr3", isNull(),     "--arg3"   , "3")

				// argStr4のテスト
				,new TestCaseConfiguratedAnnotation("argStr4", isValue("4"), "--argStr4", "4")
				,new TestCaseConfiguratedAnnotation("argStr4", isValue("4"), "--arg4"   , "4")

				// argStr5のテスト
				,new TestCaseConfiguratedAnnotation("argStr5", isValue("5"), "--argStr5", "5")
				,new TestCaseConfiguratedAnnotation("argStr5", isNull()    , "--arg5"   , "5")

				// argStr6のテスト
				,new TestCaseConfiguratedAnnotation("argStr6", isNull()    , "--argStr6", "6")
				,new TestCaseConfiguratedAnnotation("argStr6", isValue("6"), "--arg6"   , "6")
				,new TestCaseConfiguratedAnnotation("argStr6", isValue("6"), "--param1" , "6")

				// argStr7のテスト
				,new TestCaseConfiguratedAnnotation("argStr7", isValue("7"), "--argStr7", "7")
				,new TestCaseConfiguratedAnnotation("argStr7", isNull()    , "--arg7"   , "7")
				,new TestCaseConfiguratedAnnotation("argStr7", isValue("7"), "--param2" , "7")

				// argStr8のテスト
				,new TestCaseConfiguratedAnnotation("argStr8", isValue("8"), "--argStr8", "8")
				,new TestCaseConfiguratedAnnotation("argStr8", isValue("8"), "--arg8"   , "8")
				,new TestCaseConfiguratedAnnotation("argStr8", isNull()    , "--param"  , "8")

				// argStr9のテスト
				,new TestCaseConfiguratedAnnotation("argStr9", isValue("9"), "--argStr9", "9")
				,new TestCaseConfiguratedAnnotation("argStr9", isNull()    , "--arg9"   , "9")
				,new TestCaseConfiguratedAnnotation("argStr9", isNull()    , "--param"  , "9")

				)
		.forEach(test -> {
			final TestClassAnnotation actual = LyceeArgsMapper.createAndMap(TestClassAnnotation.class, test.args).execute();

			test.expected.accept(TestClassHelper.getFieldValue(actual, test.field));
		});

	}

	@Test
	public void test_configuratedAnnotation_full() {
		final String[] args = {
				"--argStr1", "A",
				"--argStr2", "B",
				"--argStr3", "C",
				"--argStr4", "D",
				"--argStr5", "E",
				"--argStr6", "F",
				"--argStr7", "G",
				"--argStr8", "H",
				"--argStr9", "I",
		};

	}
}
