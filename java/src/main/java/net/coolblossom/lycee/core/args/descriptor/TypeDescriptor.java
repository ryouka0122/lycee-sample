package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 *
 * <b>フィールドの情報を管理するクラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class TypeDescriptor extends FieldDescriptor {
	private final Logger logger = Logger.getLogger(TypeDescriptor.class);

	/**
	 * コンストラクタ
	 * @param field
	 * @param convertor
	 */
	public TypeDescriptor(@Nonnull final Field field, @Nonnull final Class<?> type) {
		super(field, type);
	}

	/**
	 *
	 * <b>変換処理</b>
	 * <p>
	 * </p>
	 *
	 * @param str 引数値
	 */
	@Override
	public void set(final Object obj, @Nullable final String str) {
		if(str==null) {
			throw new NullPointerException();
		}
		try {
			logger.info(String.format("field=%s / str=%s", field.getName(), str));
			final Object value = convertor.convert(str);

			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new LyceeRuntimeException(
					String.format("オブジェクトへのマッピングに失敗しました[field=%s/value=%s]", field.getName(), str),
					e);
		}
	}

}
