package net.coolblossom.lycee.core.args.descriptors;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.annotations.LyceeArgCollection;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.ClassUtil;
import net.coolblossom.lycee.core.args.utils.LyceeArgsUtil;

/**
 *
 * <b>Collection型やMap型のフィールドに対する記述子</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public abstract class CollectableDescriptor extends FieldDescriptor {

	/** フィールドに付与されているLyceeArgCollectionアノテーション */
	@Nonnull
	protected LyceeArgCollection annoCollection;

	@Nonnull
	protected Class<?> actualContainerType;

	/**
	 * コンストラクタ
	 * @param field
	 * @param actualType
	 */
	protected CollectableDescriptor(@Nonnull final Field field, @Nonnull final Class<?> actualType) {
		super(field, actualType);
		final LyceeArgCollection anno = field.getDeclaredAnnotation(LyceeArgCollection.class);
		if(anno==null) {
			throw new LyceeRuntimeException(
					String.format("fieldにLyceeArgCollectionがついてません[field=%s]", field.getName()));
		}
		LyceeArgsUtil.verifyClassAndInheritance(anno.value(), field.getType());
		annoCollection = anno;
		actualContainerType = anno.value();
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
			// フィールドから取得できれば、それを返却する
			return target;
		}
		return ClassUtil.newInstance(actualContainerType);

	}

}
