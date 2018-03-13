package net.coolblossom.lycee.core.args.testutil;

import java.util.Date;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.enums.LyceeDateFormat;

/**
 * <b>すべてのテストパターンを含むテスト用クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class TestClassSimpleCase {

	// ------------------------------------------------------------
	// LｙceeArgなし
	//
	protected int argIgnore = 9999;

	// ------------------------------------------------------------
	// プリミティブ型
	//
	/** プリミティブ型 / 対応Convertor：PrimitiveConvertor */
	@LyceeArg
	protected char argChar;

	/** プリミティブ型 / 対応Convertor：PrimitiveConvertor */
	@LyceeArg
	protected boolean argBool;

	/** プリミティブ型 / 対応Convertor：PrimitiveConvertor */
	@LyceeArg
	protected byte argByte;

	/** プリミティブ型 / 対応Convertor：PrimitiveConvertor */
	@LyceeArg
	protected short argShort;

	/** プリミティブ型 / 対応Convertor：PrimitiveConvertor */
	@LyceeArg
	protected int argInt;

	/** プリミティブ型 / 対応Convertor：PrimitiveConvertor */
	@LyceeArg
	protected long argLong;

	/** プリミティブ型 / 対応Convertor：PrimitiveConvertor */
	@LyceeArg
	protected float argFloat;

	/** プリミティブ型 / 対応Convertor：PrimitiveConvertor */
	@LyceeArg
	protected double argDouble;

	// ------------------------------------------------------------
	// ラッパー型
	//

	/** ラッパー型（Character） / 対応Convertor：WrapperConvertor */
	@LyceeArg
	protected Character argCharWrap;

	/** ラッパー型（Character以外） / 対応Convertor：DefaultConvertor */
	@LyceeArg
	protected Long argLongWrap;

	/** 文字列型 / 対応Convertor：StringConvertor */
	@LyceeArg
	protected String argStr;

	// ------------------------------------------------------------
	// 日付型
	//

	/** 日付型（フォーマット指定なし） / 対応Convertor：DateConvertor */
	@LyceeArg
	protected Date argDateDefault;

	/** 日付型（フォーマット指定あり） / 対応Convertor：DateConvertor */
	@LyceeArg(dateFormat=LyceeDateFormat.DASH_YYYY_MM_DD)
	protected Date argDateDashYmd;

	// ------------------------------------------------------------
	// 列挙型
	//

	/** 列挙型（通常） / 対応Convertor：EnumConvertor */
	@LyceeArg
	protected TestEnum argEnum;

	/** 列挙型（LyceeCodeEnum） / 対応Convertor：CodeEnumConvertor */
	@LyceeArg
	protected TestCodeEnum argCodeEnum;

}
