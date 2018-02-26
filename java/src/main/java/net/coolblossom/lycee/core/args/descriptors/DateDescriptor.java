package net.coolblossom.lycee.core.args.descriptors;

import java.lang.reflect.Field;
import java.text.ParseException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.enums.LyceeDateFormat;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 * <b>java.util.Date型用ディスクリプタ</b>
 * @author ryouka
 *
 */
public class DateDescriptor extends AbstractFieldDescriptor {

	@Nonnull
	private final LyceeDateFormat dateFormat;

	@SuppressWarnings("null")
	public DateDescriptor(@Nonnull final Field field) {
		super(field);
		final LyceeArg anno = field.getAnnotation(LyceeArg.class);
		dateFormat = anno.dateFormat();
	}

	@Override
	public void bind(@Nonnull final Object target, @Nullable final String value) {
		if( value==null || value.isEmpty() ) {
			return;
		}
		try {
			field.setAccessible(true);
			field.set(target, dateFormat.convert(value));
		} catch (final ParseException e) {
			throw new LyceeRuntimeException("日付のフォーマットが一致しません", e);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new LyceeRuntimeException(e);
		}
	}

}
