package net.coolblossom.lycee.core.args.convertors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * <b>StringConvertor テストケース</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class StringConvertorTest {

	@Test
	public void test_normal() {

		final Convertor convertor = new StringConvertor();

		final String testData = "A";
		final Object actual = convertor.convert(testData);

		assertEquals(testData, actual);

		// そのまま返すので同一インスタンスであることの確認
		assertTrue(testData==actual);

	}

}
