package net.coolblossom.lycee.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.args.LyceeArgs;
import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.descriptors.MapDescriptor;
import net.coolblossom.lycee.core.args.enums.LyceeDateFormat;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.TestClassMapCase;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

/**
 * <b>カバレッジ用テストケース</b>
 * <p>
 * 通常のテストケースとは違い、カバレッジを満たすことを目的としたテストケースです。
 * </p>
 * @author ryouka
 *
 */
public class JunkTest {

	@Test
	public void test_public_constructor() {
		// for LyceeArgsMapper#LyceeArgsMapper()
		new LyceeArgs();
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_MapDescriptor1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = TestClassMapCase.ComplexCase.class.getDeclaredField("strList");
		field.setAccessible(true);
		final MapDescriptor desc = new MapDescriptor(field);
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_MapDescriptor2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = TestClassMapCase.SingleMapCase.class.getDeclaredField("argStrMap");
		field.setAccessible(true);
		final MapDescriptor desc = new MapDescriptor(field);
		final TestClassMapCase.SingleMapCase object = new TestClassMapCase.SingleMapCase();
		assertTrue(desc.matches(""));
		assertTrue(desc.matches("A"));
		assertTrue(desc.matches("1"));
		desc.setValue(object, "value");
	}

	@Test
	public void test_LyceeDateFormat() {
		final LyceeDateFormat ldf = LyceeDateFormat.COMPACT_YYYY_MM_DD;
		assertEquals("yyyyMMdd", ldf.getFormat());
		assertEquals("yyyyMMdd", ldf.toString());
	}

	@Test
	public void test_LyceeRuntimeException() {
		final String message = "Error Message";
		final LyceeRuntimeException e = new LyceeRuntimeException(message);
		assertEquals(message, e.getMessage());
	}

	//	@Test
	public void check_type_and_annotation() {
		final Class<?> clazz = TestClassMapCase.TwinMapCase.class;

		Stream.of(clazz.getDeclaredFields())
		.forEach(field -> {
			final LyceeArg arg = field.getDeclaredAnnotation(LyceeArg.class);
			System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * *");
			System.out.println("Annotation Information");
			System.out.println(arg);
			System.out.println(arg.annotationType());
			System.out.println("name:" + arg.name());
			System.out.println("alias:" + arg.alias());
			System.out.println("isDefault:" + arg.isDefault());
			System.out.println("dateFormat:" + arg.dateFormat());
			System.out.println("value:" + arg.value());

			System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * *");
			System.out.println("Field Information");
			System.out.println("name:" + field.getName());
			final Class<?> actualTypes[] = ClassUtil.getActualTypeArguments(field);
			System.out.println("types:" +
					Stream.of(actualTypes)
			.map(Class::getCanonicalName)
			.collect(Collectors.joining(",", "[","]"))
					);
			printActualType(arg.value(), actualTypes);
		});
	}

	private void printActualType(final Class<?> value, final Class<?> actualTypes[]) {
		if(ClassUtil.isParent(value, Collection.class)) {
			System.out.println("★実クラス："
					+ value.getSimpleName() + "<"+actualTypes[0].getSimpleName()+">");
		}else if(ClassUtil.isParent(value, Map.class)) {
			System.out.println("★実クラス："
					+ value.getSimpleName() +
					IntStream.of(0,1).mapToObj(n->actualTypes[n].getSimpleName())
					.collect(Collectors.joining(", ", "<", ">")));
		}else{
			System.out.println("★実クラス：" + actualTypes[0].getSimpleName());
		}

	}



}
