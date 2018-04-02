package net.coolblossom.lycee.core.args.mappers;

import static net.coolblossom.lycee.core.evals.Evaluators.isNull;
import static net.coolblossom.lycee.core.evals.Evaluators.isValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.TestClassHelper;
import net.coolblossom.lycee.core.args.LyceeArgs;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.TestClassAnnotation;
import net.coolblossom.lycee.core.args.testutil.TestClassModifier;
import net.coolblossom.lycee.core.args.testutil.TestClassNoAnnotation;
import net.coolblossom.lycee.core.args.testutil.TestClassSimpleCase;
import net.coolblossom.lycee.core.args.testutil.TestClassTypeAnnotation;
import net.coolblossom.lycee.core.commons.collect.Tuple;
import net.coolblossom.lycee.core.evals.Evaluator;

public class LyceeArgsMapperTest {


	@Test
	public void test_args_normal() {
		final String[] args = { "--argInt", "1", "--argStr", "AAA" };

		final TestClassSimpleCase test = LyceeArgs.createAndMap(TestClassSimpleCase.class, args).execute();

		assertEquals(1, TestClassHelper.getIntField(test, "argInt"));
		assertEquals("AAA", TestClassHelper.getFieldValue(test, "argStr"));
	}


	static class TestClassArgsVariation {
		String args[];
		Evaluator expectedInt;
		Evaluator expectedStr;

		public TestClassArgsVariation(final Evaluator evalArgInt, final Evaluator evalArgStr, final String ...args) {
			expectedInt = evalArgInt;
			expectedStr = evalArgStr;
			this.args = args;
		}

		public void test() {
			final TestClassSimpleCase test = LyceeArgs.createAndMap(TestClassSimpleCase.class, args).execute();

			expectedInt.invoke(TestClassHelper.getFieldValue(test, "argInt"));
			expectedStr.invoke(TestClassHelper.getFieldValue(test, "argStr"));
		}
	}

	@Test
	public void test_args_variation() {
		Stream.of(
				new TestClassArgsVariation(isValue(1), isValue("AAA"), "--argInt", "1", "--argStr", "AAA" ),	// 正常
				new TestClassArgsVariation(isValue(1), isNull()      , "--argInt", "1", "--argStr"        ),	// 2つ目の値が欠損
				new TestClassArgsVariation(isValue(1), isNull()      , "--argInt", "1",             "AAA" ),	// 2つ目のキーが欠損
				new TestClassArgsVariation(isValue(0), isValue("AAA"),             "1", "--argStr", "AAA" ),	// 1つ目のキーが欠損
				new TestClassArgsVariation(isValue(0), isNull()      , "--argInt"                         ),	// 1つ目のキーしかない
				new TestClassArgsVariation(isValue(0), isNull()      ,             "1"                    ),	// キーなし
				new TestClassArgsVariation(isValue(0), isNull()      ,             "1",             "AAA" ),	// キーなし（複数）
				new TestClassArgsVariation(isValue(1), isNull()      , "--argInt", "1",   "argStr", "AAA" ),	// キーになっていない
				new TestClassArgsVariation(isValue(0), isNull()      ,   "argInt", "1",   "argStr", "AAA" )		// キーになっていない（複数）
				)
		.forEach(TestClassArgsVariation::test);
	}


	@Test()
	public void test_args_illegal() throws ReflectiveOperationException {
		Stream.of(
				new String[]{ },
				null
				)
		.forEach(args -> {
			try {
				LyceeArgs.createAndMap(TestClassSimpleCase.class, args).execute();
				fail();
			}catch(final LyceeRuntimeException e) {
				// success
			}
		});
	}

	@Test
	public void test_args_null_variation() {
		Stream.of(
				new String[]{"--argInt", null, },
				new String[]{"--argInt",  "1", "--argLong",  null, },
				new String[]{"--argInt", null, "--argLong", "100", },
				new String[]{"--argInt",  "1",        null, "100", },
				new String[]{      null, "1", "--argLong", "100", },
				new String[]{ null, null, null, null, }
				)
		.forEach(args -> {
			try {
				LyceeArgs.createAndMap(TestClassSimpleCase.class, args).execute();
				fail();
			}catch(final IllegalArgumentException e) {
				// success
			}
		});
	}


	static class TestCaseConfiguratedAnnotation {
		String field;
		Evaluator expected;
		String[] args;
		public TestCaseConfiguratedAnnotation(final String field, final Evaluator expected, final String ...args) {
			this.field = field;
			this.expected = expected;
			this.args = args;
		}
	}

	@Test
	public void test_configuratedAnnotation_field() {
		Stream.of(
				//                                  field name, validation,  arguments...
				// argStr1のテスト
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
			final TestClassAnnotation actual = LyceeArgs.createAndMap(TestClassAnnotation.class, test.args).execute();

			test.expected.invoke(TestClassHelper.getFieldValue(actual, test.field));
		});

	}

	@Test
	public void test_configuratedAnnotation_full() {
		final String[] args = {
				"--argStr1", "A",
				"--arg2"   , "B",
				"--argStr3", "C",
				"--arg4"   , "D",
				"--argStr5", "E",
				"--arg6"   , "F",
				"--argStr7", "G",
				"--arg8"   , "H",
				"--argStr9", "I",
		};

		final TestClassAnnotation actual = LyceeArgs.createAndMap(TestClassAnnotation.class, args).execute();

		Stream.of(
				Tuple.make("argStr1", isValue("A"))
				,Tuple.make("argStr2", isValue("B"))
				,Tuple.make("argStr3", isValue("C"))
				,Tuple.make("argStr4", isValue("D"))
				,Tuple.make("argStr5", isValue("E"))
				,Tuple.make("argStr6", isValue("F"))
				,Tuple.make("argStr7", isValue("G"))
				,Tuple.make("argStr8", isValue("H"))
				,Tuple.make("argStr9", isValue("I"))
				)
		.forEach(expected -> {
			final Evaluator evaluator = expected.get(1);
			evaluator.invoke(TestClassHelper.getFieldValue(actual, expected.get(0)) );
		});
	}

	@Test
	public void test_type_annotation() {
		final String[] args = {
				"--argInt", "1",
				"--argLong", "100",
				"--argStr", "a",
				"--argChar", "A",
				"--argDouble", "0.001",
		};

		final TestClassTypeAnnotation actual = LyceeArgs.createAndMap(
				TestClassTypeAnnotation.class, args).execute();
		Stream.of(
				Tuple.make("argInt", isValue(1))
				,Tuple.make("argLong", isValue(100L))
				,Tuple.make("argStr", isValue("a"))
				,Tuple.make("argChar", isValue('A'))
				,Tuple.make("argDouble", isValue(0.001))
				)
		.forEach(expected -> {
			final Evaluator evaluator = expected.get(1);
			evaluator.invoke(TestClassHelper.getFieldValue(actual, expected.get(0)) );
		});
	}

	@Test
	public void test_no_annotation() {
		final String[] args = {
				"--arg1", "1",
				"--arg2", "2",
		};
		final TestClassNoAnnotation actual = LyceeArgs.createAndMap(TestClassNoAnnotation.class, args).execute();

		Stream.of(
				Tuple.make("arg1", isValue(100))
				,Tuple.make("arg2", isValue(100))
				)
		.forEach(expected -> {
			final Evaluator evaluator = expected.get(1);
			evaluator.invoke(TestClassHelper.getFieldValue(actual, expected.get(0)) );
		});
	}

	@Test
	public void test_modifier_normal() {
		Stream.of(
				Tuple.make(TestClassModifier.TestClassPublic.class, "argPublic"),
				Tuple.make(TestClassModifier.TestClassProtected.class, "argProtected")
				)
		.forEach(tuple-> {
			final String name = tuple.get(1);
			final String[] args = { "--" + name, "1" };
			final Object actual = LyceeArgs.createAndMap(tuple.get(0), args).execute();
			assertEquals(0, TestClassHelper.getIntField(actual, name));
		});
	}

	@Test
	public void test_modifier_illegal() {
		Stream.of(
				Tuple.make(TestClassModifier.TestClassPackage.class, "argPackage"),
				Tuple.make(TestClassModifier.TestClassPackageFinal.class, "argPackageFinal"),
				Tuple.make(TestClassModifier.TestClassPublicFinal.class, "argPublicFinal"),
				Tuple.make(TestClassModifier.TestClassProtectedFinal.class, "argProtectedFinal"),
				Tuple.make(TestClassModifier.TestClassPrivate.class, "argPrivate"),
				Tuple.make(TestClassModifier.TestClassPrivateFinal.class, "argPrivateFinal")
				)
		.forEach(tuple-> {
			final String name = tuple.get(1);
			final String[] args = { "--" + name, "1" };
			final Object actual = LyceeArgs.createAndMap(tuple.get(0), args).execute();
			assertNotEquals(1, TestClassHelper.getIntField(actual, name));
		});
	}

}
