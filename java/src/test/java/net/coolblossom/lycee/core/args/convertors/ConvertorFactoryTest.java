package net.coolblossom.lycee.core.args.convertors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.StringHolder;
import net.coolblossom.lycee.core.args.testutil.TestClassSimpleCase;
import net.coolblossom.lycee.core.args.testutil.TestClassTypeAnnotation;
import net.coolblossom.lycee.core.commons.collect.Tuple;

public class ConvertorFactoryTest {

	public static class UpperConvertor extends Convertor {

		public UpperConvertor(final Class<?> clazz, final LyceeArg lyceeArg) {
			super(String.class);
		}

		@Override
		public Object convert(final String str) {
			return str.toUpperCase();
		}

	}

	public static class LowerConvertor extends Convertor {

		public LowerConvertor(final LyceeArg lyceeArg) {
			super(String.class);
		}

		@Override
		public Object convert(final String str) {
			return str.toLowerCase();
		}

	}


	@Test
	public void test_singleton() {
		final ConvertorFactory factory1 = ConvertorFactory.getInstance();
		final ConvertorFactory factory2 = ConvertorFactory.getInstance();

		assertNotEquals(factory1, factory2);
	}

	@Test
	public void test_manipulation_method() {
		final ConvertorFactory factory = ConvertorFactory.getInstance();

		// ------------------------------------------------------
		// addConvertor
		Stream.of(
				Tuple.make(null, null),
				Tuple.make(String.class, null),
				Tuple.make(null, UpperConvertor.class)
				)
		.forEach(tuple -> {
			try {
				factory.addConvertor(tuple.get(0),tuple.get(1));
				fail();
			}catch(final NullPointerException e) {
				// success
			}
		});
		factory.addConvertor(String.class, UpperConvertor.class);

		// ------------------------------------------------------
		// removeConvertor
		factory.removeConvertor(StringHolder.class);	// 未登録のクラス
		factory.removeConvertor(String.class);			// 登録済みのクラス

		// ------------------------------------------------------
		// removeAllConvertor
		factory.removeAllConvertor();

		// ------------------------------------------------------
		// containsBaseClass
		assertFalse(factory.containsBaseClass(String.class));

		factory.addConvertor(String.class, UpperConvertor.class);
		assertTrue(factory.containsBaseClass(String.class));

		// ------------------------------------------------------
		// containsConvertor
		factory.containsConvertor(UpperConvertor.class);
		;
	}


	static class TestClassCreateConvertor {
		Class<? extends Convertor> expectedClazz;
		String[] fieldNames;
		public TestClassCreateConvertor(final Class<? extends Convertor> expectedClazz, final String ...fieldNames) {
			this.expectedClazz = expectedClazz;
			this.fieldNames = fieldNames;
		}

		void test(final Class<?> targetTestClass, final ConvertorFactory factory) {
			Stream.of(fieldNames)
			.forEach(name -> {
				try {
					final Field field = targetTestClass.getDeclaredField(name);
					final Convertor convertor = factory.createConvertor(field.getType(), field.getAnnotation(LyceeArg.class));
					assertEquals(expectedClazz, convertor.getClass());
				} catch (NoSuchFieldException | SecurityException e) {
					fail();
				}
			});
		}
	}


	@Test
	public void test_normal() {
		final ConvertorFactory factory = ConvertorFactory.getInstance();

		Stream.of(
				new TestClassCreateConvertor(PrimitiveConvertor.class, "argInt", "argDouble")				// Primitive型
				,new TestClassCreateConvertor(StringConvertor.class, "argStr")								// String型
				,new TestClassCreateConvertor(DateConvertor.class, "argDateDefault", "argDateDashYmd")		// Date型
				,new TestClassCreateConvertor(LyceeCodeEnumConvertor.class, "argCodeEnum")					// LyceeCodeEnum型
				,new TestClassCreateConvertor(EnumConvertor.class, "argEnum")								// Enum型
				,new TestClassCreateConvertor(WrapperConvertor.class, "argCharWrap")						// Wrapper
				,new TestClassCreateConvertor(DefaultConvertor.class, "argLongWrap")						// Default
				)
		.forEach(testClass -> {
			testClass.test(TestClassSimpleCase.class, factory);
		});

		Stream.of(
				new TestClassCreateConvertor(PrimitiveConvertor.class, "argInt")		// Primitive型
				,new TestClassCreateConvertor(StringConvertor.class, "argStr")			// String型
				,new TestClassCreateConvertor(DateConvertor.class, "argDate")			// Date型
				,new TestClassCreateConvertor(DefaultConvertor.class, "argLong")		// Default
				)
		.forEach(testClass -> {
			testClass.test(TestClassTypeAnnotation.class, factory);
		});
	}

	@Test
	public void test_custom_convertor() {
		final ConvertorFactory factory = ConvertorFactory.getInstance();
		factory.addConvertor(String.class, UpperConvertor.class);
		Stream.of(
				new TestClassCreateConvertor(UpperConvertor.class, "argStr")
				)
		.forEach(testClass -> {
			testClass.test(TestClassSimpleCase.class, factory);
		});

	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_custom_convertor_no_constructor() {
		final ConvertorFactory factory = ConvertorFactory.getInstance();
		factory.addConvertor(String.class, LowerConvertor.class);
		Stream.of(
				new TestClassCreateConvertor(LowerConvertor.class, "argStr")
				)
		.forEach(testClass -> {
			testClass.test(TestClassSimpleCase.class, factory);
		});

	}

}
