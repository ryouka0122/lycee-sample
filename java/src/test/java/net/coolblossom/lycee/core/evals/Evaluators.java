package net.coolblossom.lycee.core.evals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.TestClassHelper;

public interface Evaluators {

	static public Evaluator isNull() {
		return (v) -> assertNull(v);
	}

	static public Evaluator isNull(
			@Nonnull final Object key
			) {
		return (object) -> {
			if(object instanceof Map) {
				assertMapNull(key, (Map)object);
			} else {
				assertNull(TestClassHelper.getFieldValue(object, key.toString()));
			}
		};
	}

	static public Evaluator notExists(@Nonnull final Object key) {
		return (actual) -> {
			if(actual instanceof Map) {
				assertFalse( ((Map)actual).containsKey(key) );
			}else if(actual instanceof Collection) {
				assertFalse( ((Collection)actual).contains(key) );
			} else {
				assertFalse( key.equals(actual) );
			}
		};
	}


	static public Evaluator isValue(
			@Nonnull final Object expected
			) {
		return (object) -> {
			if(object instanceof Collection) {
				assertTrue(((Collection)object).contains(expected));
			}else {
				assertEquals(expected, object);
			}
		};
	}

	static public Evaluator isValue(
			@Nonnull final Object key,
			@Nonnull final Object expected
			) {
		return (object) -> {
			if(object instanceof Map) {
				assertMapEquals(key, expected, (Map)object);
			} else {
				assertEquals(expected, TestClassHelper.getFieldValue(object, key.toString()));
			}
		};
	}

	static public Evaluator isEmpty() {
		return (object) -> {
			if(object == null) {
				;
			}else if(object instanceof Map) {
				assertTrue( ((Map)object).isEmpty());
			} else if(object instanceof Collection) {
				assertTrue( ((Collection)object).isEmpty() );
			} else {
				assertTrue(object.toString().isEmpty());
			}
		};
	}

	static public Evaluator isType(
			@Nonnull final Class<?> expected
			) {
		return (object) -> {
			assertEquals(expected, object.getClass());
		};
	}

	public static void assertMapNull(
			@Nonnull final Object key,
			@Nonnull final Map actual
			) {
		assertTrue(actual.containsKey(key));
		assertNull(actual.get(key));
	}


	static public void assertMapEquals(
			@Nonnull final Object key,
			@Nonnull final Object expected,
			@Nonnull final Map actual
			) {
		assertTrue(actual.containsKey(key));
		assertEquals(expected, actual.get(key));
	}


}
