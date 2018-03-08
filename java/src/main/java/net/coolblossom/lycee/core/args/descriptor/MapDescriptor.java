package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

public class MapDescriptor extends CollectableDescriptor {

	public MapDescriptor(@Nonnull final Field field, final Class<?> actualType) {
		super(field, actualType);
	}

	@Override
	public void set(final Object obj, final String value) {
		// TODO Auto-generated method stub

	}

}
