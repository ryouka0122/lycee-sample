package net.coolblossom.lycee.core.args.descriptors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 何もしないFieldDescriptor（Null Object Pattern）
 *
 * @author ryouka
 *
 */
public class SilentDescriptor implements FieldDescriptor {

	@Override
	@Nonnull
	public String getName() {
		return "";
	}

	@Override
	public void bind(@Nonnull final Object target, @Nullable final String value) {
		return;
	}

	@Override
	public boolean checkFieldName(final @Nonnull String key) {
		return false;
	}

	@Override
	public boolean isDefaultField() {
		return false;
	}

	@Override
	@Nonnull
	public Class<?> getFieldType() {
		return Object.class;
	}

}
