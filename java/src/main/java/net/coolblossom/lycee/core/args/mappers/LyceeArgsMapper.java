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
	 * @return {@link LyceeArgsMapExecutor}
	 */
	@Nonnull
	public static <T> LyceeArgsMapExecutor<T> createAndMap(
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
	 * @return {@link LyceeArgsMapExecutor}
	 */
	@Nonnull
	public static <T> LyceeArgsMapExecutor<T> map(
			@Nonnull final T target,
			@Nonnull final String args[]
			) {
		return new LyceeArgsMapExecutor<T>(target, args);
	}
}
