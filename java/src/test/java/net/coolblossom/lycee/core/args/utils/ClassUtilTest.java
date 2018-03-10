package net.coolblossom.lycee.core.args.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.args.annotations.LyceeArgCollection;
import net.coolblossom.lycee.core.args.testutil.StringHolder;
import net.coolblossom.lycee.core.args.testutil.TestClass;

/**
 * <b></b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class ClassUtilTest {

	static class TestCase {
		String name;
		Class<?> clazz[];
		int actualTypeLength;
		TestCase(final String name, final Class<?> ...clazz) {
			this.name = name;
			this.clazz = clazz;
			actualTypeLength = clazz.length;
		}
	}

	@SuppressWarnings("null")
	@Test
	public void test_getActualTypeArguments() {
		// テストケースの準備
		Stream.of(
				//  TestCase(fieldname, check type ...)
				new TestCase("intArg" , Integer.class),
				new TestCase("strArg" ,  String.class),
				new TestCase("longArg",    long.class),
				new TestCase("strList",  String.class),
				new TestCase("intList", Integer.class),
				new TestCase("strMap" ,  String.class,  String.class),
				new TestCase("intMap" ,  String.class, Integer.class)
				)
		.forEach(testCase -> {
			try {
				// テスト実施
				System.out.println(testCase.name + " / " + testCase.clazz);
				// テストクラスから検証フィールド取得
				final Field field = TestClass.class.getDeclaredField(testCase.name);

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
				//  TestCase(fieldname, check type ...)
				new TestCase("clsArg" ,   StringHolder.class),
				new TestCase("clsList",   StringHolder.class),
				new TestCase("clsMap" ,         String.class, StringHolder.class)
				)
		.forEach(testCase -> {
			try {
				// テスト実施
				System.out.println(testCase.name + " / " + testCase.clazz);
				// テストクラスから検証フィールド取得
				final Field field = TestClass.class.getDeclaredField(testCase.name);

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



	@SuppressWarnings({ "rawtypes", "unchecked", "null" })
	@Test
	public void test_reflected_field_setter() throws Exception {
		// 検証用データ
		final String testData[] = {"AAA", "BBB", "CCC"};

		final TestClass testClass = new TestClass();

		// テストクラスから検証フィールド取得
		final Field field = TestClass.class.getDeclaredField("clsList");
		final LyceeArgCollection lyceeArgCollection = field.getDeclaredAnnotation(LyceeArgCollection.class);

		field.setAccessible(true);

		// パラメータタイプのリストを取得
		final Class<?>[] actualTypes = ClassUtil.getActualTypeArguments(field);
		final Class<?> actualClass = actualTypes[0];

		for(final String data : testData) {
			final Object object = ClassUtil.newInstance(actualClass, data);
			System.out.println("create > " + object + "("+object.getClass()+")");
			final Object trgObj = field.get(testClass);
			Collection col=null;
			if(trgObj==null) {
				col = (Collection) lyceeArgCollection.value().newInstance();
			}else if(trgObj instanceof Collection) {
				col = (Collection)trgObj;
			}else {
				throw new Exception();
			}
			col.add(object);
			field.set(testClass, col);
		}
		final Object list = field.get(testClass);
		System.out.println(list.getClass()+":" + list);
		for (final Object var: (Iterable)list ) {
			System.out.println(var );
		}
	}


}
