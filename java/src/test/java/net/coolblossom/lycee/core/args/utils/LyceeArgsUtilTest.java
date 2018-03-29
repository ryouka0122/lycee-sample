package net.coolblossom.lycee.core.args.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.args.descriptors.ArrayDescriptor;
import net.coolblossom.lycee.core.args.descriptors.CollectionDescriptor;
import net.coolblossom.lycee.core.args.descriptors.FieldDescriptor;
import net.coolblossom.lycee.core.args.descriptors.MapDescriptor;
import net.coolblossom.lycee.core.args.descriptors.TypeDescriptor;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.testutil.TestClassCollectionCase;
import net.coolblossom.lycee.core.args.testutil.TestClassMapCase;
import net.coolblossom.lycee.core.args.testutil.TestClassSimpleCase;
import net.coolblossom.lycee.core.commons.collect.Tuple;

public class LyceeArgsUtilTest {

	static class TestPattern_createDescriptor {
		Class<?> expectedDescriptor;
		Class<?> testClazz;
		String name;
		TestPattern_createDescriptor(final Class<? extends FieldDescriptor> expectedDescriptor, final Class<?> testClazz, final String name) {
			this.expectedDescriptor = expectedDescriptor;
			this.testClazz = testClazz;
			this.name = name;
		}

		Field getField() throws NoSuchFieldException, SecurityException {
			return testClazz.getDeclaredField(name);
		}
	}

	@Test
	public void test_createDescriptor() {
		Stream.of(
				new TestPattern_createDescriptor(TypeDescriptor.class        , TestClassSimpleCase.class           , "argInt")
				, new TestPattern_createDescriptor(TypeDescriptor.class      , TestClassSimpleCase.class           , "argStr")
				, new TestPattern_createDescriptor(CollectionDescriptor.class, TestClassCollectionCase.class       , "argEnumList")
				, new TestPattern_createDescriptor(ArrayDescriptor.class     , TestClassCollectionCase.class       , "argIntArray")
				, new TestPattern_createDescriptor(MapDescriptor.class       , TestClassMapCase.SingleMapCase.class, "argStrMap")
				)
		.forEach(testCase -> {
			try {
				final Field testField = testCase.getField();
				final FieldDescriptor actualDescriptor = LyceeArgsUtil.createDescriptor(testField);
				assertEquals(testCase.expectedDescriptor, actualDescriptor.getClass());
			} catch (NoSuchFieldException | SecurityException e) {
				fail(e.getMessage());
			}
		});
	}

	@Test
	public void test_verifyClassAndInheritance_normal() {
		Stream.of(
				Tuple.make(HashMap.class,    Map.class),
				Tuple.make(HashMap.class, Object.class),
				Tuple.make( String.class, Object.class)
				)
		.forEach(tuple -> {
			try {
				LyceeArgsUtil.verifyClassAndInheritance(tuple.get(0), tuple.get(1));
			}catch(final LyceeRuntimeException e) {
				fail(e.getMessage());
			}
		});
	}

	@Test
	@SuppressWarnings("null")
	public void test_verifyClassAndInheritance_error() {
		Stream.of(
				Tuple.make(HashMap.class,       List.class),
				Tuple.make(HashMap.class, Collection.class),
				Tuple.make(   List.class, Collection.class),
				Tuple.make( String.class,    Integer.class),
				Tuple.make(         null,     String.class),
				Tuple.make( Object.class,     String.class)
				)
		.forEach(tuple -> {
			try {
				LyceeArgsUtil.verifyClassAndInheritance(tuple.get(0), tuple.get(1));
				fail();
			}catch(final LyceeRuntimeException e) {
				;
			}
		});
	}

	@Test
	public void test_verifyField_normal() {
		Stream.of(
				Tuple.make(TestClassSimpleCase.class           , "argInt"     , int.class       ),
				Tuple.make(TestClassSimpleCase.class           , "argStr"     , String.class    ),
				Tuple.make(TestClassCollectionCase.class       , "argIntArray", int[].class     ),
				Tuple.make(TestClassCollectionCase.class       , "argStrArray", String[].class  ),
				Tuple.make(TestClassCollectionCase.class       , "argStrList" , Collection.class),
				Tuple.make(TestClassCollectionCase.class       , "argStrSet"  , Collection.class),
				Tuple.make(TestClassMapCase.SingleMapCase.class, "argStrMap"  , Map.class       )
				)
		.forEach(tuple -> {
			try {
				final Field field = tuple.getIfBadCast(0, Class.class).getDeclaredField(tuple.get(1));
				// LyceeArgsUtil#verifyFieldの第一引数がそのまま返却されていること
				assertTrue( field == LyceeArgsUtil.verifyField(field, tuple.get(2)) );
			}catch(final Exception e) {
				fail(e.getMessage());
			}
		});
	}

	@Test
	public void test_verifyField_error() {
		Stream.of(
				Tuple.make(TestClassSimpleCase.class, "argInt", Integer.class)
				, Tuple.make(TestClassSimpleCase.class, "argStr", Double.class)
				)
		.forEach(tuple -> {
			try {
				final Field field = tuple.getIfBadCast(0, Class.class).getDeclaredField(tuple.get(1));
				LyceeArgsUtil.verifyField(field, tuple.get(2));
				fail();
			}catch(final LyceeRuntimeException e) {
				// this catch statement is valid pattern
			}catch(final Exception e) {
				fail();
			}
		});
	}

}
