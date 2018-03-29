package net.coolblossom.lycee.core.args.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.TestClassHelper;
import net.coolblossom.lycee.core.args.LyceeArgs;
import net.coolblossom.lycee.core.args.testutil.TestClassMapCase;

public class MapDescriptorTest {

	static class TestCase {
		int expectedLength;
		String args[];

		public TestCase(final int len, final String ...strings) {
			expectedLength = len;
			args = strings;
		}

	}

	/** テスト用データパターン */
	public static TestCase[] PARAMS = {
			new TestCase(1, "--argStrMap", "a", "--argStrMap", "b", "--argStrMap", "c"),
			new TestCase(3, "--argStrMap1", "a", "--argStrMap2", "b", "--argStrMap3", "c"),
	};


	@Test
	public void test_set() {
		Stream.of(PARAMS)
		.forEach(testData -> {
			try {
				final TestClassMapCase.SingleMapCase testClass = LyceeArgs.createAndMap(TestClassMapCase.SingleMapCase.class, testData.args).execute();

				final Map actual = TestClassHelper.getFieldValue(testClass, "argStrMap");
				assertNotNull(actual);
				assertEquals(testData.expectedLength, actual.size());
			} catch (final Exception e) {
				fail();
			}
		});
	}




}
