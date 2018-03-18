package net.coolblossom.lycee.core.args.descriptors;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.TestClassCollectionCase;

public class ArrayDescriptorTest {

	private TestClassCollectionCase makeTestClass(final String fieldName, final String testData[]) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
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

	private Object[] getFieldValue(final TestClassCollectionCase testClass, final String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = testClass.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return (Object[]) field.get(testClass);
	}


	@Test
	public void test_int_array() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final String[] testData = {"0", "1", "2"};
		final TestClassCollectionCase testClass = makeTestClass("argStrArray", testData);

		final String actual = Stream.of(getFieldValue(testClass, "argStrArray"))
				.map(o-> o.toString())
				.collect(Collectors.joining(","));

		assertEquals("0,1,2",actual);
	}

	@Test
	public void test_string_array() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final String[] testData = {"A", "1", "2"};
		final TestClassCollectionCase testClass = makeTestClass("argStrArray", testData);

		final String actual = Stream.of(getFieldValue(testClass, "argStrArray"))
				.map(o-> o.toString())
				.collect(Collectors.joining(","));

		assertEquals("A,1,2",actual);
	}

	@Test
	public void test_class_array() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final String[] testData = {"A", "1", "2"};
		final TestClassCollectionCase testClass = makeTestClass("argClsArray", testData);
		;
		final String actual = Stream.of(getFieldValue(testClass, "argClsArray"))
				.map(o-> o.toString())
				.collect(Collectors.joining(","));

		assertEquals("A,1,2",actual);
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_verifyField_invalid() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final String[] testData = {"A", "1", "2"};
		makeTestClass("argStrList", testData);
	}


}
