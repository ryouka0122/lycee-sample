package net.coolblossom.lycee.core.args.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.coolblossom.lycee.core.args.enums.LyceeDateFormat;

/**
 * <b>引数紐づけさせるためのアノテーション</b>
 *
 * @author ryouka
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LyceeArg {

	/** 明示的に紐づけしたい場合に使用（指定した場合、こちらの名称で紐づけ処理が行われる） */
	String name() default "";

	/** フォーマット（主に日付型で使用される想定） */
	LyceeDateFormat dateFormat() default LyceeDateFormat.COMPACT_YYYY_MM_DD;

	/** [未対応] デフォルト指定項目（使用可能なコレクションは配列型、List、Setのみ） */
	@Deprecated
	boolean isDefault() default false;

	/** [未対応] 短縮名やエイリアス使用時に指定（一応、予約項目） */
	@Deprecated
	String alias() default "";

}
