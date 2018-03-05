package net.coolblossom.lycee.core.args.old_descriptors;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 *
 * <b>任意クラス用ディスクリプタ</b>
 * <p>
 * 指定できるクラスには、String型を引数にしたコンストラクタが条件です。
 * </p>
 * @author ryouka
 *
 */
public class ClassObjectDescriptor extends AbstractFieldDescriptor {

	public ClassObjectDescriptor(@Nonnull final Field field) {
		super(field);
	}

	@Override
	public void bind(@Nonnull final Object target, @Nullable final String value) {
		field.setAccessible(true);
		try {
			field.set(target, makeObject(value));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new LyceeRuntimeException(
					String.format("%sに%sを設定することができませんでした。", field.getName(), value)
					, e);
		}
	}

	/**
	 *
	 * <b>makeObject</b>
	 * <p>
	 * </p>
	 *
	 * @param value
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private Object makeObject(final String value) throws IllegalAccessException, IllegalArgumentException {
		final Class<?> fieldType = field.getType();
		try {
			return fieldType.getConstructor(String.class).newInstance(value);
		}catch(final NoSuchMethodException e) {
			throw new LyceeRuntimeException(fieldType.getName()+"にはString型を引数にしたコンストラクタが定義されていません。", e);
		} catch (final InstantiationException | InvocationTargetException e) {
			throw new LyceeRuntimeException("コンストラクタを利用したインスタンス生成時にエラーが発生しました。", e);
		}
	}

}
