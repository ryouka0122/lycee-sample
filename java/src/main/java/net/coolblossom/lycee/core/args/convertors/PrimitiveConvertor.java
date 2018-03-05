package net.coolblossom.lycee.core.args.convertors;

import java.util.function.Function;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

public class PrimitiveConvertor extends Convertor {

	/*
	@SuppressWarnings("null")
	@Nonnull
	private static final Map<Class<?>, Function<String, Object>>
	PRIMITIVE_CONVERT_MAP = ImmutableMap.<Class<?>, Function<String,Object>>builder()
	.put(boolean.class, Boolean::getBoolean)
	.put(char.class, str -> str.indexOf(0))
	.put(byte.class, Byte::parseByte)
	.put(short.class, Short::parseShort)
	.put(int.class, Integer::parseInt)
	.put(long.class, Long::parseLong)
	.put(float.class, Float::parseFloat)
	.put(double.class, Double::parseDouble)
	.build();
	 */

	@Nonnull
	private final Function<String, Object> convertFunction;

	public PrimitiveConvertor(@Nonnull final Class<?> typeClass) {
		super(typeClass);
		//		if( !PRIMITIVE_CONVERT_MAP.containsKey(typeClass)) {
		//			throw new LyceeRuntimeException("未定義のPrimitive型です["+typeClass.getName()+"]");
		//		}
		//		convertFunction = PRIMITIVE_CONVERT_MAP.get(typeClass);

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
