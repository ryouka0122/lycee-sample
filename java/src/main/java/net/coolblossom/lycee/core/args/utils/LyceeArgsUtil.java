package net.coolblossom.lycee.core.args.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import net.coolblossom.lycee.core.args.ConvertorFactory;
import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.convertors.Convertor;
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
			return new CollectionDescriptor(field);
		}else if (ClassUtil.isParent(fieldType, Map.class)) {
			// Map型の時
			return new MapDescriptor(field);
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
			throw new LyceeRuntimeException("LyceeArg#valueにインスタンス生成時の型を指定してください");
		}
		if(!ClassUtil.isParent(containerType, fieldType)) {
			throw new LyceeRuntimeException(
					String.format("LyceeArg#valueにはフィールドの型と同じか継承した型を指定してください[field=%s / LyceeArg#value=%s]",
							fieldType.getName(), containerType.getName()));
		}
		if(!ClassUtil.isImplementationClass(containerType)) {
			throw new LyceeRuntimeException(
					String.format("LyceeArg#valueにインタフェイスクラスや抽象クラスを指定することはできません[LyceeArg#value=%s]", containerType.getName()));
		}
	}

	@Nonnull
	public static Class<?> getActualFieldType(@Nonnull final Field field) {
		final Class<?> fieldType = field.getType();
		if(ClassUtil.isImplementationClass(fieldType)) {
			return fieldType;
		}
		final LyceeArg anno = field.getDeclaredAnnotation(LyceeArg.class);
		if(anno==null) {
			throw new LyceeRuntimeException(
					String.format("fieldにLyceeArgがついてません[field=%s]", field.getName()));
		}
		verifyClassAndInheritance(anno.value(), field.getType());
		return anno.value();
	}

	/**
	 * <b>フィールドの型が指定されたクラスを継承しているか検証するメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param field 検証対象フィールド
	 * @param parent 親クラス
	 * @return 検証対象フィールドの型が親クラスを継承している場合TRUEを返す
	 */
	@Nonnull
	public static Field verifyField(@Nonnull final Field field, @Nonnull final Class<?> parent) {
		if(!ClassUtil.isParent(field.getType(), parent)) {
			throw new LyceeRuntimeException(
					String.format("%s型のフィールドではありません[field=%s]", parent.getName(), field.getName()));
		}
		return field;
	}

	/**
	 * <b>フィールドのインスタンス取得</b>
	 * <p>
	 * インスタンスがまだ生成されていない場合は、アノテーションで定義された型でインスタンスを生成する。
	 * </p>
	 *
	 * @param obj 取得したいフィールドを所有するオブジェクト
	 * @return インスタンス
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Nonnull
	public static Object getFieldObject(final Field field, final Object obj, final Class<?> actualContainerType) throws IllegalArgumentException, IllegalAccessException {
		final Object target = field.get(obj);
		if(target!=null) {
			// フィールドから取得できれば、それを返却する
			return target;
		}
		return ClassUtil.newInstance(actualContainerType);

	}

}
