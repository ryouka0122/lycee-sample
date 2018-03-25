package net.coolblossom.lycee.core.args.convertors;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.junit.Test;

import net.coolblossom.lycee.core.args.enums.LyceeDateFormat;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 * <b>DateConvertor テストケース</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class DateConvertorTest {

	static class TestClass {
		LyceeDateFormat format;
		String arg;
		Date expected;

		TestClass(
				@Nonnull final LyceeDateFormat format,
				@Nonnull final String arg,
				@Nonnull final String expected
				) throws ParseException
		{
			this.format = format;
			this.arg = arg;
			this.expected = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expected);
		}

		void test() {
			final Convertor convertor = new DateConvertor(format);
			final Object result = convertor.convert(arg);
			System.out.println(result);
			assertTrue(expected.equals(result));
		}
	}


	@Test
	public void test_normal() throws ParseException {
		Stream.of(
				new TestClass(LyceeDateFormat.COMPACT_YYYY_MM_DD          , "20180101"           , "2018-01-01 00:00:00"),
				new TestClass(LyceeDateFormat.COMPACT_YYYY_MM_DD__HH_MM_SS, "20180101123456"     , "2018-01-01 12:34:56"),
				new TestClass(LyceeDateFormat.SLASH_YYYY_MM_DD            , "2018/01/01"         , "2018-01-01 00:00:00"),
				new TestClass(LyceeDateFormat.SLASH_YYYY_MM_DD__HH_MM_SS  , "2018/01/01 12:34:56", "2018-01-01 12:34:56"),
				new TestClass(LyceeDateFormat.DASH_YYYY_MM_DD             , "2018-01-01"         , "2018-01-01 00:00:00"),
				new TestClass(LyceeDateFormat.DASH_YYYY_MM_DD__HH_MM_SS   , "2018-01-01 12:34:56", "2018-01-01 12:34:56"),
				new TestClass(LyceeDateFormat.JPN_YYYY_MM_DD              , "2018年01月01日"            , "2018-01-01 00:00:00"),
				new TestClass(LyceeDateFormat.JPN_YYYY_MM_DD__HH_MM_SS    , "2018年01月01日 12時34分56秒", "2018-01-01 12:34:56")
				)
		.forEach(TestClass::test);
	}

	@Test(expected=LyceeRuntimeException.class)
	public void test_exception() {
		final Convertor convertor = new DateConvertor(LyceeDateFormat.COMPACT_YYYY_MM_DD);
		final Object actual = convertor.convert("2018-01-01");
		assertNull(actual);

	}

	/**
	 * <b>確認用テストメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @throws ParseException
	 */
	@Test
	public void check_date_format() throws ParseException {

		Stream.of(
				"20180101"           ,
				"20180101123456"     ,
				"2018/01/01"         ,
				"2018/01/01 12:34:56",
				"2018-01-01"         ,
				"2018-01-01 12:34:56",
				"2018年01月01日"            ,
				"2018年01月01日 12時34分56秒"
				)
		.forEach(date -> {
			System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * *");
			System.out.println("date -> " + date);
			Stream.of(
					LyceeDateFormat.COMPACT_YYYY_MM_DD
					,LyceeDateFormat.COMPACT_YYYY_MM_DD__HH_MM_SS
					,LyceeDateFormat.SLASH_YYYY_MM_DD
					,LyceeDateFormat.SLASH_YYYY_MM_DD__HH_MM_SS
					,LyceeDateFormat.DASH_YYYY_MM_DD
					,LyceeDateFormat.DASH_YYYY_MM_DD__HH_MM_SS
					,LyceeDateFormat.JPN_YYYY_MM_DD
					,LyceeDateFormat.JPN_YYYY_MM_DD__HH_MM_SS
					)
			.forEach(ldf -> {
				final String fmt = ldf.getFormat();
				System.out.println(String.format("lycee:%5s java7:%5s java8:%5s [%s]",
						checkDateForLycee(ldf, date),
						checkDateForJava7(fmt, date),
						checkDateForJava8(fmt, date),
						fmt
						));
				final String orgFmt = fmt.replace("\'", "");
				if(!orgFmt.equals(fmt)) {
					System.out.println(String.format("lycee:%5s java7:%5s java8:%5s [%s]",
							checkDateForLycee(ldf, date),
							checkDateForJava7(orgFmt, date),
							checkDateForJava8(orgFmt, date),
							orgFmt
							));
				}
			});
		});
	}

	private Object checkDateForLycee(final LyceeDateFormat ldf, final String date) {
		try {
			ldf.convert(date);
			return true;
		}catch(final ParseException e) {
			return false;
		}
	}

	boolean checkDateForJava8(final String fmt, final String date) {
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(fmt);
		dtf.withResolverStyle(ResolverStyle.STRICT);
		try {
			final LocalDate ld= LocalDate.parse(date, dtf);

		}catch(final DateTimeParseException e) {
			return false;
		}
		return true;
	}

	boolean checkDateForJava7(final String fmt, final String date) {
		final SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		sdf.setLenient(false);
		try {
			final String resolvedDate = sdf.format(sdf.parse(date));
			return resolvedDate.equals(date);
		}catch(final ParseException e) {
			return false;
		}
	}

}