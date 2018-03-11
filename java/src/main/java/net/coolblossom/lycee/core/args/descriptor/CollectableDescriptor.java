package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.annotations.LyceeArgCollection;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

/**
 *
 * <b>Collection型やMap型のフィールドに対する記述子</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public abstract class CollectableDescriptor extends FieldDescriptor {

	protected LyceeArgCollection annoCollection;

	protected CollectableDescriptor(@Nonnull final Field field, @Nonnull final Class<?> actualType) {
		super(field, actualType);
		annoCollection = field.getDeclaredAnnotation(LyceeArgCollection.class);
		if(annoCollection!=null) {
			verifyClassInheritance(annoCollection.value(), field.getType());
		}
	}

	/**
	 * <b>フィールドの型とアノテーションの型の継承チェック</b>
	 * <p>
	 * </p>
	 *
	 * @param containerType アノテーションの型
	 * @param fieldType フィールドの型
	 */
	private void verifyClassInheritance(@Nullable final Class<?> containerType, @Nonnull final Class<?> fieldType) {
		if(containerType==null || containerType.equals(Object.class)) {
			throw new LyceeRuntimeException(
					String.format("LyceeArgCollectionのcontainerにインスタンス生成時の型を指定してください[field=%s]" ,field.getName()));
		}
		if(!ClassUtil.isParent(containerType, fieldType)) {
			throw new LyceeRuntimeException(
					String.format("LyceeArgCollection#containerにはフィールドの型と同じか継承した型を指定してください[field=%s / LyceeArgCollection#container=%s]",
							fieldType.getName(), containerType.getClass()));
		}
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
	protected Object getFieldObject(final Object obj) throws IllegalArgumentException, IllegalAccessException {
		final Object target = field.get(obj);
		if(target!=null) {
			return target;
		}
		if(annoCollection==null) {
			throw new LyceeRuntimeException(
					String.format("fieldにLyceeArgCollectionがついてません[field=%s]", field.getName()));
		}

		final Class<?> containerType = annoCollection.value();
		return ClassUtil.newInstance(containerType);
	}

}
