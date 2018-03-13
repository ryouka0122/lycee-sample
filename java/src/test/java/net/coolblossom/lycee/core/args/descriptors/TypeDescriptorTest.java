package net.coolblossom.lycee.core.args.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import net.coolblossom.lycee.core.args.mappers.LyceeArgsMapper;
import net.coolblossom.lycee.core.args.testutil.TestClassSimpleCase;

@RunWith(Theories.class)
public class TypeDescriptorTest {

	static class TestCase {
		@Nonnull
		public String[] args;
		public TestCase(@Nonnull final String ...args) {
			this.args = args;
		}
	}

	/** テスト用データパターン */
	@DataPoints
	public static TestCase[] PARAMS = {
			new TestCase("--argChar", "a"),
			new TestCase("--argChar", "1"),
			new TestCase("--argChar", "あ"),
			new TestCase("--argBool", "true"),
			new TestCase("--argByte", "1"),
			new TestCase("--argShort", "1"),
			new TestCase("--argInt", "1"),
			new TestCase("--argInt", "-1"),
			new TestCase("--argLong", "10000"),
			new TestCase("--argFloat", "0.123"),
			new TestCase("--argDouble", "3.14"),
			new TestCase("--argCharWrap", "a"),
			new TestCase("--argCharWrap", "1"),
			new TestCase("--argCharWrap", "あ"),
			new TestCase("--argLongWrap", "10000"),
			new TestCase("--argStr", "a"),
			new TestCase("--argDateDefault", "20180101"),
			new TestCase("--argDateDashYmd", "2018-01-01"),
			new TestCase("--argEnum", "ENUM1"),
			new TestCase("--argCodeEnum", "1")
	};

	/** ログ出力用カウンタ */
	int testCount=1;


	/**
	 * <b>テストケース</b>
	 * <p>
	 * </p>
	 *
	 * @param testCase テスト用データ
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Theory
	public void test(final TestCase testCase)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		System.out.println("TEST CASE["+testCount++ +"]" +
				Stream.of(testCase.args).collect(Collectors.joining(",")));

		final TestClassSimpleCase result = LyceeArgsMapper
				.createAndMap(TestClassSimpleCase.class, testCase.args)
				.execute();

		final String fieldName = testCase.args[0].replaceAll("-", "");
		final TestClassSimpleCase base = new TestClassSimpleCase();


		final Field[] baseFields = base.getClass().getDeclaredFields();
		final Field[] resultFields = result.getClass().getDeclaredFields();

		for(int i=0, length = baseFields.length ; i<length ; i++) {
			final Field baseField = baseFields[i];
			final Field resultField = resultFields[i];
			baseField.setAccessible(true);
			resultField.setAccessible(true);
			if(baseField.getName().equals(fieldName)) {
				assertNotEquals(baseField.get(base), resultField.get(result));
			} else {
				assertEquals(baseField.get(base), resultField.get(result));
			}
		}
	}

}
