package net.coolblossom.lycee.core.args.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

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
	@Nonnull
	LyceeDateFormat dateFormat() default LyceeDateFormat.COMPACT_YYYY_MM_DD;

	/** 短縮名やエイリアス使用時に指定 */
	String alias() default "";

}
