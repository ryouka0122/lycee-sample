package net.coolblossom.lycee.core.args.convertors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.testutil.TestClassSimpleCase;
import net.coolblossom.lycee.core.args.testutil.TestClassTypeAnnotation;

public class ConvertorFactoryTest {

	@Before
	public void beforeTest() {
		ConvertorFactory.getInstance().removeAllConvertor();
		;
	}

	@Test
	public void test_singleton() {
		final ConvertorFactory factory1 = ConvertorFactory.getInstance();
		final ConvertorFactory factory2 = ConvertorFactory.getInstance();

		assertEquals(factory1, factory2);
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


	public static class UpperConvertor extends Convertor {

		List<String> strList;

		public UpperConvertor(final Class<?> clazz, final LyceeArg lyceeArg) {
			super(String.class);
			strList = new ArrayList<>();
		}

		@Override
		public Object convert(final String str) {
			return str;
		}

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

	@Test
	public void check_constructor() {
		Stream.of(TestClassSimpleCase.class.getDeclaredFields())
		.peek(f -> f.setAccessible(true))
		.forEach(field -> {
			printVarArgs(field.getName(), field.getType(), field.getDeclaredAnnotation(LyceeArg.class));
		});
	}


	private void printVarArgs(final String name, final Object ...args) {
		System.out.println(name + " : " +
				Stream.of(args)
		.peek(arg -> {
			if(arg instanceof Proxy) {
				System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
				System.out.println(arg + " is Proxy class.");
				System.out.println("Proxy.getInvocationHandler(arg).getClass() : "+ Proxy.getInvocationHandler(arg).getClass());
				System.out.println("((Proxy)arg).getClass() : "+ ((Proxy)arg).getClass());
				System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
			}
		})
		.map(arg -> arg!=null ? arg.getClass().toString() :"null")
		.collect(Collectors.joining(","))
				);
	}

}
