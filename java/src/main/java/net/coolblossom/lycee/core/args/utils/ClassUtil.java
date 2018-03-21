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

	/**
	 * <b>インスタンス生成</b>
	 * <p>
	 * インスタンスを生成します。<br>
	 * 指定した引数の型で定義されているコンストラクタが存在しない場合エラーとなります。<br>
	 * </p>
	 *
	 * @param clazz 生成したいインスタンスの型
	 * @param args コンストラクタの引数
	 * @return 生成したインスタンス
	 */
	@Nonnull
	@SuppressWarnings("null")
	public static <T> T newInstance(@Nonnull final Class<T> clazz, final Object ...args) {
		Constructor<T> ctor = null;
		try {
			Class<?>[] ctorArgs = null;
			if(args!=null && args.length>0) {
				ctorArgs = Stream.of(args)
						.map(arg -> arg.getClass())
						.toArray(Class<?>[]::new);
			}
			ctor = clazz.getDeclaredConstructor(ctorArgs);
		}catch(final NoSuchMethodException e) {
			throw new LyceeRuntimeException("該当するコンストラクタがありませんでした", e);
		} catch (final SecurityException e) {
			throw new LyceeRuntimeException("コンストラクタの取得に失敗しました", e);
		}
		try {
			final T obj = ctor.newInstance(args);
			return obj;
		} catch (InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			throw new LyceeRuntimeException("インスタンス生成に失敗しました", e);
		}
	}

	/**
	 * <b>2つのクラスが親子関係かをチェックするメソッド</b>
	 * <p>
	 * 子クラスからスーパークラスの方向に探索していきます。<br>
	 * 探索している途中で、指定されている親クラスを見つけるとTRUEを返すようとなります。<br>
	 * Objectまで探索しても見つからない場合は、親子関係ではないと判断され、FALSEを返します。<br>
	 * 例１： ArrayListとListは親子関係である
	 * 例２： ArrayListとMapは親子関係ではない
	 * </p>
	 *
	 * @param child 子クラスとなっている型クラス
	 * @param parent 親クラスとなっている型クラス
	 * @return
	 */
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

	/**
	 * <b>継承元クラスのリストを取得するメソッド</b>
	 * <p>
	 * 指定された型クラスの親クラスとインタフェイスのリストを列挙したListを返します。<br>
	 * 親がObjectのみ（POJOなど）の場合、Object.classだけのリストが返されます。<br>
	 * </p>
	 *
	 * @param clazz 取得したい型クラス
	 * @return 継承元クラスのリスト
	 */
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

	/**
	 * <b>フィールドの実クラスの型を取得するメソッド</b>
	 * <p>
	 * ListやMapなどのジェネリック化されたフィールドで指定されている型を取得する際に使用するメソッドです。<br>
	 * 通常のフィールドであれば、そのクラスの型で返します。<br>
	 * </p>
	 *
	 * @param field 実クラスを取得したいフィールド
	 * @return 実クラスの配列
	 */
	@Nonnull
	public static Class<?>[] getActualTypeArguments(@Nonnull final Field field) {
		final Type fieldGenericType = field.getGenericType();

		if( fieldGenericType instanceof Class) {
			return new Class<?>[] { (Class<?>)fieldGenericType };
		}
		return Stream.of(fieldGenericType)
				.filter(type-> type instanceof ParameterizedType)
				.flatMap(type -> {
					return Stream.of( ((ParameterizedType)type).getActualTypeArguments() );
				})
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
		return !(clazz.isInterface()
				|| Modifier.isAbstract(clazz.getModifiers()));
	}
}
