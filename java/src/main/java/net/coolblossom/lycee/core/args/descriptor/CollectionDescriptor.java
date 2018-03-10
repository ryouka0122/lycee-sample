package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

/**
 * <b></b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class CollectionDescriptor extends CollectableDescriptor {

	/**
	 * コンストラクタ
	 * @param field
	 * @param actualType
	 */
	public CollectionDescriptor(@Nonnull final Field field, @Nonnull final Class<?> actualType) {
		super(verifyField(field), actualType);
	}

	/**
	 * <b></b>
	 * <p>
	 * </p>
	 *
	 * @param field
	 * @return
	 */
	@Nonnull
	private static Field verifyField(@Nonnull final Field field) {
		if(!ClassUtil.isParent(field.getType(), Collection.class)) {
			throw new LyceeRuntimeException(
					String.format("Collection型のフィールドではありません[field=%s]",field.getName()));
		}
		return field;
	}

	@Override
	public void set(@Nonnull final Object obj, @Nullable final String value) {
		try {
			if(value!=null) {
				final Collection coll = (Collection) getFieldObject(obj);
				coll.add(convertor.convert(value));
				field.set(obj, coll);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new LyceeRuntimeException(e);
		}

	}
}
