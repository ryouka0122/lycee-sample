package net.coolblossom.lycee.core.args.enums;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nonnull;

/**
 * 日付変換用フォーマット列挙型
 * @category [DATE]
 * @author ryouka
 *
 */
public enum LyceeDateFormat {
	COMPACT_YYYY_MM_DD("yyyyMMdd")
	, COMPACT_YYYY_MM_DD__HH_MM_SS("yyyyMMddHHmmss")
	, SLASH_YYYY_MM_DD("yyyy/MM/dd")
	, SLASH_YYYY_MM_DD__HH_MM_SS("yyyy/MM/dd HH:mm:ss")
	, DASH_YYYY_MM_DD("yyyy-MM-dd")
	, DASH_YYYY_MM_DD__HH_MM_SS("yyyy-MM-dd HH:mm:ss")
	, JAP_YYYY_MM_DD("yyyy年MM月dd日")
	, JAP_YYYY_MM_DD__HH_MM_SS("yyyy年MM月dd日 HH時mm分ss秒")
	;

	@Nonnull
	private String format;

	private LyceeDateFormat(@Nonnull final String format) {
		this.format = format;
	}

	/**
	 * フォーマット情報の取得
	 * @return フォーマット情報
	 */
	@Nonnull
	public String getFormat() {
		return format;
	}

	/**
	 * フォーマット情報に従い変換
	 * @param date 変換したい文字列
	 * @return 変換結果
	 * @throws ParseException 変換処理で異常が発生した場合
	 */
	@Nonnull
	public Date convert(@Nonnull final String date) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat(format);
		final Date result = sdf.parse(date);
		if(result==null) {
			throw new NullPointerException();
		}
		return result;
	}

	@Override
	public String toString() {
		return format;
	}
}
