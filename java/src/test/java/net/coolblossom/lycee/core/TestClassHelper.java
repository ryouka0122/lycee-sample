package net.coolblossom.lycee.core;

import java.lang.reflect.Field;

import net.coolblossom.lycee.core.args.descriptors.ArrayDescriptor;
import net.coolblossom.lycee.core.args.testutil.TestClassCollectionCase;

public class TestClassHelper {

	public static TestClassCollectionCase makeTestClass(final String fieldName, final String testData[]) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final TestClassCollectionCase testClass = new TestClassCollectionCase();
		final Field field = testClass.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		final Class<?> componentType = field.getType().getComponentType();
		final ArrayDescriptor descriptor = new ArrayDescriptor(field, componentType);
		for(final String data : testData) {
			descriptor.set(testClass, fieldName, data);
		}
		return testClass;
	}

	public static <T> T getFieldValue(final Object obj, final String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return (T) field.get(obj);
	}



}
