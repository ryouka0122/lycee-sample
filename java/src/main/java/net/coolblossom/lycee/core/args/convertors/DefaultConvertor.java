package net.coolblossom.lycee.core.args.convertors;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.utils.ClassUtil;

/**
 * <b>定義されていないクラスの変換処理クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class DefaultConvertor extends Convertor {

	/**
	 * コンストラクタ
	 * @param typeClass
	 */
	public DefaultConvertor(@Nonnull final Class<?> typeClass) {
		super(typeClass);
	}

	@Override
	@Nonnull
	public Object convert(@Nonnull final String str) {
		return ClassUtil.newInstance(typeClass, str);
	}

}
