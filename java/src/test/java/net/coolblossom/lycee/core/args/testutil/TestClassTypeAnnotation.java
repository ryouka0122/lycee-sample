package net.coolblossom.lycee.core.args.testutil;

import net.coolblossom.lycee.core.args.annotations.LyceeArgClass;

/**
 * <b>クラスに付与するアノテーションのテスト用クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
@LyceeArgClass
public class TestClassTypeAnnotation {

	// ===========================================================
	// public修飾子
	//
	public int argInt;

	public Long argLong;

	public String argStr;

	// ===========================================================
	// protected修飾子
	//
	protected char argChar;

	protected Double argDouble;

}