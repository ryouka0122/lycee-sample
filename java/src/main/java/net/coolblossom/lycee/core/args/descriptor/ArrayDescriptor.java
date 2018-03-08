package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

public class ArrayDescriptor extends FieldDescriptor {

	public ArrayDescriptor(@Nonnull final Field field, @Nonnull final Class<?> componentType) {
		super(field, componentType);
	}

	@Override
	public void set(final Object obj, final String value) {
		// TODO Auto-generated method stub

	}

}
