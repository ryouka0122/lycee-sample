package net.coolblossom.lycee.core.args.descriptors;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.TestClass;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

public class CollectionDescriptorTest {


	@Test
	public void test_string_list() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// 投入するデータ
		final String[] testData = {"A", "1", "2"};

		// 検証クラス
		final TestClass testClass = new TestClass();

		// フィールド名
		final String fieldName = "strList";

		// フィールド
		final Field field = testClass.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);

		// 配列を構成するクラスの型
		final Class<?> componentType = ClassUtil.getActualTypeArguments(field)[0];

		System.out.println("field:"+field.getName());			// field:strAry
		System.out.println("componentType:" + componentType);	// componentType:class java.lang.String

		final CollectionDescriptor desc = new CollectionDescriptor(field);
		for(final String data : testData) {
			desc.set(testClass, fieldName, data);
		}

		System.out.println(testClass.strList.stream().collect(Collectors.joining(",")));
		// -> A,1,2
	}

	@Test
	public void test_integer_list() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// 投入するデータ
		final String[] testData = {"0", "1", "2"};

		// 検証クラス
		final TestClass testClass = new TestClass();

		// フィールド名
		final String fieldName = "intList";

		// フィールド
		final Field field = testClass.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);

		// 配列を構成するクラスの型
		final Class<?> componentType = ClassUtil.getActualTypeArguments(field)[0];

		System.out.println("field:"+field.getName());			// field:strAry
		System.out.println("componentType:" + componentType);	// componentType:class java.lang.String

		final CollectionDescriptor desc = new CollectionDescriptor(field);
		for(final String data : testData) {
			desc.set(testClass, fieldName, data);
		}

		assertEquals(3, testClass.intList.size());
		for(final Object value : testClass.intList) {
			System.out.println(value.getClass()+":"+value);;
		}
		// -> 0,1,2
	}

	@Test
	public void test_character_list() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// 投入するデータ
		final String[] testData = "abbccddde".split("");

		// 検証クラス
		final TestClass testClass = new TestClass();

		// フィールド名
		final String fieldName = "charSet";

		// フィールド
		final Field field = testClass.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);

		// 配列を構成するクラスの型
		final Class<?> componentType = ClassUtil.getActualTypeArguments(field)[0];

		System.out.println("field:"+field.getName());			// field:strAry
		System.out.println("componentType:" + componentType);	// componentType:class java.lang.String

		final CollectionDescriptor desc = new CollectionDescriptor(field);
		for(final String data : testData) {
			desc.set(testClass, fieldName, data);
		}

		assertEquals(5, testClass.charSet.size());
		System.out.println(
				testClass.charSet.stream()
				.map(c->c.toString())
				.collect(Collectors.joining(",")));
		// -> a,b,c,d,e
	}

	static public class TestClassNoAnnotation {
		List<String> argList;
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_no_annotation() throws ReflectiveOperationException {
		new CollectionDescriptor(
				TestClassNoAnnotation.class.getDeclaredField("argList"));
	}


}
