package net.coolblossom.lycee.core.args;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.mappers.LyceeArgsMapper;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

/**
 * <b>LyceeArgsのマッピング処理</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class LyceeArgs {

	/**
	 *
	 * <b>任意のクラスに引数の値をマッピングさせるメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param clazz 指定クラス
	 * @param args 引数リスト
	 * @return {@link LyceeArgsMapper}
	 */
	@Nonnull
	public static <T> LyceeArgsMapper<T> createAndMap(
			@Nonnull final Class<T> clazz,
			@Nullable final String args[]
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
	 * @return {@link LyceeArgsMapper}
	 */
	@Nonnull
	public static <T> LyceeArgsMapper<T> map(
			@Nonnull final T target,
			@Nullable final String args[]
			) {
		return new LyceeArgsMapper<T>(target, args);
	}
}
