package net.coolblossom.lycee.core.args.convertors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.enums.LyceeCodeEnum;
import net.coolblossom.lycee.core.args.enums.LyceeDateFormat;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
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
	private final Map<Class<?>, Class<? extends Convertor>> convertorMap;


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
	public synchronized void addConvertor(@Nonnull final Class<?> baseClass, @Nonnull final Class<? extends Convertor> convertorClass) {
		convertorMap.put(baseClass, convertorClass);
	}

	/**
	 * <b>変換処理クラスの削除</b>
	 * <p>
	 * </p>
	 *
	 * @param baseClass 指定クラス
	 */
	public synchronized void removeConvertor(@Nonnull final Class<?> baseClass) {
		convertorMap.remove(baseClass);
	}

	/**
	 * <b>変換処理クラスの全削除</b>
	 * <p>
	 * </p>
	 *
	 * @param baseClass 指定クラス
	 */
	public synchronized void removeAllConvertor() {
		convertorMap.clear();
	}

	/**
	 * <b>変換処理クラスの確認</b>
	 * <p>
	 * </p>
	 *
	 * @param baseClass 指定クラス
	 * @return 登録済みの場合TRUEを返す
	 */
	public synchronized boolean containsBaseClass(@Nonnull final Class<?> baseClass) {
		return convertorMap.containsKey(baseClass);
	}

	/**
	 * <b>変換処理クラスの確認</b>
	 * <p>
	 * </p>
	 *
	 * @param convertorClass 変換処理クラス
	 * @return 登録済みの場合TRUEを返す
	 */
	public synchronized boolean containsConvertor(@Nonnull final Class<?> convertorClass) {
		return convertorMap.containsValue(convertorClass);
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

		// 追加されたコンバータに該当するかチェック（追加されたほうが優先される）
		final Class<? extends Convertor> convertor = convertorMap.get(clazz);
		if(convertor!=null) {
			return newConvertor(convertor, clazz, lyceeArg);
			// FIXME:下のような方法でインスタンス生成ができるようにしたい
			//return ClassUtil.newInstance(convertor, clazz, lyceeArg);
		}

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
			return new DateConvertor(lyceeArg!=null ? lyceeArg.dateFormat() : LyceeDateFormat.COMPACT_YYYY_MM_DD);
		}else if(String.class.equals(clazz)) {
			return new StringConvertor();
		}


		// すべてに該当しなかったクラスの変換処理クラス
		return new DefaultConvertor(clazz);
	}

	/**
	 * <b>変換処理クラスのインスタンス生成メソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param convertor 変換処理クラス
	 * @param clazz 変換処理クラスのコンストラクタで使用する第1引数
	 * @param lyceeArg 変換処理クラスのコンストラクタで使用する第2引数
	 * @return 変換処理クラスのインスタンス
	 */
	private Convertor newConvertor(final Class<? extends Convertor> convertor, final Class<?> clazz, final LyceeArg lyceeArg) {
		try {
			final Constructor ctor = convertor.getDeclaredConstructor(Class.class, LyceeArg.class);
			return (Convertor) ctor.newInstance(clazz, lyceeArg);
		}catch(final NoSuchMethodException e) {
			throw new LyceeRuntimeException("該当するコンストラクタがありませんでした", e);
		} catch (final SecurityException e) {
			throw new LyceeRuntimeException("コンストラクタの取得に失敗しました", e);
		} catch (final InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			throw new LyceeRuntimeException("インスタンス生成に失敗しました", e);
		}
	}
}
