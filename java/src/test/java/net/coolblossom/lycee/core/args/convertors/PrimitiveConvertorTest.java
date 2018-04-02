package net.coolblossom.lycee.core.args.convertors;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.evals.Evaluator;
import net.coolblossom.lycee.core.evals.Evaluators;

/**
 * <b>PrimitiveConvertorのテストケース</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class PrimitiveConvertorTest {

	static class TestCasePrimitiveConvertor {
		Class<?> type;
		Evaluator evaluator;
		String arg;

		public TestCasePrimitiveConvertor() {
		}

		TestCasePrimitiveConvertor type(final Class<?> type) {
			this.type = type;
			return this;
		}

		<T> TestCasePrimitiveConvertor isValue(final T value) {
			evaluator = Evaluators.isValue(value);
			return this;
		}

		TestCasePrimitiveConvertor input(final String value) {
			arg = value;
			return this;
		}

	}


	@Test
	public void test_normal() {
		Stream.of(
				new TestCasePrimitiveConvertor().type(char.class).input("a").isValue('a')
				,new TestCasePrimitiveConvertor().type(byte.class).input("1").isValue((byte)1)
				,new TestCasePrimitiveConvertor().type(short.class).input("1").isValue((short)1)
				,new TestCasePrimitiveConvertor().type(int.class).input("1").isValue(1)
				,new TestCasePrimitiveConvertor().type(long.class).input("100").isValue(100L)
				,new TestCasePrimitiveConvertor().type(double.class).input("3.14").isValue(3.14)
				,new TestCasePrimitiveConvertor().type(float.class).input("2.0").isValue(2.0f)
				)
		.forEach(testCase -> {
			final Convertor convertor = new PrimitiveConvertor(testCase.type);
			testCase.evaluator.invoke(convertor.convert(testCase.arg));
		});
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_exception() {
		new PrimitiveConvertor(Map.class);
	}


}
