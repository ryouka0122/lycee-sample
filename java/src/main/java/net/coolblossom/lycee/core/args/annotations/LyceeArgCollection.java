package net.coolblossom.lycee.core.args.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * <b>Collection/Map系引数で使用できるアノテーション</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface LyceeArgCollection {
	/** 紐づけ時に使用したいクラス */
	Class<?> value() default Object.class;

	/** Collection/Mapのジェネリック型のリスト */
	Class<?>[] types() default {};

	/** [未対応] デフォルト指定項目（使用可能なコレクションは配列型、List、Setのみ） */
	@Deprecated
	boolean isDefault() default false;
}
