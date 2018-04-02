package net.coolblossom.lycee.core.args.mappers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.annotations.LyceeArgClass;
import net.coolblossom.lycee.core.args.descriptors.FieldDescriptor;
import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.LyceeArgsUtil;

/**
 * <b>記述子を管理するクラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class LyceeArgsProfile {

	@Nonnull
	private final List<FieldDescriptor> descriptors;

	private FieldDescriptor defaultDescriptor;

	/**
	 * コンストラクタ
	 * @param target
	 */
	public LyceeArgsProfile(@Nonnull final Class<?> clazz) {
		this(clazz, new ArrayList<String>());
	}

	/**
	 * コンストラクタ
	 * @param target 対象オブジェクト
	 * @param ignoreList
	 */
	public LyceeArgsProfile(@Nonnull final Class<?> clazz, final List<String> ignoreList) {
		Stream<Field> fieldStream = Stream
				.of(clazz.getDeclaredFields())
				.filter(field -> !ignoreList.contains(field.getName()))
				.filter(this::checkAccessor);

		if( !clazz.isAnnotationPresent(LyceeArgClass.class) ) {
			fieldStream = fieldStream.filter(f -> f.isAnnotationPresent(LyceeArg.class));
		}
		final List<FieldDescriptor> descriptorList = fieldStream
				.peek(f->f.setAccessible(true))
				.map(LyceeArgsUtil::createDescriptor)
				.collect(Collectors.toList());

		descriptors = new ArrayList<>();

		for(final FieldDescriptor desc : descriptorList) {
			if(desc.isDefault()) {
				if(defaultDescriptor != null) {
					throw new LyceeRuntimeException(String.format(
							"デフォルト指定は1か所のみです.[指定済みフィールド=%s]", defaultDescriptor.getName()));
				}
				defaultDescriptor = desc;
			}else {
				descriptors.add(desc);
			}
		}

		if( descriptors.isEmpty() && defaultDescriptor!=null ) {
			descriptors.add(defaultDescriptor);
			defaultDescriptor = null;
		}
	}

	public boolean isEmpty() {
		return descriptors.isEmpty();
	}

	public boolean set(final Object target, final String key, final String value)
			throws IllegalArgumentException, IllegalAccessException {
		for(final FieldDescriptor desc : descriptors) {
			if(desc.set(target, key, value)) {
				return true;
			}
		}
		return (defaultDescriptor!=null ? defaultDescriptor.set(target, key, value) : false);
	}


	/**
	 *
	 * <b>マッピングできるフィールドかチェックするメソッド</b>
	 * <p>
	 * マッピング可能フィールド：publicかprotectedであること かつ finalが付与されていないこと
	 * </p>
	 *
	 * @param field 検証フィールド
	 * @return マッピング可能の場合TRUEを返す
	 */
	private boolean checkAccessor(@Nonnull final Field field) {
		final int mod = field.getModifiers();
		return (Modifier.isPublic(mod) || Modifier.isProtected(mod))
				&& !Modifier.isFinal(mod);
	}

}
