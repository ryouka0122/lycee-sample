package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

/**
 *
 * <b>Collection型やMap型のフィールドに対する記述子</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public abstract class CollectableDescriptor extends FieldDescriptor {

	protected CollectableDescriptor(@Nonnull final Field field, final Class<?> actualType) {
		super(field, actualType);
	}

}
