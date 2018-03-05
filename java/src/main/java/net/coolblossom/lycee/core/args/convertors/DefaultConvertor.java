package net.coolblossom.lycee.core.args.convertors;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.utils.ClassUtil;

public class DefaultConvertor extends Convertor {

	public DefaultConvertor(@Nonnull final Class<?> typeClass) {
		super(typeClass);
	}

	@Override
	@Nonnull
	public Object convert(@Nonnull final String str) {
		return ClassUtil.newInstance(typeClass, str);
	}

}
