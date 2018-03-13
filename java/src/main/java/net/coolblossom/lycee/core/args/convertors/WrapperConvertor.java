package net.coolblossom.lycee.core.args.convertors;

import java.util.function.Function;

import javax.annotation.Nonnull;

/**
 * <b>ラッパークラス用変換処理クラス</b>
 * <p>
 * 現在はCharacter型にのみ使用
 * </p>
 * @author ryouka
 *
 */
public class WrapperConvertor extends Convertor {
	/** 変換処理 */
	@Nonnull
	private final Function<String, Object> parser;

	protected WrapperConvertor(@Nonnull final Class<?> typeClass, @Nonnull final Function<String, Object> parser) {
		super(typeClass);
		this.parser = parser;
	}

	@Nonnull
	@Override
	public Object convert(@Nonnull final String str) {
		return parser.apply(str);
	}

}
