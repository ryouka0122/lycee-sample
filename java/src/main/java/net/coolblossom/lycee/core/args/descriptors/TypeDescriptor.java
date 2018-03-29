package net.coolblossom.lycee.core.args.descriptors;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.convertors.Convertor;
import net.coolblossom.lycee.core.args.utils.LyceeArgsUtil;

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

	/** マッピングする際の変換処理クラス */
	@Nonnull
	protected Convertor convertor;

	/**
	 * コンストラクタ
	 * @param field
	 * @param convertor
	 */
	public TypeDescriptor(@Nonnull final Field field, @Nonnull final Class<?> type) {
		super(field);
		convertor = LyceeArgsUtil.createConvertor(type, field.getDeclaredAnnotation(LyceeArg.class));
	}

	@Override
	@Nonnull
	protected Field verifyField(@Nonnull final Field field) {
		return field;
	}

	/**
	 *
	 * <b>変換処理</b>
	 * <p>
	 * </p>
	 *
	 * @param str 引数値
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Override
	public void setValue(@Nonnull final Object obj, @Nonnull final String str)
			throws IllegalArgumentException, IllegalAccessException {
		logger.info(String.format("field=%s / str=%s", field.getName(), str));
		final Object value = convertor.convert(str);

		field.set(obj, value);
	}


}
