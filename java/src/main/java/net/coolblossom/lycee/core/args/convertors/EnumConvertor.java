package net.coolblossom.lycee.core.args.convertors;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 * <b>LyceeCodeEnumを継承していないEnum型の変換処理クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class EnumConvertor extends Convertor {

	/**
	 * コンストラクタ
	 * @param typeClass
	 */
	public EnumConvertor(@Nonnull final Class<?> typeClass) {
		super(typeClass);
	}

	@Override
	@Nonnull
	protected Class<?> verifyType(@Nonnull final Class<?> typeClass) {
		if(!typeClass.isEnum()) {
			throw new LyceeRuntimeException("列挙型ではありません。[class="+typeClass.getName()+"]");
		}
		return typeClass;
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
