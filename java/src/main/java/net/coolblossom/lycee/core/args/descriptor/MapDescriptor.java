package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;
import java.util.Map;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

public class MapDescriptor extends CollectableDescriptor {

	public MapDescriptor(@Nonnull final Field field, @Nonnull final Class<?> actualType) {
		super(verifyField(field), actualType);
	}

	@Nonnull
	private static Field verifyField(@Nonnull final Field field) {
		if( !ClassUtil.isParent(field.getType(), Map.class)) {
			throw new LyceeRuntimeException(
					String.format("Map型のフィールドではありません[field=%s]",field.getName()));
		}
		return field;
	}

	@Override
	public void set(final Object obj, final String value) {
		try {
			final Map map = (Map) field.get(obj);
			;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new LyceeRuntimeException(e);
		}
	}

}
