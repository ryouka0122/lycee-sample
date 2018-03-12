package net.coolblossom.lycee.core.args.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 *
 * <b>クラスに関するユーティリティクラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public final class ClassUtil {
	private ClassUtil() { }

	@Nonnull
	public static <T> T newInstance(@Nonnull final Class<T> clazz) {
		try {
			final T obj = clazz.newInstance();
			if(obj==null) {
				throw new NullPointerException();
			}
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new LyceeRuntimeException("インスタンス生成に失敗しました", e);
		}
	}

	@Nonnull
	public static <T> T newInstance(@Nonnull final Class<T> clazz, final Object ...args) {
		Constructor<T> ctor = null;
		try {
			final Class<?>[] ctorArgs = Stream.of(args)
					.map(arg -> arg.getClass())
					.toArray(Class<?>[]::new);
			ctor = clazz.getConstructor(ctorArgs);
		}catch(final NoSuchMethodException e) {
			throw new LyceeRuntimeException("該当するコンストラクタがありませんでした", e);
		} catch (final SecurityException e) {
			throw new LyceeRuntimeException("コンストラクタの取得に失敗しました", e);
		}
		try {
			final T obj = ctor.newInstance(args);
			if(obj==null) {
				throw new NullPointerException();
			}
			return obj;
		} catch (InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			throw new LyceeRuntimeException("インスタンス生成に失敗しました", e);
		}
	}


	@SuppressWarnings("null")
	public static boolean isParent(@Nonnull final Class<?> child, @Nonnull final Class<?> parent) {
		if(child.equals(Object.class)) {
			return parent.equals(Object.class);
		}
		if(child.equals(parent)) {
			return true;
		}
		for(final Class<?> ancestor : getAncestors(child)) {
			if(isParent(ancestor, parent)) {
				return true;
			}
		}
		return false;
	}

	@Nonnull
	public static List<Class<?>> getAncestors(@Nonnull final Class<?> clazz) {
		final List<Class<?>> ancestorList = new ArrayList<>();
		if(null!=clazz.getSuperclass()) {
			ancestorList.add(clazz.getSuperclass());
		}
		for(final Class<?> interfaceClass : clazz.getInterfaces()) {
			ancestorList.add(interfaceClass);
		}
		return ancestorList;
	}


	@Nonnull
	public static Class<?>[] getActualTypeArguments(@Nonnull final Field field) {
		final Type fieldGenericType = field.getGenericType();

		if( fieldGenericType instanceof Class) {
			return new Class<?>[] { (Class<?>)fieldGenericType };
		}
		return Stream.of(((ParameterizedType)fieldGenericType).getActualTypeArguments())
				.filter(t-> t instanceof Class)
				.toArray(Class<?>[]::new);
	}

	/**
	 * <b>実装クラスか確認するメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param clazz
	 * @return
	 */
	public static boolean isImplementationClass(@Nonnull final Class<?> clazz) {
		return clazz.isInterface()
				|| Modifier.isAbstract(clazz.getModifiers());
	}
}
