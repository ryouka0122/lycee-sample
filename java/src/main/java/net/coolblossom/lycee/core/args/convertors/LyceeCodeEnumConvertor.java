package net.coolblossom.lycee.core.args.convertors;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.enums.LyceeCodeEnum;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

/**
 * <b>LyceeArg専用Enumの変換処理クラス</b>
 * <p>
 * LyceeCodeEnumで定義されるcode値をもとに変換をかけます。
 * </p>
 * @author ryouka
 *
 */
public class LyceeCodeEnumConvertor extends Convertor {

	/** 対象のCodeEnumの定数 */
	@Nonnull
	private final LyceeCodeEnum valueList[];

	/**
	 * コンストラクタ
	 * @param typeClass
	 */
	public LyceeCodeEnumConvertor(@Nonnull final Class<?> typeClass) {
		super(typeClass);

		if( !ClassUtil.isParent(typeClass, LyceeCodeEnum.class) ) {
			throw new LyceeRuntimeException("LyceeCodeEnumが継承されていません。[class="+typeClass.getName()+"]");
		}

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
