package net.coolblossom.lycee.core.args.convertors;

import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.TestClassHelper;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.TestCodeEnum;
import net.coolblossom.lycee.core.args.testutil.TestEnum;
import net.coolblossom.lycee.core.evals.Evaluator;

public class LyceeCodeEnumConvertorTest {

	static class TestCaseLyceeCodeEnum {
		String arg;
		Evaluator evaluator;

		public TestCaseLyceeCodeEnum() {
		}

		TestCaseLyceeCodeEnum input(final String value) {
			arg=value;
			return this;
		}

		TestCaseLyceeCodeEnum isValue(final TestCodeEnum expected) {
			evaluator = TestClassHelper.isValue(expected);
			return this;
		}
	}

	@Test
	public void test_normal() {
		final Convertor convertor = new LyceeCodeEnumConvertor(TestCodeEnum.class);
		Stream.of(
				new TestCaseLyceeCodeEnum().input("1").isValue(TestCodeEnum.TEST1)
				,new TestCaseLyceeCodeEnum().input("2").isValue(TestCodeEnum.TEST2)
				,new TestCaseLyceeCodeEnum().input("3").isValue(TestCodeEnum.TEST3)
				,new TestCaseLyceeCodeEnum().input("4").isValue(TestCodeEnum.TEST4)
				,new TestCaseLyceeCodeEnum().input("5").isValue(TestCodeEnum.TEST5)
				)
		.forEach(testCase -> {
			testCase.evaluator.invoke( convertor.convert(testCase.arg) );
		});
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_no_exists() {
		final Convertor convertor = new LyceeCodeEnumConvertor(TestCodeEnum.class);
		convertor.convert("999");
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_illegal_parent() {
		new LyceeCodeEnumConvertor(TestEnum.class);
	}
}
