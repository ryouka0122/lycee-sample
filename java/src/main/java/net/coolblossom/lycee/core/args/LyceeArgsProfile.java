package net.coolblossom.lycee.core.args;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.annotations.LyceeArgClass;
import net.coolblossom.lycee.core.args.descriptors.ArrayDescriptor;
import net.coolblossom.lycee.core.args.descriptors.ClassObjectDescriptor;
import net.coolblossom.lycee.core.args.descriptors.DateDescriptor;
import net.coolblossom.lycee.core.args.descriptors.EnumDescriptor;
import net.coolblossom.lycee.core.args.descriptors.FieldDescriptor;
import net.coolblossom.lycee.core.args.descriptors.PrimitiveDescriptor;
import net.coolblossom.lycee.core.args.descriptors.SilentDescriptor;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

/**
 * 紐づけクラス
 * @author ryouka
 *
 */
public class LyceeArgsProfile {

	/** keyとなる文字列のパターン */
	private static final String DEFAULT_KEY_FORMAT_STRING = "^--?([A-Za-z_][A-Za-z0-9_]+)$";

	public LyceeArgsProfile() {
	}


	@Nonnull
	@SuppressWarnings("null")
	public <T> T createAndBind(@Nonnull final Class<T> clazz, @Nonnull final String[] args) {
		try {
			return bind(clazz.newInstance(), args);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new LyceeRuntimeException("インスタンスの生成に失敗しました", e);
		}
	}

	/**
	 *
	 * <b>bind</b>
	 * <p>
	 * </p>
	 *
	 * @param object
	 * @param args
	 * @return
	 */
	@Nonnull
	@SuppressWarnings("null")
	public <T> T bind(@Nonnull final T object, @Nonnull final String[] args) {
		if(args.length<2) {
			throw new LyceeRuntimeException("argsは2つ以上のStringの配列でないといけません");
		}

		final Class<?> clazz = object.getClass();
		final List<FieldDescriptor> validField = makeArgFieldList(clazz);

		if( validField==null || validField.isEmpty() ) {
			// 該当フィールドがないとき
			return object;
		}

		final FieldDescriptor defaultField = pickupDefaultField(validField);

		for(int ki = 0, vi=1 ; ki<args.length-1 ; ki++, vi++) {
			final String key = args[ki];
			final String value = args[vi];
			// TODO: アノテーションから指定できるようにするとお手軽感が向上する
			if( !Pattern.matches(DEFAULT_KEY_FORMAT_STRING, key) ) {
				continue;
			}

			boolean finishFieldBound = false;
			for(final FieldDescriptor desc : validField) {
				if(desc.checkFieldName(key)) {
					desc.bind(object, value);
					finishFieldBound = true;
					break;
				}
			}
			if(!finishFieldBound) {
				defaultField.bind(object, value);
			}
		}
		return object;

	}


	/**
	 * <b>pickupDefaultField</b>
	 * <p>
	 * LyceeDefaultArgがついているフィールドを見つけるメソッド
	 * </p>
	 *
	 * @param validField
	 * @return
	 */
	private FieldDescriptor pickupDefaultField(final List<FieldDescriptor> validField) {
		final List<FieldDescriptor> defaults = validField.stream()
				.filter(f -> f.isDefaultField())
				.collect(Collectors.toList());
		final int listSize = defaults.size();
		if(listSize>1) {
			throw new LyceeRuntimeException("LyceeDefaultArgを複数フィールドに指定することができません");
		}
		if(listSize!=0) {
			final FieldDescriptor desc = defaults.get(0);
			final Class<?> clazz = desc.getFieldType();
			if(clazz.isArray() || ClassUtil.isParent(clazz, Collection.class)) {
				return desc;
			}
		}
		return new SilentDescriptor();
	}

	/**
	 * 紐づけが有効なフィールドのリストを生成
	 * @param clazz
	 * @return
	 */
	@Nullable
	private List<FieldDescriptor> makeArgFieldList(@Nonnull final Class<?> clazz) {
		if(clazz.isAnnotationPresent(LyceeArgClass.class)) {
			return Arrays.stream(clazz.getDeclaredFields())
					.map(this::makeDescriptor)
					.collect(Collectors.toList());
		}
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(f -> f.isAnnotationPresent(LyceeArg.class))
				.filter(this::enableModifier)
				.map(this::makeDescriptor)
				.collect(Collectors.toList());
	}

	/**
	 * <b>紐づけ可能かチェックするメソッド</b>
	 * @return publicかprotectedで、finalではないフィールドが可能
	 */
	private boolean enableModifier(final Field field) {
		final int mod = field.getModifiers();
		return (Modifier.isPublic(mod) || Modifier.isProtected(mod))
				&& !Modifier.isFinal(mod);
	}

	/**
	 * フィールド記述子の生成
	 * @param field 対象フィールド
	 * @return 対象フィールドの型に合わせたフィールド記述子
	 */
	@Nonnull
	@SuppressWarnings("null")
	protected FieldDescriptor makeDescriptor(@Nonnull final Field field) {
		final @Nonnull Class<?> clazz = field.getType();
		if(isCollectionLikeType(clazz)) {
			throw new LyceeRuntimeException("コレクション型やMap型にLyceeArgを指定することはできません");
		}
		if(clazz.isArray()) {
			// 配列型の時
			return new ArrayDescriptor(field);
		}else if(clazz.isPrimitive()) {
			// プリミティブ型の時
			return new PrimitiveDescriptor(field);
		}else if(clazz.isEnum()) {
			// 列挙型の時
			return new EnumDescriptor(field);
		}else if(java.util.Date.class.equals(clazz)) {
			// java.util.Date型の時
			return new DateDescriptor(field);
		}else {
			// それ以外（オブジェクト型）の時
			return new ClassObjectDescriptor(field);
		}
	}

	/**
	 *
	 * <b>isCollectionType</b>
	 * <p>
	 * コレクション型かMap型の場合trueを返すメソッド
	 * </p>
	 *
	 * @param clazz
	 * @return
	 */
	private boolean isCollectionLikeType(@Nonnull final Class<?> clazz) {
		return (ClassUtil.isParent(clazz, Collection.class)
				|| ClassUtil.isParent(clazz, Map.class));
	}
}
