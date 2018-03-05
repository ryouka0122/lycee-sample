package net.coolblossom.lycee.core.args.mappers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import javassist.Modifier;
import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.annotations.LyceeArgClass;
import net.coolblossom.lycee.core.args.convertors.Convertor;
import net.coolblossom.lycee.core.args.convertors.ConvertorFactory;
import net.coolblossom.lycee.core.args.descriptor.FieldDescriptor;

public class LyceeBinder<T> {

	private static Logger logger = Logger.getLogger(LyceeBinder.class);

	/** keyとなる文字列の正規表現 */
	private static final String DEFAULT_KEY_FORMAT_STRING = "^--?([A-Za-z_][A-Za-z0-9_]+)$";

	/** keyとなる文字列パターン */
	private static final Pattern KEY_FORMAT_PATTERN = Pattern.compile(DEFAULT_KEY_FORMAT_STRING);


	@Nonnull
	private final T target;

	@Nonnull
	private final String args[];

	@Nonnull
	private final ConvertorFactory convertorFactory;

	public LyceeBinder(@Nonnull final T target, @Nonnull final String args[]) {
		this.target = target;
		this.args = args;
		this.convertorFactory = ConvertorFactory.createInstance();
	}

	public T execute() {

		final List<FieldDescriptor> descriptors = createDescriptorList();
		if(descriptors.isEmpty()) {
			return target;
		}

		final Pattern keyPattern = Pattern.compile(DEFAULT_KEY_FORMAT_STRING);
		for(int ki=0, vi=1 ; vi<args.length ; ki++, vi++) {
			final String key = args[ki];
			final String value = args[vi];

			logger.info(String.format("verify %s=%s", key, value));

			final Matcher matcher = keyPattern.matcher(key);
			if(!matcher.find()) {
				continue;
			}

			final String trimmedKey = matcher.group(1);
			for(final FieldDescriptor desc : descriptors) {
				if(desc.matches(trimmedKey)) {
					logger.info(String.format("map %s(%s)=%s", key, trimmedKey, value));
					desc.set(target, value);
					ki++;
					vi++;
					break;
				}
			}

		}
		return target;
	}

	private List<FieldDescriptor> createDescriptorList() {
		Stream<Field> fieldStream = Stream
				.of(target.getClass().getDeclaredFields())
				.filter(this::checkAccessor);

		if( !target.getClass().isAnnotationPresent(LyceeArgClass.class) ) {
			fieldStream = fieldStream.filter(f -> f.isAnnotationPresent(LyceeArg.class));
		}

		return fieldStream
				.peek(f->f.setAccessible(true))
				.map(this::createDescriptor)
				.collect(Collectors.toList());
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

	/**
	 *
	 * <b>FieldからFieldDescriptorを生成するメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param field 対象フィールド
	 * @return {@link FieldDescriptor}のインスタンス
	 */
	@Nonnull
	private FieldDescriptor createDescriptor(@Nonnull final Field field) {
		@Nonnull
		Class<?> fieldType = field.getType();
		if(fieldType.isArray()) {
			fieldType = fieldType.getComponentType();
		}
		final Convertor convertor = convertorFactory.createConvertor(fieldType, field.getDeclaredAnnotation(LyceeArg.class));
		logger.info("field="+field.getName() + " / convertor=" + convertor);
		return new FieldDescriptor(field, convertor);
	}



}
