package net.coolblossom.lycee.core.args.convertors;

import static org.junit.Assert.assertEquals;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.junit.Test;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.StringHolder;

/**
 * <b>DefaultConvertor テストケース</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class DefaultConvertorTest {

	static class TestClass {
		Class<?> clazz;
		String arg;
		Object expected;

		TestClass(@Nonnull final Class<?> clazz, @Nonnull final String arg, @Nonnull final Object expected) {
			this.clazz = clazz;
			this.arg = arg;
			this.expected = expected;
		}

		void test() {
			final Convertor convertor = new DefaultConvertor(clazz);
			assertEquals(expected, convertor.convert(arg));
		}

	}

	@Test
	public void test_normal() {

		Stream.of(
				new TestClass(Boolean.class, "true", Boolean.TRUE)
				, new TestClass(Boolean.class, "false", Boolean.FALSE)
				, new TestClass(Byte.class, "1", Byte.valueOf((byte)1))
				, new TestClass(Short.class, "1", Short.valueOf((short)1))
				, new TestClass(Integer.class, "1", Integer.valueOf(1))
				, new TestClass(Long.class, "1", Long.valueOf(1L))
				, new TestClass(Double.class, "1.0", Double.valueOf(1.0))
				, new TestClass(Float.class, "1.0", Float.valueOf(1.0f))
				, new TestClass(StringHolder.class, "aaa", new StringHolder("aaa"))
				)
		.forEach(TestClass::test);

	}

	static class TestClassNoConstructor extends StringHolder {

		public TestClassNoConstructor() {
			super("Illegal Constructor");
		}

	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_no_constructor() {
		final Convertor convertor = new DefaultConvertor(TestClassNoConstructor.class);
		convertor.convert("AAA");
	}


}
