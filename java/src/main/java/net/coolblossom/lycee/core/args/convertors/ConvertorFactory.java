package net.coolblossom.lycee.core.args.convertors;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.enums.LyceeCodeEnum;
import net.coolblossom.lycee.core.args.enums.LyceeDateFormat;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

/**
 *
 * <b>コンバータ生成クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class ConvertorFactory {


	private static Logger logger = Logger.getLogger(ConvertorFactory.class);

	private static ConvertorFactory theInstance = null;

	@Nonnull
	private final Map<Class<?>, Class<Convertor>> convertorMap;


	/**
	 *
	 * <b>ConvertorFactory生成メソッド</b>
	 * <p>
	 * </p>
	 *
	 * @return ConvertorFactoryのインスタンス
	 */
	@Nonnull
	public static synchronized ConvertorFactory getInstance() {
		if(theInstance==null) {
			theInstance = new ConvertorFactory();
		}
		return theInstance;
	}

	protected ConvertorFactory() {
		convertorMap = new HashMap<>();
	}

	public void addConvertor(@Nonnull final Class<?> baseClass, @Nonnull final Class<Convertor> convertorClass) {
		convertorMap.put(baseClass, convertorClass);
	}

	/**
	 *
	 * <b>Convertor生成メソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param clazz 変換後クラス
	 * @param lyceeArg フィールドに付与されていた{@link LyceeArg}
	 * @return Convertorインスタンス
	 */
	@Nonnull
	public Convertor createConvertor(@Nonnull final Class<?> clazz, @Nullable final LyceeArg lyceeArg) {
		logger.info("class="+clazz.getName() + " / lyceeArg=" + (lyceeArg!=null));
		if(clazz.isPrimitive()) {
			return new PrimitiveConvertor(clazz);
		}else if(clazz.isEnum()) {
			if(ClassUtil.isParent(clazz, LyceeCodeEnum.class)) {
				return new CodeEnumConvertor(clazz);
			}else {
				return new EnumConvertor(clazz);
			}
		}else if(Character.class.equals(clazz)) {
			// Characterだけほかのラッパーとは違うので個別対応
			return new WrapperConvertor(clazz, str->str.charAt(0));
		}else if(java.util.Date.class.equals(clazz)) {
			return new DateConvertor(clazz, lyceeArg!=null ?lyceeArg.dateFormat(): LyceeDateFormat.COMPACT_YYYY_MM_DD);
		}

		final Class<Convertor> convertor = convertorMap.get(clazz);
		if(convertor!=null) {
			return ClassUtil.newInstance(convertor, clazz, lyceeArg);
		}
		return new DefaultConvertor(clazz);
	}

}
