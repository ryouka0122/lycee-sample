package net.coolblossom.lycee.core.args.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 文字列に関するユーティリティクラス
 *
 * @category [ユーティリティ]
 * @author ryouka
 *
 */
public final class StringUtil {
	private StringUtil() { }

	/**
	 * 空文字判定メソッド
	 * @param arg　判定したいStringの変数
	 * @return NULLか空文字の場合、trueを返す
	 */
	public static boolean isEmpty(@Nullable final String arg) {
		return arg==null || arg.isEmpty();
	}

	/**
	 *
	 * <b>文字列の等価処理</b>
	 * <p>
	 * nullの時、それぞれがnullでないとtrueにならない。
	 * </p>
	 *
	 * @param left 1つ目の文字列
	 * @param right 2つ目の文字列
	 * @return {@link String#equals(Object)}
	 */
	public static boolean equals(@Nullable final String left, @Nullable final String right) {
		return (left==null) ? right==null : left.equals(right);
	}

	/**
	 *
	 * <b>非NULL化メソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param value 検証インスタンス
	 * @param defaultStr valueがNULLの時の値
	 * @return 非NULL {@link String}
	 */
	@Nonnull
	public static String toString(@Nullable final String value, @Nonnull final String defaultStr) {
		return (value!=null?value:defaultStr);
	}

}
