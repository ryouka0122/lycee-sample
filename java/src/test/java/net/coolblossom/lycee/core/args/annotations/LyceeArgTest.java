package net.coolblossom.lycee.core.args.annotations;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.Test;

import net.coolblossom.lycee.core.TestClassHelper;
import net.coolblossom.lycee.core.args.LyceeArgs;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.StringHolder;
import net.coolblossom.lycee.core.args.testutil.TestClassMapCase;
import net.coolblossom.lycee.core.evals.Evaluator;
import net.coolblossom.lycee.core.evals.Evaluators;

public class LyceeArgTest {

	static class TestCase {

		@Nonnull
		Class<?> clazz;

		@Nullable
		String[] args;

		@Nonnull
		List<Evaluator> evaluators;

		@Nonnull
		List<Evaluator> exceptionEvaluatuors;

		TestCase(@Nonnull final Class<?> clazz) {
			this.clazz = clazz;
			evaluators = new ArrayList<>();
			exceptionEvaluatuors = new ArrayList<>();
		}

		TestCase testData(@Nonnull final String ...data) {
			args = data;
			return this;
		}

		TestCase isNull(@Nonnull final String fieldName) {
			evaluators.add( Evaluators.isNull(fieldName) );
			return this;
		}

		TestCase notExists(@Nonnull final String fieldName, @Nonnull final Object key) {
			final Evaluator evaluator = Evaluators.notExists(key);
			evaluators.add( actual -> evaluator.invoke(TestClassHelper.getFieldValue(actual, fieldName)) );
			return this;
		}

		TestCase isValue(@Nonnull final String targetField, @Nonnull final Object expected) {
			final Evaluator evaluator = Evaluators.isValue(expected);
			evaluators.add( actual -> evaluator.invoke(TestClassHelper.getFieldValue(actual, targetField)) );
			return this;
		}

		TestCase isValue(@Nonnull final String targetField, @Nonnull final Object key, @Nonnull final Object expected) {
			final Evaluator evaluator = Evaluators.isValue(key, expected);
			evaluators.add( actual -> evaluator.invoke(TestClassHelper.getFieldValue(actual, targetField)) );
			return this;
		}

		TestCase isEmpty(@Nonnull final String fieldName) {
			final Evaluator evaluator = Evaluators.isEmpty();
			evaluators.add(actual -> {
				evaluator.invoke( TestClassHelper.getFieldValue(actual, fieldName) );;
			});
			return this;
		}

		TestCase isType(@Nonnull final String fieldName, final Class<?> clazz) {
			final Evaluator evaluator = Evaluators.isType(clazz);
			evaluators.add(actual -> {
				evaluator.invoke( TestClassHelper.getFieldValue(actual, fieldName) );;
			});
			return this;
		}

		TestCase catchException(final Class<? extends Exception> clazz) {
			exceptionEvaluatuors.add(Evaluators.isType(clazz));
			return this;
		}

		void test() {
			final Object actual;
			try {
				actual = LyceeArgs.createAndMap(clazz, args).execute();
			}catch(final Exception e) {
				exceptionEvaluatuors.forEach(evaluator -> {
					evaluator.invoke(e);
				});
				return;
			}
			evaluators.forEach( evaluator -> {
				evaluator.invoke(actual);
			});
		}
	}

	@Test
	public void test_normal() {
		Stream.of(
				// ---------------------------------------------------------------------
				// 1. Map<String,String>1つ / マッピングするデータ：1組
				new TestCase(TestClassMapCase.SingleMapCase.class)
				.testData("--key1", "value1")
				.isValue("argStrMap", "key1", "value1"),
				// ---------------------------------------------------------------------
				// 2. Map<String,String>1つ / マッピングするデータ：2組
				new TestCase(TestClassMapCase.SingleMapCase.class)
				.testData("--key1", "value1", "--key2", "value2")
				.isValue("argStrMap", "key1", "value1")
				.isValue("argStrMap", "key2", "value2"),
				// ---------------------------------------------------------------------
				// 3. Map<String,String>1つ / マッピングするデータ：1組
				new TestCase(TestClassMapCase.StringLikeKeyCase.class)
				.testData("--key1", "value1")
				.isValue("argStrMap", new StringHolder("key1"), "value1"),
				// ---------------------------------------------------------------------
				// 4. Map<String,String>1つ / マッピングするデータ：2組
				new TestCase(TestClassMapCase.StringLikeKeyCase.class)
				.testData("--key1", "value1", "--key2", "value2")
				.isValue("argStrMap", new StringHolder("key1"), "value1")
				.isValue("argStrMap", new StringHolder("key2"), "value2"),
				// ---------------------------------------------------------------------
				// 5. HashMap<String,String>1つ / マッピングするデータ：2組
				new TestCase(TestClassMapCase.HashMapCase.class)
				.testData("--key1", "1", "--key2", "2")
				.isValue("argStrMap", "key1", new Integer(1))
				.isValue("argStrMap", "key2", new Integer(2)),
				// ---------------------------------------------------------------------
				// 6. Map1つ / マッピングするデータ：2組
				new TestCase(TestClassMapCase.ClassAnnotationCase.class)
				.testData("--key1", "value1", "--key2", "value2")
				.isType("argStrMap", LinkedHashMap.class)
				.isValue("argStrMap", "key1", "value1")
				.isValue("argStrMap", "key2", "value2"),
				// ---------------------------------------------------------------------
				// 7. Map1つ / マッピングするデータ：2組
				new TestCase(TestClassMapCase.ClassAnnotationCaseHashMap.class)
				.testData("--key1", "value1", "--key2", "value2")
				.isType("argStrMap", TreeMap.class)
				.isValue("argStrMap", "key1", "value1")
				.isValue("argStrMap", "key2", "value2"),
				// ---------------------------------------------------------------------
				// 8. Map1つ＋List2つ（StringとInteger） / マッピングするデータ：3組（Map->Listの順番）
				new TestCase(TestClassMapCase.ComplexCase.class)
				.testData("--key1", "value1", "--key2", "value2", "--intList", "1", "--strList", "あ")
				.isValue("argStrMap", "key1", "value1")
				.isValue("argStrMap", "key2", "value2")
				.isEmpty("intList")
				.isEmpty("strList"),
				// ---------------------------------------------------------------------
				// 9. Map1つ＋List2つ（StringとInteger） / マッピングするデータ：3組（List->Map->Listの順番）
				new TestCase(TestClassMapCase.ComplexCase.class)
				.testData("--intList", "1", "--key1", "value1", "--key2", "value2", "--strList", "あ")
				.isValue("argStrMap", "key1", "value1")
				.isValue("argStrMap", "key2", "value2")
				.isEmpty("intList")
				.isEmpty("strList"),
				// ---------------------------------------------------------------------
				// 10. Map1つ＋List2つ（StringとInteger） / マッピングするデータ：3組（List->Mapの順番）
				new TestCase(TestClassMapCase.ComplexCase.class)
				.testData("--intList", "1", "--strList", "あ", "--key1", "value1", "--key2", "value2")
				.isValue("argStrMap", "key1", "value1")
				.isValue("argStrMap", "key2", "value2")
				.isEmpty("intList")
				.isEmpty("strList"),
				// ---------------------------------------------------------------------
				// 11. Map1つ（デフォルト指定）＋List2つ（StringとInteger） / マッピングするデータ：3組（Map->Listの順番）
				new TestCase(TestClassMapCase.ComplexCaseUseDefault.class)
				.testData("--key1", "value1", "--key2", "value2", "--intList", "1", "--strList", "あ")
				.isValue("argStrMap", "key1", "value1")
				.isValue("argStrMap", "key2", "value2")
				.isValue("intList", new Integer(1))
				.isValue("strList", "あ"),
				// ---------------------------------------------------------------------
				// 12. Map1つ（デフォルト指定）＋List2つ（StringとInteger） / マッピングするデータ：3組（List->Map->Listの順番）
				new TestCase(TestClassMapCase.ComplexCaseUseDefault.class)
				.testData("--intList", "1", "--key1", "value1", "--key2", "value2", "--strList", "あ")
				.isValue("argStrMap", "key1", "value1")
				.isValue("argStrMap", "key2", "value2")
				.isValue("intList", new Integer(1))
				.isValue("strList", "あ"),
				// ---------------------------------------------------------------------
				// 13. Map1つ（デフォルト指定）＋List2つ（StringとInteger） / マッピングするデータ：3組（List->Mapの順番）
				new TestCase(TestClassMapCase.ComplexCaseUseDefault.class)
				.testData("--intList", "1", "--strList", "あ", "--key1", "value1", "--key2", "value2")
				.isValue("argStrMap", "key1", "value1")
				.isValue("argStrMap", "key2", "value2")
				.isValue("intList", new Integer(1))
				.isValue("strList", "あ")
				)
		.forEach(TestCase::test);
	}

	@Test
	public void test_illegal() {
		Stream.of(
				// ---------------------------------------------------------------------
				// 1. Mapのキーが適切でない
				new TestCase(TestClassMapCase.IllegalKeyCase.class)
				.testData("--key1", "value1", "--key2", "value2")
				.catchException(LyceeRuntimeException.class),
				// ---------------------------------------------------------------------
				// 2. Mapが複数ある
				new TestCase(TestClassMapCase.TwinMapCase.class)
				.testData("--key1", "value1", "--key2", "value2")
				.catchException(LyceeRuntimeException.class)
				)
		.forEach(TestCase::test);
	}


}
