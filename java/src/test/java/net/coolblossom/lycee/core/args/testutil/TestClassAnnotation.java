package net.coolblossom.lycee.core.args.testutil;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;

/**
 * <b>アノテーション用テストクラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class TestClassAnnotation {

	/** 指定なし */
	@LyceeArg
	protected String argStr1;

	/** name指定あり（フィールド名と違う） */
	@LyceeArg(name="arg2")
	protected String argStr2;

	/** name指定あり（フィールド名と同じ） */
	@LyceeArg(name="argStr3")
	protected String argStr3;

	/** alias指定あり（フィールド名と違う） */
	@LyceeArg(alias="arg4")
	protected String argStr4;

	/** alias指定あり（フィールド名と同じ） */
	@LyceeArg(alias="argStr5")
	protected String argStr5;

	/** nameとalias指定あり（name＝フィールド名と違う / alias＝フィールド名と違う） */
	@LyceeArg(name="arg6", alias="param1")
	protected String argStr6;

	/** nameとalias指定あり（name＝フィールド名と同じ / alias＝フィールド名と違う） */
	@LyceeArg(name="argStr7", alias="param2")
	protected String argStr7;

	/** nameとalias指定あり（name＝フィールド名と違う / alias＝フィールド名と同じ） */
	@LyceeArg(name="arg8", alias="argStr8")
	protected String argStr8;

	/** nameとalias指定あり（name＝フィールド名と同じ / alias＝フィールド名と同じ） */
	@LyceeArg(name="argStr9", alias="argStr9")
	protected String argStr9;

}
