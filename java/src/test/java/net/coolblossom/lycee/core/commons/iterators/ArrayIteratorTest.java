package net.coolblossom.lycee.core.commons.iterators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.stream.Stream;

import org.junit.Test;

public class ArrayIteratorTest {

	@Test
	public void test_make() {
		final ArrayIterator<Integer> iter = ArrayIterator.make(new Integer[] {1,2,3,4,5});
		Integer expected = 1;
		while(iter.hasNext()) {
			assertEquals(expected++, iter.next());
		}
	}

	@Test
	public void test_make_partial() {
		final Integer[] ary = new Integer[] {1,2,3,4,5};
		final ArrayIterator<Integer> iter = ArrayIterator.make(ary, 3,5);
		Integer expected = 4;
		while(iter.hasNext()) {
			assertEquals(expected++, iter.next());
		}
	}

	@Test
	public void test_make_illegal() {
		final Integer[] ary = new Integer[] {1,2,3,4,5};
		final int[][] params = {
				{-1,  3},
				{-1, -1},
				{ 4,  3},
				{ 6, 10},
		};
		Stream.of(params)
		.forEach(param -> {
			try {
				ArrayIterator.make(ary, param[0], param[1]);
				fail();
			}catch(final IllegalArgumentException e) {
				;
			}
		});
	}


}
