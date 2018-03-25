package net.coolblossom.lycee.core.args.enums;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.stream.Stream;

import org.junit.Test;

import net.coolblossom.lycee.core.commons.collect.Tuple;

public class LyceeDateFormatTest {

	@Test
	public void test_normal() {
		Stream.of(
				"20180101"
				, "20180201"
				, "20181231"
				, "20200229"	// leak day
				)
		.forEach(date -> {
			try {
				LyceeDateFormat.COMPACT_YYYY_MM_DD.convert(date);
			} catch (final ParseException e) {
				fail(e.getMessage());
			}
		});
	}

	@Test
	public void test_illegal() {
		Stream.of(
				Tuple.make(LyceeDateFormat.COMPACT_YYYY_MM_DD, "")
				, Tuple.make(LyceeDateFormat.COMPACT_YYYY_MM_DD, "2018")
				, Tuple.make(LyceeDateFormat.COMPACT_YYYY_MM_DD, "201801")
				, Tuple.make(LyceeDateFormat.COMPACT_YYYY_MM_DD, "20180199")
				, Tuple.make(LyceeDateFormat.COMPACT_YYYY_MM_DD, "20180229")
				, Tuple.make(LyceeDateFormat.COMPACT_YYYY_MM_DD, "2018-01-01")
				, Tuple.make(LyceeDateFormat.COMPACT_YYYY_MM_DD, "20180101123456")

				, Tuple.make(LyceeDateFormat.DASH_YYYY_MM_DD, "")
				, Tuple.make(LyceeDateFormat.DASH_YYYY_MM_DD, "2018")
				, Tuple.make(LyceeDateFormat.DASH_YYYY_MM_DD, "201801")
				, Tuple.make(LyceeDateFormat.DASH_YYYY_MM_DD, "20180199")
				, Tuple.make(LyceeDateFormat.DASH_YYYY_MM_DD, "20180101")
				, Tuple.make(LyceeDateFormat.DASH_YYYY_MM_DD, "2018-01-01 12:34:56")

				, Tuple.make(LyceeDateFormat.DASH_YYYY_MM_DD__HH_MM_SS, "2018-01-01")

				)
		.forEach(tuple -> {

			try {
				final LyceeDateFormat ldf = tuple.get(0);
				ldf.convert(tuple.get(1));
				fail();
			} catch (final ParseException e) {
				;
			}
		});
	}

}
