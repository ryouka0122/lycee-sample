package net.coolblossom.lycee.core.args.convertors;

import java.util.function.Function;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 * <b>プリミティブ型の変換処理クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class PrimitiveConvertor extends Convertor {

	/** 変換処理 */
	@Nonnull
	private final Function<String, Object> convertFunction;

	/**
	 * コンストラクタ
	 * @param typeClass
	 */
	public PrimitiveConvertor(@Nonnull final Class<?> typeClass) {
		super(typeClass);

		if( char.class.equals(typeClass)) {
			convertFunction = (value) -> value.charAt(0);
		}else if( boolean.class.equals(typeClass)) {
			convertFunction = Boolean::parseBoolean;
		}else if( byte.class.equals(typeClass)) {
			convertFunction = Byte::parseByte;
		}else if( short.class.equals(typeClass)) {
			convertFunction = Short::parseShort;
		}else if( int.class.equals(typeClass)) {
			convertFunction = Integer::parseInt;
		}else if( long.class.equals(typeClass) ) {
			convertFunction = Long::parseLong;
		}else if( double.class.equals(typeClass) ) {
			convertFunction = Double::parseDouble;
		}else if( float.class.equals(typeClass) ) {
			convertFunction = Float::parseFloat;
		}else {
			throw new LyceeRuntimeException("未定義のPrimitive型です["+typeClass.getName()+"]");
		}
	}

	@Override
	@Nonnull
	public Object convert(@Nonnull final String str) {
		return convertFunction.apply(str);
	}
}
