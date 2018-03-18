package net.coolblossom.lycee.core.commons.collect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class TupleTest {

	@Test
	public void test_iterator() {
		final Tuple tuple = Tuple.make(1,2,3,4,5);
		final Iterator<Object> iter = tuple.iterator();
		Integer expected = 1;
		while(iter.hasNext()) {
			assertEquals(expected++, iter.next());
		}
	}

	@Test
	public void test_iterator_direct() {
		final Tuple tuple = Tuple.make(1,2,3,4,5);
		Integer expected=1;
		for(final Object actual : tuple) {
			assertEquals(expected++, actual);
		}
	}

	@Test
	public void test_string_only() {
		final Tuple tuple = Tuple.make("A", "B", "C");

		assertEquals(3, tuple.size());

		assertEquals("A", tuple.get(0));
		assertEquals("B", tuple.get(1));
		assertEquals("C", tuple.get(2));

	}

	@Test
	public void test_type_complex() {
		final Tuple tuple = Tuple.make("A", 10000, Math.PI);

		/** actual type */
		assertTrue(tuple.check(0, String.class));
		assertTrue(tuple.check(1, Integer.class));
		assertTrue(tuple.check(2, Double.class));

		/** illegal type */
		assertFalse(tuple.check(0, Integer.class));
		assertFalse(tuple.check(1, Float.class));
		assertFalse(tuple.check(2, List.class));

		/** parent type */
		assertTrue(tuple.check(0, Object.class));
		assertTrue(tuple.check(1, Number.class));

	}

	@Test
	public void test_cast() {
		final Tuple tuple = Tuple.make("A", 10000, Math.PI);

		/** actual type */
		final String str = tuple.get(0);
		final Integer num = tuple.get(1);
		final Double pi = tuple.get(2);

		/** illegal type */
		try {
			final Integer illegal = tuple.get(0);
			fail();
		}catch(final ClassCastException e) {
			;
		}

		/** parent type */
		try{
			final Number trual = tuple.get(2);
		}catch(final ClassCastException e) {
			fail();
		}
	}

	@Test
	public void test_getIfBadCast() {
		final Tuple tuple = Tuple.make("A", 10000, Math.PI);
		final String result1 = tuple.getIfBadCast(0, "B");
		assertEquals("A", result1);
		assertNotEquals("B", result1);

		final Integer result2 = tuple.getIfBadCast(0, 123);
		assertEquals(123, result2.intValue());
	}

	@Test
	public void test_getTypeList() {
		final Tuple tuple = Tuple.make("A", 10000, Math.PI);

		final List<Class<?>> expected = Stream.of(String.class, Integer.class, Double.class)
				.collect(Collectors.toList());
		final List<Class<?>> actual = tuple.getTypeList();
		for(int i=0 ; i<expected.size() ; i++) {
			assertEquals(expected.get(i), actual.get(i));
		}
	}

	@Test
	public void test_equals() {
		final Tuple tuple1 = Tuple.make("A", 10000, Math.PI);	// 基準となるタプル
		final Tuple tuple2 = Tuple.make("A", 10000, Math.PI);	// 同じ値を持つタプル
		final Tuple tuple3 = Tuple.make("A", 100, 0.02);		// 同じ型を持つタプル
		final Tuple tuple4 = Tuple.make("B", 100, 123);			// 一部だけ型を持つタプル
		final Tuple tuple5 = Tuple.make(1,2,3,4,5);				// 値の数が違うタプル

		assertTrue(tuple1.equals(tuple1));
		assertTrue(tuple1.equals(tuple2));
		assertFalse(tuple1.equals(tuple3));
		assertFalse(tuple1.equals(tuple4));
		assertFalse(tuple1.equals(tuple5));
		assertFalse(tuple1.equals("A"));
	}

	@Test
	public void test_toString() {
		final Tuple tuple = Tuple.make("A", 10, 0.1);
		assertEquals("(A, 10, 0.1)", tuple.toString());
	}





}
