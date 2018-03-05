package net.coolblossom.lycee.core.args.convertors;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.enums.LyceeCodeEnum;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

public class CodeEnumConvertor extends Convertor {

	@Nonnull
	private final LyceeCodeEnum valueList[];

	public CodeEnumConvertor(@Nonnull final Class<?> typeClass) {
		super(typeClass);
		Stream.of(typeClass.getInterfaces())
		.filter(clazz -> clazz.equals(LyceeCodeEnum.class))
		.findAny()
		.orElseThrow(() -> new LyceeRuntimeException("LyceeCodeEnumが継承されていません。[class="+typeClass.getName()+"]"));

		valueList = (LyceeCodeEnum[]) typeClass.getEnumConstants();
	}

	@Override
	@Nonnull
	public Object convert(@Nonnull final String str) {
		return Stream.of(valueList)
				.filter(enumValue -> enumValue.getCode().equals(str))
				.findAny()
				.orElseThrow(() -> new LyceeRuntimeException("未定義のコード値です[code="+str+"]"))
				;
	}




}
