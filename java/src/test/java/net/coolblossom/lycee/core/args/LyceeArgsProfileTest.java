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
	 * �m�F�p�񋓌^
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
	 * �^�̊m�F�p�e�X�g�N���X
	 * @author ryouka
	 *
	 */
	public static class TypeTestClass {

		/** ������^ */
		@LyceeArg
		protected String argString;

		/** �����^ */
		@LyceeArg
		protected double argDouble;

		/** �����^ */
		@LyceeArg
		protected long argLong;

		/** ���t�^ */
		@LyceeArg(dateFormat=LyceeDateFormat.SLASH_YYYY_MM_DD)
		protected Date argDate;

		/** �񋓌^ */
		@LyceeArg
		protected SampleEnum argEnum;
	}
	/**
	 * TypeTestClass�̃e�X�g�f�[�^
	 */
	private static final @Nonnull String[][] NORMAL_TEST_DATA = {
			// �S������
			{"--argString", "abc", "--argDouble", "3.14", "--argLong", "100", "--argDate", "2018/01/01", "--argEnum", "TEST3"}
			// �Ō�Ȃ�
			, {"--argString", "abc", "--argDouble", "3.14", "--argLong", "100", "--argDate", "2018/01/01"}
			// �擪�Ȃ�
			, {                      "--argDouble", "3.14", "--argLong", "100", "--argDate", "2018/01/01", "--argEnum", "TEST3"}
			// �r���Ȃ�
			, {"--argString", "abc",                        "--argLong", "100", "--argDate", "2018/01/01", "--argEnum", "TEST3"}
			// 1����
			, {"--argString", "abc" }
	};

	/**
	 * �A�N�Z�b�T�̊m�F�p�e�X�g�N���X
	 * @author ryouka
	 *
	 */
	public static class AccessTestClass {
		/** �����ݒ�\�t�B�[���h */
		@LyceeArg
		public String arg101;

		/** ����arg110�w�� */
		@LyceeArg(name="arg110")
		protected String arg102;

		/** �A�N�Z�X�ł��Ȃ��t�B�[���h */
		@LyceeArg
		private String invalidField;
	}

	/**
	 * AccessTestClass�̃e�X�g�f�[�^
	 */
	private static final @Nonnull String[][] ACCESSOR_TEST_DATA = {
			// ����n
			{"--arg101", "abc", "--arg110", "xyz",},
			// ���O�w�肷��O�̃p�^�[��
			{"--arg101", "abc", "--arg102", "xyz",},
			// ���O�w���2�d�ɋL�q
			{"--arg101", "abc", "--arg110", "xyz1", "--arg110", "xyz2",},
			// ���O�w��O��2�d�ɋL�q
			{"--arg101", "abc", "--arg102", "xyz1", "--arg102", "xyz2",},
			// ���O�w��O���2�d�ɋL�q�i���ԁF�w���->�w��O�j
			{"--arg101", "abc", "--arg110", "xyz1", "--arg102", "xyz2",},
			// ���O�w��O���2�d�ɋL�q�i���ԁF�w��O->�w���j
			{"--arg101", "abc", "--arg102", "xyz1", "--arg110", "xyz2",},
	};

	/**
	 * �N���X�A�m�e�[�V�����p�e�X�g�N���X
	 * @author ryouka
	 *
	 */
	@LyceeArgClass
	public static class AutoBindTestClass {
		/** ������^ */
		protected String arg001;

		/** ������^ */
		@LyceeArg(name="arg100")
		protected String arg010;

		/** �����^ */
		protected double arg002;

		/** �����^ */
		protected long arg003;

		/** �����^(public) */
		protected long arg004;

		/** �����^(private) */
		protected long arg005;
	}

	/**
	 * AccessTestClass�̃e�X�g�f�[�^
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
	public void test_autobind() throws Exception {
		for(final @Nonnull String[] data : AUTOBIND_TEST_DATA) {
			final LyceeArgsProfile profile = new LyceeArgsProfile();
			final AutoBindTestClass object = profile.createAndBind(AutoBindTestClass.class, data);

			System.out.println("==============================================");
			printClassInfo(AutoBindTestClass.class, object);
		}
	}


	// ==================================================================
	// ���ؗp�e�X�g�N���X
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

	//@Test
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
	// ���������\�b�h
	//

	/**
	 * <b>�N���X�̎�ނ��m�F���郁�\�b�h</b>
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
	 * �m�F�p�t�B�[���h�o�̓��\�b�h
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
	 * �A�N�Z�X�C���q���ɕϊ����郁�\�b�h
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
