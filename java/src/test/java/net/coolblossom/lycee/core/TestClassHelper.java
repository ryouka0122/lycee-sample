package net.coolblossom.lycee.core;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.descriptors.FieldDescriptor;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

/**
 * <b>テスト用ヘルパ</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class TestClassHelper {

	/**
	 * インスタンス生成＋特定フィールドにセット<b></b>
	 * <p>
	 * </p>
	 *
	 * @param clazz
	 * @param fieldName
	 * @param testData
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Nonnull
	public static <T, S extends FieldDescriptor> T makeTestClass(
			@Nonnull final Class<T> clazz,
			@Nonnull final String fieldName,
			@Nonnull final Class<S> descriptorClass,
			@Nonnull final String testData[])
					throws NoSuchFieldException, SecurityException,
					IllegalArgumentException, IllegalAccessException
	{
		final T testClass = ClassUtil.newInstance(clazz);
		final Field field = testClass.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		Class<?>[] actualTypes = null;
		if(field.getType().isArray()) {
			actualTypes = new Class<?>[] { field.getType().getComponentType() };
		}else {
			actualTypes = ClassUtil.getActualTypeArguments(field);
		}
		final Class<?> componentType = actualTypes[actualTypes.length-1];
		final S descriptor = ClassUtil.newInstance(descriptorClass, field, componentType);
		for(final String data : testData) {
			descriptor.set(testClass, fieldName, data);
		}
		return testClass;
	}

	public static <T> T getFieldValue(final Object obj, final String fieldName) {
		try {
			final Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return (T) field.get(obj);
		}catch(final ReflectiveOperationException e) {
			// to Uncheckable
			throw new RuntimeException(e);
		}
	}



}
