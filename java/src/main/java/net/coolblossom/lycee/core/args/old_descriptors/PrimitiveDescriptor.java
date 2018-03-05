package net.coolblossom.lycee.core.args.old_descriptors;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;


/**
 * <b>プリミティブ型用ディスクリプタ</b>
 * @author ryouka
 *
 */
public class PrimitiveDescriptor extends AbstractFieldDescriptor {

	/**
	 * コンストラクタ
	 * @see AbstractFieldDescriptor
	 * @param field 対象フィールド
	 */
	public PrimitiveDescriptor(@Nonnull final Field field) {
		super(field);
	}

	@Override
	public void bind(@Nonnull final Object target, @Nullable final String value) {
		if( value==null || value.isEmpty() ) {
			return;
		}
		final Class<?> type = field.getType();
		field.setAccessible(true);
		try {
			// FIXME: イケてるコードにしたい（if文の列挙を避けたい）
			if( char.class.equals(type)) {
				field.setChar(target, value.charAt(0));
			}else if( boolean.class.equals(type)) {
				field.setBoolean(target, Boolean.parseBoolean(value));
			}else if( byte.class.equals(type)) {
				field.setByte(target, Byte.parseByte(value));
			}else if( short.class.equals(type)) {
				field.setShort(target, Short.parseShort(value));
			}else if( int.class.equals(type)) {
				field.setInt(target, Integer.parseInt(value));
			}else if( long.class.equals(type) ) {
				field.setLong(target, Long.parseLong(value));
			}else if( double.class.equals(type) ) {
				field.setDouble(target, Double.parseDouble(value));
			}else if( float.class.equals(type) ) {
				field.setFloat(target, Float.parseFloat(value));
			}else {
				throw new LyceeRuntimeException(
						String.format("定義されていないプリミティブ型です[%s]", type.getName())
						);
			}
		}catch(IllegalArgumentException | IllegalAccessException e) {
			throw new LyceeRuntimeException(e);
		}
	}

}
