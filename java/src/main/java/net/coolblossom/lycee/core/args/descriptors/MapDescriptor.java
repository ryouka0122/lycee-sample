package net.coolblossom.lycee.core.args.descriptors;

import java.lang.reflect.Field;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import net.coolblossom.lycee.core.args.ConvertorFactory;
import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.convertors.Convertor;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.ClassUtil;
import net.coolblossom.lycee.core.args.utils.LyceeArgsUtil;

/**
 * <b>マップ用記述子</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class MapDescriptor extends FieldDescriptor {
	private static final Logger logger = Logger.getLogger(MapDescriptor.class);

	/** キーに対する変換器 */
	@Nonnull
	private final Convertor keyConvertor;

	/** 値に対する変換器 */
	@Nonnull
	private final Convertor valueConvertor;

	/**  */
	@Nonnull
	private final Class<?> actualContainerType;

	/**
	 * コンストラクタ
	 * @param field 対象フィールド
	 * @param actualType Map型の2つ目の型パラメータの型クラス
	 */
	public MapDescriptor(@Nonnull final Field field) {
		super(field);

		final Class<?> actualTypes[] = ClassUtil.getActualTypeArguments(field);
		final ConvertorFactory factory = ConvertorFactory.getInstance();
		actualContainerType = LyceeArgsUtil.getActualFieldType(field);
		keyConvertor = factory.createConvertor(actualTypes[0], null);
		valueConvertor = factory.createConvertor(actualTypes[1], field.getDeclaredAnnotation(LyceeArg.class));
	}

	@Override
	protected Field verifyField(final Field field) {
		return LyceeArgsUtil.verifyField(field, Map.class);
	}


	@Override
	public boolean matches(@Nonnull final String key) {
		// Map型はすべて許容する
		return true;
	}

	@Override
	public boolean set(@Nonnull final Object obj, @Nonnull final String key, @Nonnull final String value)
			throws IllegalArgumentException, IllegalAccessException {
		logger.info("key=" + key + " / value="+ value);
		final Map map = (Map) LyceeArgsUtil.getFieldObject(field, obj, actualContainerType);
		map.put(keyConvertor.convert(key), valueConvertor.convert(value));
		field.set(obj, map);
		return true;
	}


	@Override
	public void setValue(@Nonnull final Object obj, @Nonnull final String value)
			throws IllegalArgumentException, IllegalAccessException {
		throw new LyceeRuntimeException("MapDescritptor#setValue()は使用できません");
	}

}
