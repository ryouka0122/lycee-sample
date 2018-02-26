package net.coolblossom.lycee.core.args.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 *
 * <b>クラスに関するユーティリティクラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public final class ClassUtil {
	private ClassUtil() { }


	@SuppressWarnings("null")
	static public boolean isParent(@Nonnull final Class<?> child, @Nonnull final Class<?> parent) {
		if(child.equals(Object.class)) {
			return parent.equals(Object.class);
		}
		if(child.equals(parent)) {
			return true;
		}
		for(final Class<?> ancestor : getAncestors(child)) {
			if(isParent(ancestor, parent)) {
				return true;
			}
		}
		return false;
	}

	@Nonnull
	static public List<Class<?>> getAncestors(@Nonnull final Class<?> clazz) {
		final List<Class<?>> ancestorList = new ArrayList<>();
		if(null!=clazz.getSuperclass()) {
			ancestorList.add(clazz.getSuperclass());
		}
		for(final Class<?> interfaceClass : clazz.getInterfaces()) {
			ancestorList.add(interfaceClass);
		}
		return ancestorList;
	}

}
