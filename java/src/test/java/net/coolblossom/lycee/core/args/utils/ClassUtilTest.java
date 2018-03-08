package net.coolblossom.lycee.core.args.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

public class ClassUtilTest {

	static class TestClass {
		public Integer intArg;
		public String strArg;
		public long longArg;
		public List<String> strList;
		public List<Integer> intList;
		public Map<String, String> strMap;
		public Map<String, Integer> intMap;
	}


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
				new TestCase("strMap" ,  String.class, String.class),
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

}
