package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.args.testutil.TestClass;

public class ArrayDescriptorTest {

	@Test
	public void test_standard_array() throws NoSuchFieldException, SecurityException {
		// 投入するデータ
		final String[] testData = {"A", "1", "2"};

		// 検証クラス
		final TestClass testClass = new TestClass();

		// フィールド名
		final String fieldName = "strAry";

		// フィールド
		final Field field = testClass.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		// フィールドの型
		final Class<?> fieldType = field.getType();
		// 配列を構成するクラスの型
		final Class<?> componentType = fieldType.getComponentType();

		System.out.println("field:"+field.getName());			// field:strAry
		System.out.println("fieldType:"+fieldType);				// fieldType:class [Ljava.lang.String
		System.out.println("isArray:" + fieldType.isArray());	// isArray:true
		System.out.println("componentType:" + componentType);	// componentType:class java.lang.String

		final ArrayDescriptor desc = new ArrayDescriptor(field, componentType);
		for(final String data : testData) {
			desc.set(testClass, data);
		}

		System.out.println(Stream.of(testClass.strAry).collect(Collectors.joining(",")));
		// -> A,1,2
	}

	@Test
	public void test_class_array() throws NoSuchFieldException, SecurityException {
		// 投入するデータ
		final String[] testData = {"A", "1", "2"};

		// 検証クラス
		final TestClass testClass = new TestClass();

		// フィールド名
		final String fieldName = "clsAry";

		// フィールド
		final Field field = testClass.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		// フィールドの型
		final Class<?> fieldType = field.getType();
		// 配列を構成するクラスの型
		final Class<?> componentType = fieldType.getComponentType();

		System.out.println("field:"+field.getName());			// field:strAry
		System.out.println("fieldType:"+fieldType);				// fieldType:class [Ljava.lang.String
		System.out.println("isArray:" + fieldType.isArray());	// isArray:true
		System.out.println("componentType:" + componentType);	// componentType:class java.lang.String

		final ArrayDescriptor desc = new ArrayDescriptor(field, componentType);
		for(final String data : testData) {
			desc.set(testClass, data);
		}

		System.out.println(Stream.of(testClass.clsAry)
				.map(o -> o.toString())
				.collect(Collectors.joining(",")));
		// -> A,1,2
	}

}
