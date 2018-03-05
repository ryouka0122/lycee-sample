package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.convertors.Convertor;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.StringUtil;

/**
 *
 * <b>フィールドの情報を管理するクラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class FieldDescriptor {
	private final Logger logger = Logger.getLogger(FieldDescriptor.class);

	@Nonnull
	protected Field field;

	@Nonnull
	protected Convertor convertor;

	@Nonnull
	protected List<String> matchingNameList;

	/**
	 * コンストラクタ
	 * @param field
	 * @param convertor
	 */
	public FieldDescriptor(@Nonnull final Field field, @Nonnull final Convertor convertor) {
		this.field = field;
		this.convertor = convertor;
		matchingNameList = new ArrayList<>();

		String name = field.getName();
		if(field.isAnnotationPresent(LyceeArg.class)) {
			final LyceeArg lyceeArg = field.getDeclaredAnnotation(LyceeArg.class);
			if( !StringUtil.isEmpty(lyceeArg.name())) {
				name=lyceeArg.name();
			}

			if( !StringUtil.isEmpty(lyceeArg.alias()) ) {
				matchingNameList.add(lyceeArg.alias());
			}
		}
		matchingNameList.add(name);

		logger.info(String.format("field=%s / convertorName=%s / matchingNameList=%s", field.getName(),
				convertor.getClass().getName(),
				matchingNameList.stream().collect(Collectors.joining(",","[","]"))
				));
	}

	/**
	 *
	 * <b>キーとフィールド名がマッチしているか確認するメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param key キー
	 * @return マッチしていればTRUEを返す
	 */
	public boolean matches(@Nonnull final String key) {
		return matchingNameList.contains(key);
	}

	/**
	 *
	 * <b>変換処理</b>
	 * <p>
	 * </p>
	 *
	 * @param str 引数値
	 */
	public void set(final Object obj, @Nonnull final String str) {
		try {
			logger.info(String.format("field=%s / str=%s", field.getName(), str));
			final Object value = convertor.convert(str);

			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new LyceeRuntimeException(
					String.format("オブジェクトへのマッピングに失敗しました[field=%s/value=%s]", field.getName(), str),
					e);
		}
	}


}
