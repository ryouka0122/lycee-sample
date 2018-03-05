package net.coolblossom.lycee.core.args.old_descriptors;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.StringUtil;

public class EnumDescriptor extends AbstractFieldDescriptor {

	public EnumDescriptor(@Nonnull final Field field) {
		super(field);
	}

	@Override
	public void bind(@Nonnull final Object target, @Nullable final String value) {
		if(value==null || value.isEmpty()) {
			return;
		}
		try {
			for(final Object enumValue : field.getType().getEnumConstants()) {
				if(StringUtil.equals(enumValue.toString(), value)) {
					field.setAccessible(true);
					field.set(target, enumValue);
					return;
				}
			}
		}catch(IllegalArgumentException|IllegalAccessException e) {
			throw new LyceeRuntimeException(e);
		}
	}

}
