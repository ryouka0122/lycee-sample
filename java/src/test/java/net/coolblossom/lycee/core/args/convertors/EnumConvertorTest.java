package net.coolblossom.lycee.core.args.convertors;

import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.TestClassHelper;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.StringHolder;
import net.coolblossom.lycee.core.args.testutil.TestEnum;
import net.coolblossom.lycee.core.evals.Evaluator;

public class EnumConvertorTest {

	static class TestCaseEnum {
		String arg;
		Evaluator evaluator;

		public TestCaseEnum() {
		}

		TestCaseEnum input(final String value) {
			arg=value;
			return this;
		}

		TestCaseEnum isValue(final TestEnum expected) {
			evaluator = TestClassHelper.isValue(expected);
			return this;
		}
	}

	@Test
	public void test_normal() {
		final Convertor convertor = new EnumConvertor(TestEnum.class);
		Stream.of(
				new TestCaseEnum().input("ENUM1").isValue(TestEnum.ENUM1)
				,new TestCaseEnum().input("ENUM2").isValue(TestEnum.ENUM2)
				,new TestCaseEnum().input("ENUM3").isValue(TestEnum.ENUM3)
				,new TestCaseEnum().input("ENUM4").isValue(TestEnum.ENUM4)
				,new TestCaseEnum().input("ENUM5").isValue(TestEnum.ENUM5)
				)
		.forEach(testCase -> {
			testCase.evaluator.invoke( convertor.convert(testCase.arg) );
		});
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_no_exists() {
		final Convertor convertor = new EnumConvertor(TestEnum.class);
		convertor.convert("999");
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_illegal_parent() {
		new EnumConvertor(StringHolder.class);
	}
}
