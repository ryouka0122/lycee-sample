package net.coolblossom.lycee.core.args.convertors;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

public class EnumConvertor extends Convertor {

	public EnumConvertor(@Nonnull final Class<?> typeClass) {
		super(typeClass);
	}

	@Override
	@Nonnull
	public Object convert(@Nonnull final String str) {
		return Stream.of(typeClass.getEnumConstants())
				.filter(enumValue -> enumValue.toString().equals(str))
				.findAny()
				.orElseThrow(() -> new LyceeRuntimeException("未定義のコード値です[code="+str+"]"))
				;
	}




}
