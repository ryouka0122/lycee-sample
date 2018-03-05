package net.coolblossom.lycee.core.args.old_descriptors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * <b>FieldDescriptor</b>
 *
 * @author ryouka
 *
 */
public interface FieldDescriptor {

	@Nonnull
	String getName();

	void bind(@Nonnull Object target, @Nullable String value);

	boolean checkFieldName(@Nonnull String key);

	@Nonnull
	Class<?> getFieldType();

	default boolean isDefaultField() {
		// TODO: 動的に指定できるようにする
		return false;
	}

}