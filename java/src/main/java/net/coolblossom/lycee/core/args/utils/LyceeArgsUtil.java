package net.coolblossom.lycee.core.args.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import net.coolblossom.lycee.core.args.descriptor.ArrayDescriptor;
import net.coolblossom.lycee.core.args.descriptor.CollectionDescriptor;
import net.coolblossom.lycee.core.args.descriptor.FieldDescriptor;
import net.coolblossom.lycee.core.args.descriptor.MapDescriptor;
import net.coolblossom.lycee.core.args.descriptor.TypeDescriptor;

/**
 *
 * <b>LyceeArgsに関するユーティリティクラス</b>
 * <p>
 * </p>
 * @author ryouka
 * @category LyceeArgs
 *
 */
public final class LyceeArgsUtil {
	private static Logger logger = Logger.getLogger(LyceeArgsUtil.class);

	private LyceeArgsUtil() { }


	/**
	 *
	 * <b>FieldからFieldDescriptorを生成するメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param field 対象フィールド
	 * @return {@link TypeDescriptor}のインスタンス
	 */
	@Nonnull
	public static FieldDescriptor createDescriptor(@Nonnull final Field field) {
		@Nonnull
		final Class<?> fieldType = field.getType();
		if(fieldType.isArray()) {
			return new ArrayDescriptor(field, fieldType.getComponentType());
		}

		if (ClassUtil.isParent(fieldType, Collection.class)) {
			return new CollectionDescriptor(field, ClassUtil.getActualTypeArguments(field)[0]);
		}else if (ClassUtil.isParent(fieldType, Map.class)) {
			return new MapDescriptor(field, ClassUtil.getActualTypeArguments(field)[1]);
		}else {
			return new TypeDescriptor(field, fieldType);
		}
	}




}
