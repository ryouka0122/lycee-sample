package net.coolblossom.lycee.core.args.descriptors;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 * <b>配列用記述子</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class ArrayDescriptor extends FieldDescriptor {

	/**
	 * コンストラクタ
	 * @param field
	 * @param componentType
	 */
	public ArrayDescriptor(@Nonnull final Field field, @Nonnull final Class<?> componentType) {
		super(field, componentType);
	}

	/**
	 * <b>フィールドの検証</b>
	 * <p>
	 * </p>
	 *
	 * @param field
	 * @return
	 */
	@Override
	@Nonnull
	protected Field verifyField(@Nonnull final Field field) {
		if(!field.getType().isArray()) {
			throw new LyceeRuntimeException(
					String.format("配列型のフィールドではありません[field=%s]",field.getName()));
		}
		return field;
	}

	@Override
	public void setValue(@Nonnull final Object obj, @Nonnull final String value)
			throws IllegalArgumentException, IllegalAccessException {
		// オブジェクトからフィールドにあるデータを取得
		final Object oldArray = field.get(obj);
		// 取得時点の配列の長さ
		final int beforeLength = (oldArray!=null) ? Array.getLength(oldArray) : 0;

		// 新規配列の生成（長さは取得時点の配列の長さ+１）
		final Object newArray = Array.newInstance(field.getType().getComponentType(), beforeLength+1);
		// 前まであったデータを移行
		for(int i=0 ; i<beforeLength ; i++) {
			Array.set(newArray, i, Array.get(oldArray, i));;
		}
		// 新規分追加
		Array.set(newArray, beforeLength, convertor.convert(value));

		// オブジェクトにセット
		field.set(obj, newArray);
	}

}
