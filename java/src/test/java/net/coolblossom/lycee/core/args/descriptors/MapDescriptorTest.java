package net.coolblossom.lycee.core.args.descriptors;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import net.coolblossom.lycee.core.args.mappers.LyceeArgsMapper;
import net.coolblossom.lycee.core.args.testutil.TestClassMapCase;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

@RunWith(Theories.class)
public class MapDescriptorTest {

	static class MapTestCase {
		int expectedLength;
		String args[];

		public MapTestCase(final int len, final String ...strings) {
			expectedLength = len;
			args = strings;
		}

	}

	/** テスト用データパターン */
	@DataPoints
	public static MapTestCase[] PARAMS = {
			new MapTestCase(1, "--argStrMap", "a", "--argStrMap", "b", "--argStrMap", "c"),
			new MapTestCase(3, "--argStrMap1", "a", "--argStrMap2", "b", "--argStrMap3", "c"),
	};

	static int counter=1;

	@Theory
	public void test(final MapTestCase testCase) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		System.out.println("[test case "+counter++ +"]");

		final TestClassMapCase mapCase =
				LyceeArgsMapper.createAndMap(TestClassMapCase.class, testCase.args).execute();

		assertEquals(testCase.expectedLength, mapCase.argStrMap.size());

	}

	public void test1(final MapTestCase testCase) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final TestClassMapCase mapCase = new TestClassMapCase();
		final Field field = mapCase.getClass().getDeclaredField("argStrMap");
		field.setAccessible(true);

		final Class<?> actualTypes[] = ClassUtil.getActualTypeArguments(field);
		final MapDescriptor mapDesc = new MapDescriptor(field, actualTypes[1]);

		for(int ki=0, vi=1 ; vi<testCase.args.length ; ki++, vi++) {
			final String key=testCase.args[ki];
			final String value=testCase.args[vi];
			if(mapDesc.set(mapCase, key.substring(2), value)) {
				ki++;
				vi++;
			}
		}

		final Map actual = (Map)field.get(mapCase);
		actual.forEach((key, value)->{
			System.out.println(key+"="+value);
		});

		assertEquals(testCase.expectedLength, actual.size());


	}

}
