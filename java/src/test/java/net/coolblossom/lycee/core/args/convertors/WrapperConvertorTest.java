package net.coolblossom.lycee.core.args.convertors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * <b>WrapperConvertor テストケース</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class WrapperConvertorTest {

	@Test
	public void test_normal() {
		final Convertor convertor = new WrapperConvertor(String.class, String::toUpperCase);

		assertEquals("A", convertor.convert("a"));

	}

}
