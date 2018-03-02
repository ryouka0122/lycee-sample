package net.coolblossom.lycee.core.args;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.junit.Test;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.annotations.LyceeArgClass;
import net.coolblossom.lycee.core.args.enums.LyceeDateFormat;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

public class LyceeArgsProfileTest {
	/**
	 * <b>SampleEnum</b>
	 * <p>
	 * 確認用列挙型
	 * </p>
	 * @author ryouka
	 *
	 */
	enum SampleEnum {
		TEST1,
		TEST2,
		TEST3,
		TEST4
		;
	}


	/**
	 * 型の確認用テストクラス
	 * @author ryouka
	 *
	 */
	public static class TypeTestClass {

		/** 文字列型 */
		@LyceeArg
		protected String argString;

		/** 小数型 */
		@LyceeArg
		protected double argDouble;

		/** 整数型 */
		@LyceeArg
		protected long argLong;

		/** 日付型 */
		@LyceeArg(dateFormat=LyceeDateFormat.SLASH_YYYY_MM_DD)
		protected Date argDate;

		/** 列挙型 */
		@LyceeArg
		protected SampleEnum argEnum;
	}
	/**
	 * TypeTestClassのテストデータ
	 */
	private static final @Nonnull String[][] NORMAL_TEST_DATA = {
			// 全部あり
			{"--argString", "abc", "--argDouble", "3.14", "--argLong", "100", "--argDate", "2018/01/01", "--argEnum", "TEST3"}
			// 最後なし
			, {"--argString", "abc", "--argDouble", "3.14", "--argLong", "100", "--argDate", "2018/01/01"}
			// 先頭なし
			, {                      "--argDouble", "3.14", "--argLong", "100", "--argDate", "2018/01/01", "--argEnum", "TEST3"}
			// 途中なし
			, {"--argString", "abc",                        "--argLong", "100", "--argDate", "2018/01/01", "--argEnum", "TEST3"}
			// 1つだけ
			, {"--argString", "abc" }
	};

	/**
	 * アクセッサの確認用テストクラス
	 * @author ryouka
	 *
	 */
	public static class AccessTestClass {
		/** 自動設定可能フィールド */
		@LyceeArg
		public String arg101;

		/** 引数arg110指定 */
		@LyceeArg(name="arg110")
		protected String arg102;

		/** アクセスできないフィールド */
		@LyceeArg
		private String invalidField;
	}

	/**
	 * AccessTestClassのテストデータ
	 */
	private static final @Nonnull String[][] ACCESSOR_TEST_DATA = {
			// 正常系
			{"--arg101", "abc", "--arg110", "xyz",},
			// 名前指定する前のパターン
			{"--arg101", "abc", "--arg102", "xyz",},
			// 名前指定で2重に記述
			{"--arg101", "abc", "--arg110", "xyz1", "--arg110", "xyz2",},
			// 名前指定前で2重に記述
			{"--arg101", "abc", "--arg102", "xyz1", "--arg102", "xyz2",},
			// 名前指定前後を2重に記述（順番：指定後->指定前）
			{"--arg101", "abc", "--arg110", "xyz1", "--arg102", "xyz2",},
			// 名前指定前後を2重に記述（順番：指定前->指定後）
			{"--arg101", "abc", "--arg102", "xyz1", "--arg110", "xyz2",},
	};

	/**
	 * クラスアノテーション用テストクラス
	 * @author ryouka
	 *
	 */
	@LyceeArgClass
	public static class AutoBindTestClass {
		/** 文字列型 */
		protected String arg001;

		/** 文字列型 */
		@LyceeArg(name="arg100")
		protected String arg010;

		/** 小数型 */
		protected double arg002;

		/** 整数型 */
		protected long arg003;

		/** 整数型(public) */
		public long arg004;

		/** 整数型(private) */
		private long arg005;
	}

	/**
	 * AutoBindTestClassのテストデータ
	 */
	private static final @Nonnull String[][] AUTOBIND_TEST_DATA = {
			{"--arg001", "abc", "--arg002", "3.14", "--arg003", "100", "--arg004", "200"},
			{"--arg001", "abc"                    , "--arg003", "100"                   },
			{"--arg001", "abc", "--arg002", "3.14", "--arg003", "100"                   , "--arg100", "xyz"},
	};

	/**
	 * <b>test_normal</b>
	 * <p>
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	@SuppressWarnings("null")
	public void test_normal() throws Exception {
		for(final @Nonnull String[] data : NORMAL_TEST_DATA) {
			final LyceeArgsProfile profile = new LyceeArgsProfile();
			final TypeTestClass object = profile.createAndBind(TypeTestClass.class, data);

			System.out.println("==============================================");
			printClassInfo(TypeTestClass.class, object);
		}
	}

	/**
	 * <b>test_accessor</b>
	 * <p>
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	@SuppressWarnings("null")
	public void test_accessor() throws Exception {
		for(final @Nonnull String[] data : ACCESSOR_TEST_DATA) {
			final LyceeArgsProfile profile = new LyceeArgsProfile();
			final AccessTestClass object = profile.createAndBind(AccessTestClass.class, data);

			System.out.println("==============================================");
			printClassInfo(AccessTestClass.class, object);
		}
	}

	/**
	 * <b>test_autobind</b>
	 * <p>
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	@SuppressWarnings("null")
	public void test_autobind() throws Exception {
		for(final @Nonnull String[] data : AUTOBIND_TEST_DATA) {
			final LyceeArgsProfile profile = new LyceeArgsProfile();
			final AutoBindTestClass object = profile.createAndBind(AutoBindTestClass.class, data);

			System.out.println("==============================================");
			printClassInfo(AutoBindTestClass.class, object);
		}
	}

	@Test
	public void test_pattern() {
		final String regex = "--?([A-Za-z_][A-Za-z0-9_]*)";
		final Pattern ptn = Pattern.compile(regex);
		Stream.of(
				// OK
				"--arg", "-a", "--arg_001", "--arg001",
				// NG
				"ng", "---ng", "-ng-ng")
		.forEach(arg -> {
			System.out.println("==============================================");
			System.out.println("target: " + arg);
			ptn.splitAsStream(arg)
			.forEach(System.out::println);
			final boolean bMatches = Pattern.matches(regex, arg);
			System.out.println("match: " + bMatches);
		});

	}

	public static class ArrayTestClass {
		@LyceeArg
		String argString;
		@LyceeArg
		String argAry[];
	}

	@Test
	public void test_array() {
		final String args[] = {
				"--argString", "abc"
				, "--argAry", "1"
				, "--argAry", "2"
				, "--argAry", "3"
				, "--argAry", "4"
				, "--argAry", "5"
		};

		final ArrayTestClass object = new LyceeArgsProfile().createAndBind(ArrayTestClass.class, args);

		System.out.println(object.argString);
		System.out.println(Stream.of(object.argAry).collect(Collectors.joining(",")));

	}









	// ==================================================================
	// 検証用テストクラス
	//

	//	@Test
	public void verify_collection() {
		final List<String> list = new ArrayList<>();
		final Set<String> set = new HashSet<>();
		final Map<String, String> map = new HashMap<>();

		Stream.of(list, set, map)
		.map(c -> c.getClass())
		.forEach( clazz -> {
			System.out.println("===============================================================");
			System.out.println("*" + clazz);
			System.out.println(" - getName : " + clazz.getName());
			System.out.println(" - getSimpleName : " + clazz.getSimpleName());
			System.out.println(" - getCanonicalName : " + clazz.getCanonicalName());
			System.out.println(" - getModifiers : " + clazz.getModifiers());
			System.out.println(" - getTypeName : " + clazz.getTypeName());
			System.out.println(" - getComponentType : " + clazz.getComponentType());
			System.out.println(" - getGenericSuperclass : " + clazz.getGenericSuperclass());
			System.out.println(" - getTypeParameters : " + Stream.of(clazz.getTypeParameters()).map(t->t.getName()).collect(Collectors.joining(",")));

			System.out.println(" - ArrayList : " + ClassUtil.isParent(clazz, ArrayList.class));
			System.out.println(" - HashSet : " + ClassUtil.isParent(clazz, HashSet.class));
			System.out.println(" - HashMap : " + ClassUtil.isParent(clazz, Map.class));
			System.out.println(" - List : " + ClassUtil.isParent(clazz, List.class));
			System.out.println(" - Set : " + ClassUtil.isParent(clazz, Set.class));
			System.out.println(" - Map : " + ClassUtil.isParent(clazz, Map.class));
			System.out.println(" - Collection : " + ClassUtil.isParent(clazz, Collection.class));
			System.out.println(" - Iterable : " + ClassUtil.isParent(clazz, Iterable.class));
			System.out.println(" - Object : " + ClassUtil.isParent(clazz, Object.class));
		});

	}

	static class SampleClass {
		@LyceeArg
		protected SampleEnum sample;
	}

	//@Test
	public void verify_test4() throws Exception {
		final LyceeArgsProfile profile = new LyceeArgsProfile();
		final SampleClass sample = profile.createAndBind(SampleClass.class, new String[] {"--sample", "TEST5"});

		System.out.println("sample: "+sample.sample);
	}

	@Test
	public void verify_test3() {
		final int[] iAry = {1,2,3};
		final String[] sAry = {"a", "B", "C"};

		final List<Integer> aryInt = new ArrayList<>();
		final Set<Integer> setInt = new HashSet<>();
		final Map<Integer, String> mapIS = new HashMap<>();

		checkClass("iAry", iAry);
		checkClass("sAry", sAry);
		checkClass("aryInt", aryInt);
		checkClass("setInt", setInt);
		checkClass("mapIS", mapIS);
		;
	}


	// =======================================================================
	//
	// 下請けメソッド
	//

	/**
	 * <b>クラスの種類を確認するメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param name
	 * @param target
	 */
	private void checkClass(final String name, final Object target) {
		final Class<?> clazz = target.getClass();
		System.out.println("======================================================");
		System.out.println("name: " + name);
		System.out.println("type: " + clazz);
		System.out.println("isArray: " + clazz.isArray());
		System.out.println("isEnum: " + clazz.isEnum());
		System.out.println("isCollection: " + (target instanceof Collection<?>));
		System.out.println("superclass: " + clazz.getSuperclass());
		if(clazz.isArray()) {
			System.out.println("Component Type: " + clazz.getComponentType());
		}else if(target instanceof Collection<?>) {
			System.out.println("Collection Type: " + clazz.toGenericString());
		}else if(target instanceof Map<?, ?>) {
			System.out.println("Map Type: " +
					Arrays.stream(clazz.getTypeParameters())
			.map(t->t.getName())
			.collect(Collectors.joining(",", "[", "]")));
		}
	}

	/**
	 * 確認用フィールド出力メソッド
	 * @param clazz
	 * @param object
	 */
	private <T> void printClassInfo(final Class<T> clazz, final T object) {
		Stream.of(clazz.getDeclaredFields())
		.forEach(field-> {
			field.setAccessible(true);
			try {
				System.out.println(String.format(
						"[%9s] %-10s = %s"
						, getAccessibleName(field.getModifiers())
						, field.getName()
						, field.get(object)
						) );
			} catch (final Exception e) {
				;
			}
		});
	}

	/**
	 * アクセス修飾子名に変換するメソッド
	 * @param mod
	 * @return
	 */
	private String getAccessibleName(final int mod) {
		if(Modifier.isPrivate(mod)) {
			return "private";
		}else if(Modifier.isProtected(mod)) {
			return "protected";
		}else if(Modifier.isPublic(mod)) {
			return "public";
		}else {
			return "internal";
		}
	}

}
