package net.coolblossom.lycee.core.args.convertors;

import java.text.ParseException;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.enums.LyceeDateFormat;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 * <b>Date型の変換処理クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class DateConvertor extends Convertor {

	/** Date型変換用フォーマット */
	@Nonnull
	private final LyceeDateFormat dateFormat;

	/**
	 * コンストラクタ
	 * @param typeClass
	 * @param dateFormat
	 */
	public DateConvertor(@Nonnull final Class<?> typeClass, @Nonnull final LyceeDateFormat dateFormat) {
		super(typeClass);
		this.dateFormat = dateFormat;
	}

	@Override
	@Nonnull
	public Object convert(@Nonnull final String str) {
		try {
			return dateFormat.convert(str);
		} catch (final ParseException e) {
			throw new LyceeRuntimeException("日付のフォーマットが一致しません", e);
		}
	}

}
