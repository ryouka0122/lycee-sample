package net.coolblossom.lycee.core.args.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.convertors.Convertor;
import net.coolblossom.lycee.core.args.convertors.ConvertorFactory;
import net.coolblossom.lycee.core.args.descriptors.ArrayDescriptor;
import net.coolblossom.lycee.core.args.descriptors.CollectionDescriptor;
import net.coolblossom.lycee.core.args.descriptors.FieldDescriptor;
import net.coolblossom.lycee.core.args.descriptors.MapDescriptor;
import net.coolblossom.lycee.core.args.descriptors.TypeDescriptor;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 *
 * <b>LyceeArgsに関するユーティリティクラス</b>
 * <p>
 * </p>
 * @author ryouka
 * @category LyceeArgs
 *
 */
public final class LyceeArgsUtil {
	private static Logger logger = Logger.getLogger(LyceeArgsUtil.class);

	private LyceeArgsUtil() { }


	/**
	 *
	 * <b>FieldからFieldDescriptorを生成するメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param field 対象フィールド
	 * @return {@link TypeDescriptor}のインスタンス
	 */
	@Nonnull
	public static FieldDescriptor createDescriptor(@Nonnull final Field field) {
		@Nonnull
		final Class<?> fieldType = field.getType();
		if(fieldType.isArray()) {
			// 配列型の時
			return new ArrayDescriptor(field, fieldType.getComponentType());
		}else if (ClassUtil.isParent(fieldType, Collection.class)) {
			// Collection型の時
			return new CollectionDescriptor(field, ClassUtil.getActualTypeArguments(field)[0]);
		}else if (ClassUtil.isParent(fieldType, Map.class)) {
			// Map型の時
			return new MapDescriptor(field, ClassUtil.getActualTypeArguments(field)[1]);
		}else {
			// それ以外（通常のフィールドと認識された）
			return new TypeDescriptor(field, fieldType);
		}
	}

	/**
	 * <b>変換処理クラスの取得メソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param clazz Convertorを継承した型クラス
	 * @param lyceeArg フィールドに付与されていたLyceeArgアノテーション
	 * @return 変換処理クラス
	 */
	@Nonnull
	public static Convertor createConvertor(@Nonnull final Class<?> clazz, @Nullable final LyceeArg lyceeArg) {
		final ConvertorFactory factory = ConvertorFactory.getInstance();
		return factory.createConvertor(clazz, lyceeArg);
	}


	/**
	 * <b>フィールドの型とアノテーションの型の継承チェック</b>
	 * <p>
	 * </p>
	 *
	 * @param containerType アノテーションの型
	 * @param fieldType フィールドの型
	 */
	public static void verifyClassAndInheritance(@Nullable final Class<?> containerType, @Nonnull final Class<?> fieldType) {
		if(containerType==null || containerType.equals(Object.class)) {
			throw new LyceeRuntimeException("LyceeArgCollection#containerにインスタンス生成時の型を指定してください");
		}
		if(!ClassUtil.isParent(containerType, fieldType)) {
			throw new LyceeRuntimeException(
					String.format("LyceeArgCollection#containerにはフィールドの型と同じか継承した型を指定してください[field=%s / LyceeArgCollection#container=%s]",
							fieldType.getName(), containerType.getName()));
		}
		if(!ClassUtil.isImplementationClass(containerType)) {
			throw new LyceeRuntimeException(
					String.format("LyceeArgCollection#containerにインタフェイスクラスや抽象クラスを指定することはできません[LyceeArgCollection#container=%s]", containerType.getName()));
		}
	}

	@Nonnull
	public static Field verifyField(@Nonnull final Field field, @Nonnull final Class<?> parent) {
		if(!ClassUtil.isParent(field.getType(), parent)) {
			throw new LyceeRuntimeException(
					String.format("%s型のフィールドではありません[field=%s]", parent.getName(), field.getName()));
		}
		return field;
	}

}
