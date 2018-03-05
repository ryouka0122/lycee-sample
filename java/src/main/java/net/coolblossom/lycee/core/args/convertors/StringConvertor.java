package net.coolblossom.lycee.core.args.convertors;

import javax.annotation.Nonnull;

public class StringConvertor extends Convertor {

	public StringConvertor(@Nonnull final Class<?> typeClass) {
		super(typeClass);
	}

	@Override
	@Nonnull
	public Object convert(@Nonnull final String str) {
		return str;
	}

}
