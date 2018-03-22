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

	/** ロガー */
	private static Logger logger = Logger.getLogger(ConvertorFactory.class);

	/** Singletonインスタンス */
	private static ConvertorFactory theInstance = null;

	@Nonnull
	private final Map<Class<?>, Class<Convertor>> convertorMap;


	/**
	 * <b>ConvertorFactory生成メソッド</b>
	 * <p>
	 * </p>
	 *
	 * @return ConvertorFactoryのインスタンス
	 */
	@SuppressWarnings("null")
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

	/**
	 * <b>変換処理クラスの追加</b>
	 * <p>
	 * </p>
	 *
	 * @param baseClass 指定クラス
	 * @param convertorClass 変換処理クラス
	 */
	public synchronized void addConvertor(@Nonnull final Class<?> baseClass, @Nonnull final Class<Convertor> convertorClass) {
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
	public synchronized Convertor createConvertor(@Nonnull final Class<?> clazz, @Nullable final LyceeArg lyceeArg) {
		logger.info("class="+clazz.getName() + " / lyceeArg=" + (lyceeArg!=null));
		if(clazz.isPrimitive()) {
			// プリミティブ型のとき
			return new PrimitiveConvertor(clazz);
		}else if(clazz.isEnum()) {
			if(ClassUtil.isParent(clazz, LyceeCodeEnum.class)) {
				// LyceeCodeEnumを継承したEnum型のとき
				return new LyceeCodeEnumConvertor(clazz);
			}else{
				// 通常のEnum型のとき
				return new EnumConvertor(clazz);
			}
		}else if(Character.class.equals(clazz)) {
			// Characterだけほかのラッパーとは違うので個別対応
			// それ以外のWrapperはDefaultConvertorで対応可能
			return new WrapperConvertor(clazz, str->str.charAt(0));
		}else if(java.util.Date.class.equals(clazz)) {
			return new DateConvertor(clazz, lyceeArg!=null ? lyceeArg.dateFormat() : LyceeDateFormat.COMPACT_YYYY_MM_DD);
		}else if(String.class.equals(clazz)) {
			return new StringConvertor(clazz);
		}

		final Class<Convertor> convertor = convertorMap.get(clazz);
		if(convertor!=null) {
			return ClassUtil.newInstance(convertor, clazz, lyceeArg);
		}

		// すべてに該当しなかったクラスの変換処理クラス
		return new DefaultConvertor(clazz);
	}

}
