package net.coolblossom.lycee.core.args.descriptors;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 *
 * <b>ArrayDescriptor</b>
 *
 * @author ryouka
 *
 */
public class ArrayDescriptor extends AbstractFieldDescriptor {

	public ArrayDescriptor(@Nonnull final Field field) {
		super(field);
	}

	@Override
	public void bind(@Nonnull final Object target, @Nullable final String value) {

		throw new LyceeRuntimeException("配列への自動紐づけは未実装です");
	}

}
