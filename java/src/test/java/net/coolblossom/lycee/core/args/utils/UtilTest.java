package net.coolblossom.lycee.core.args.utils;

import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * <b>ユーティリティクラスのprivateコンストラクタ用クラス</b>
 * <p>
 * privateコンストラクタのカバレッジ用
 * </p>
 * @author ryouka
 *
 */
@RunWith(Theories.class)
public class UtilTest {

	@DataPoints
	public static Class<?>[] TEST_CLASSES = {
			ClassUtil.class,
			LyceeArgsUtil.class,
			StringUtil.class,
	};

	@Theory
	public void test_private_constructor(final Class<?> clazz) {
		try {
			final Constructor<?> ctor = clazz.getDeclaredConstructor();
			ctor.setAccessible(true);
			ctor.newInstance();
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			fail(e.getMessage());
		}
	}

}
