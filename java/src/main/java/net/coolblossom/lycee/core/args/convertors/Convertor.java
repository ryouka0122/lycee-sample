package net.coolblossom.lycee.core.args.convertors;

import javax.annotation.Nonnull;

/**
 *
 * <b>変換処理の抽象クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public abstract class Convertor {

	@Nonnull
	protected Class<?> typeClass;

	protected Convertor(@Nonnull final Class<?> typeClass) {
		this.typeClass = verifyType(typeClass);
	}

	/**
	 * <b>型の検証</b>
	 * <p>
	 * </p>
	 *
	 * @param typeClass
	 * @return
	 */
	@Nonnull
	protected Class<?> verifyType(@Nonnull final Class<?> typeClass) {
		return typeClass;
	}


	/**
	 * <b>変換処理</b>
	 * <p>
	 * </p>
	 *
	 * @param str 変換対象文字列
	 * @return 変換後のオブジェクト
	 */
	@Nonnull
	public abstract Object convert(@Nonnull String str);
}
