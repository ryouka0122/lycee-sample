package net.coolblossom.lycee.core.args.enums;

import javax.annotation.Nonnull;

/**
 * <b>Enum用インタフェイス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public interface LyceeCodeEnum {

	/**
	 * <b>コード値の取得</b>
	 * <p>
	 * </p>
	 *
	 * @return コード値
	 */
	@Nonnull
	String getCode();

	/**
	 * <b>表示用文字列の取得</b>
	 * <p>
	 * 出力・表示に使用する文字列を取得するためのメソッドです。<br>
	 * そのため、codeとは別にわかりやすい文字を使用することができます。<br>
	 * </p>
	 *
	 * @return 表示用文字列
	 */
	@Nonnull
	String getDisplay();
}
