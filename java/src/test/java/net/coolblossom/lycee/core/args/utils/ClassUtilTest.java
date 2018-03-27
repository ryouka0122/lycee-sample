package net.coolblossom.lycee.core.args.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.args.convertors.Convertor;
import net.coolblossom.lycee.core.args.enums.LyceeCodeEnum;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.StringHolder;
import net.coolblossom.lycee.core.args.testutil.TestClass;
import net.coolblossom.lycee.core.args.testutil.TestClassMapCase;
import net.coolblossom.lycee.core.args.testutil.TestCodeEnum;

/**
 * <b></b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class ClassUtilTest {

	@Test
	public void test_newInstance_normal() {

		final String resStr1 = ClassUtil.newInstance(String.class);
		assertEquals("", resStr1);

		final String resStr2 = ClassUtil.newInstance(String.class, "abc");
		assertEquals("abc", resStr2);

		final Integer resInt = ClassUtil.newInstance(Integer.class, "123");
		assertEquals(123, resInt.intValue());

		final List<?> resList = ClassUtil.newInstance(ArrayList.class);
		assertEquals(ArrayList.class, resList.getClass());
		assertEquals(0, resList.size());

	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_newInstance_no_constructor() {
		// Integerクラスに対して存在しない「引数がDoubleのコンストラクタ」を呼び出す
		ClassUtil.newInstance(Integer.class, 3.14);
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_newInstance_private_constructor() {
		ClassUtil.newInstance(StringUtil.class);
	}

	static class TestCase_getActualTypeArguments {
		Class<?> testClazz;
		String name;
		Class<?> clazz[];
		int actualTypeLength;
		TestCase_getActualTypeArguments(final Class<?> testClazz, final String name, final Class<?> ...clazz) {
			this.testClazz = testClazz;
			this.name = name;
			this.clazz = clazz;
			actualTypeLength = clazz.length;
		}

		Field getField() throws NoSuchFieldException, SecurityException {
			return testClazz.getDeclaredField(name);
		}
	}

	@Test
	@SuppressWarnings("null")
	public void test_getActualTypeArguments() {
		// テストケースの準備
		Stream.of(
				//  TestCase_getActualTypeArguments(testclass, fieldname, check type ...)
				new TestCase_getActualTypeArguments(TestClass.class, "intArg" , Integer.class),
				new TestCase_getActualTypeArguments(TestClass.class, "strArg" ,  String.class),
				new TestCase_getActualTypeArguments(TestClass.class, "longArg",    long.class),
				new TestCase_getActualTypeArguments(TestClass.class, "strList",  String.class),
				new TestCase_getActualTypeArguments(TestClass.class, "intList", Integer.class),
				new TestCase_getActualTypeArguments(TestClass.class, "strMap" ,  String.class,  String.class),
				new TestCase_getActualTypeArguments(TestClass.class, "intMap" ,  String.class, Integer.class)
				)
		.forEach(testCase -> {
			try {
				// テスト実施
				System.out.println(testCase.name + " / " + testCase.clazz);
				// テストクラスから検証フィールド取得
				final Field field = testCase.getField();

				// パラメータタイプのリストを取得
				final Class<?>[] actualTypes = ClassUtil.getActualTypeArguments(field);

				// 個数の確認
				assertEquals(testCase.actualTypeLength, actualTypes.length);

				// クラスの種類を確認
				for(int i=0 ; i<testCase.actualTypeLength ; i++) {
					assertEquals(testCase.clazz[i], actualTypes[i]);
				}
			} catch (NoSuchFieldException | SecurityException e) {
				fail(e.getMessage());
			}
		});
	}

	/**
	 * <b></b>
	 * <p>
	 * </p>
	 *
	 */
	@Test
	public void test_getActualTypeArguments_class() {

		// テストケースの準備
		Stream.of(
				//  TestCase_getActualTypeArguments(testclass, fieldname, check type ...)
				new TestCase_getActualTypeArguments(TestClass.class, "clsArg" ,   StringHolder.class),
				new TestCase_getActualTypeArguments(TestClass.class, "clsList",   StringHolder.class),
				new TestCase_getActualTypeArguments(TestClassMapCase.SingleMapCase.class, "argStrMap" ,  String.class, String.class)
				)
		.forEach(testCase -> {
			try {
				// テスト実施
				System.out.println(testCase.name + " / " + testCase.clazz);
				// テストクラスから検証フィールド取得
				final Field field = testCase.getField();

				// パラメータタイプのリストを取得
				final Class<?>[] actualTypes = ClassUtil.getActualTypeArguments(field);

				// 個数の確認
				assertEquals(testCase.actualTypeLength, actualTypes.length);

				// クラスの種類を確認
				for(int i=0 ; i<testCase.actualTypeLength ; i++) {
					assertEquals(testCase.clazz[i], actualTypes[i]);
				}
			} catch (NoSuchFieldException | SecurityException e) {
				fail(e.getMessage());
			}
		});
	}

	@Test
	public void test_isParent() {
		final Class<?> clazz = TestCodeEnum.class;

		if(ClassUtil.isParent(clazz, Enum.class)) {
			System.out.println(clazz.getName() + " is child of Enum class");
		}
		if(ClassUtil.isParent(clazz, LyceeCodeEnum.class)) {
			System.out.println(clazz.getName() + " is child of LyceeCodeEnum class");
		}
		if(ClassUtil.isParent(clazz, TestCodeEnum.class)) {
			System.out.println(clazz.getName() + " is child of TestCodeEnum class");
		}

		final LyceeCodeEnum[] codeEnumAry = (LyceeCodeEnum[]) clazz.getEnumConstants();
		Stream.of(codeEnumAry)
		.forEach(System.out::println);
	}

	@Test
	public void test_isImplemetntationClass() {
		assertTrue(ClassUtil.isImplementationClass(String.class));
		assertFalse(ClassUtil.isImplementationClass(List.class));
		assertFalse(ClassUtil.isImplementationClass(Convertor.class));
	}


}
