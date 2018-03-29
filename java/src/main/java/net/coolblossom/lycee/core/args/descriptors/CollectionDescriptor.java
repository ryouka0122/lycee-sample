package net.coolblossom.lycee.core.args.descriptors;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.utils.ClassUtil;
import net.coolblossom.lycee.core.args.utils.LyceeArgsUtil;

/**
 * <b>コレクション用記述子</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class CollectionDescriptor extends TypeDescriptor {

	@Nonnull
	private final Class<?> actualContainerType;


	/**
	 * コンストラクタ
	 * @param field
	 * @param actualType
	 */
	public CollectionDescriptor(@Nonnull final Field field) {
		super(field, ClassUtil.getActualTypeArguments(field)[0]);
		actualContainerType = LyceeArgsUtil.getActualFieldType(field);
	}

	@Override
	protected Field verifyField(final Field field) {
		return LyceeArgsUtil.verifyField(field, Collection.class);
	}

	@Override
	public void setValue(@Nonnull final Object obj, @Nonnull final String value)
			throws IllegalArgumentException, IllegalAccessException {
		final Collection coll = (Collection) LyceeArgsUtil.getFieldObject(field, obj, actualContainerType);
		coll.add(convertor.convert(value));
		field.set(obj, coll);
	}

}
