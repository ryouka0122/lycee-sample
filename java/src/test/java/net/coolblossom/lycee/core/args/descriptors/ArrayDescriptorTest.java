package net.coolblossom.lycee.core.args.descriptors;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.junit.Test;

import net.coolblossom.lycee.core.TestClassHelper;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.StringHolder;
import net.coolblossom.lycee.core.args.testutil.TestClassCollectionCase;

public class ArrayDescriptorTest {

	@Test
	public void test_int_array() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final String[] testData = {"0", "1", "2"};
		final TestClassCollectionCase testClass = makeTestClass("argIntArray", testData);

		final String actual = Arrays.stream((int[])TestClassHelper.getFieldValue(testClass, "argIntArray"))
				.mapToObj(n -> ""+n)
				.collect(Collectors.joining(","));

		assertEquals("0,1,2",actual);
	}

	@Test
	public void test_string_array() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final String[] testData = {"A", "1", "2"};
		final TestClassCollectionCase testClass = makeTestClass("argStrArray", testData);

		final String actual = Stream.of((String[])TestClassHelper.getFieldValue(testClass, "argStrArray"))
				.map(o-> o.toString())
				.collect(Collectors.joining(","));

		assertEquals("A,1,2",actual);
	}

	@Test
	public void test_class_array() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final String[] testData = {"A", "1", "2"};
		final TestClassCollectionCase testClass = makeTestClass("argClsArray", testData);
		;
		final String actual = Stream.of((StringHolder[])TestClassHelper.getFieldValue(testClass, "argClsArray"))
				.map(o-> o.toString())
				.collect(Collectors.joining(","));

		assertEquals("A,1,2",actual);
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_verifyField_invalid() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = TestClassCollectionCase.class.getDeclaredField("argStrList");
		field.setAccessible(true);
		new ArrayDescriptor(field, field.getType().getComponentType());		// -> throw LyceeRuntimeException
	}

	@Nonnull
	private TestClassCollectionCase makeTestClass(
			@Nonnull final String fieldName,
			@Nonnull final String testData[])
					throws NoSuchFieldException, SecurityException,
					IllegalArgumentException, IllegalAccessException
	{
		return TestClassHelper.makeTestClass(TestClassCollectionCase.class, fieldName, ArrayDescriptor.class, testData);
	}

}
