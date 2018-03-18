package net.coolblossom.lycee.core.args.utils;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * <b>オブジェクトに関するユーティリティクラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public final class ObjectUtil {
	private ObjectUtil() { }

	/**
	 *
	 * <b>オブジェクトNULL時のデフォルト処理</b>
	 * <p>
	 * 主に@Nonnull回避用に使います
	 * </p>
	 *
	 * @param object 判定オブジェクト
	 * @param supplier null時のlambda処理
	 * @return 非NULLオブジェクト
	 */
	@Nonnull
	public static <T> T orNullDefault(@Nullable final T object, @Nonnull final Supplier<T> supplier) {
		if(object!=null) {
			return object;
		}
		final T retValue = supplier.get();
		if(retValue==null) {
			throw new NullPointerException();
		}
		return retValue;
	}


}
