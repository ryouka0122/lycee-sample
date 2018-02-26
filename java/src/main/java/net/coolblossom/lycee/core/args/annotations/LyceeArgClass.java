package net.coolblossom.lycee.core.args.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>クラスごと引数クラスとする場合に指定するアノテーション</b>
 * 
 * @author ryouka
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LyceeArgClass {

}
