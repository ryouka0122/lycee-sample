package net.coolblossom.lycee.core.args.mappers;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.utils.ClassUtil;

/**
 *
 * <b></b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class LyceeArgsMapper {

	/**
	 *
	 * <b>任意のクラスに引数の値をマッピングさせるメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param clazz 指定クラス
	 * @param args 引数リスト
	 * @return {@link LyceeBinder}
	 */
	@Nonnull
	public static <T> LyceeBinder<T> createAndMap(
			@Nonnull final Class<T> clazz,
			@Nonnull final String args[]
			) {
		return map(ClassUtil.newInstance(clazz), args);
	}

	/**
	 * <b>任意のオブジェクトに引数の値をマッピングさせるメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param target 対象オブジェクト
	 * @param args 引数リスト
	 * @return {@link LyceeBinder}
	 */
	@Nonnull
	public static <T> LyceeBinder<T> map(
			@Nonnull final T target,
			@Nonnull final String args[]
			) {
		return new LyceeBinder<T>(target, args);
	}
}
