package net.coolblossom.lycee.core.args.convertors;

import javax.annotation.Nonnull;

/**
 * <b>文字列用変換処理クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class StringConvertor extends Convertor {

	/**
	 * コンストラクタ
	 */
	public StringConvertor() {
		super(String.class);
	}

	@Override
	@Nonnull
	public Object convert(@Nonnull final String str) {
		// 文字列から文字列への変換なのでそのまま返す
		return str;
	}

}
