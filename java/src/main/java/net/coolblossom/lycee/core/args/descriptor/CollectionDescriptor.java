package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CollectionDescriptor extends CollectableDescriptor {

	public CollectionDescriptor(@Nonnull final Field field, final Class<?> actualType) {
		super(field, actualType);
	}

	@Override
	public void set(@Nonnull final Object obj, @Nullable final String value) {
		// TODO Auto-generated method stub

	}
}
