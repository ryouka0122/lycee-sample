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
import net.coolblossom.lycee.core.args.descriptor.FieldDescriptor;
import net.coolblossom.lycee.core.args.utils.LyceeArgsUtil;

/**
 * <b>LyceeArgsのマッピング処理クラス</b>
 * <p>
 * LyceeArgsのマッピング処理を行う実クラス
 * </p>
 * @author ryouka
 *
 * @param <T> マッピングしたい型
 */
public class LyceeArgsMapExecutor<T> {
	/** ロガー */
	private static Logger logger = Logger.getLogger(LyceeArgsMapExecutor.class);

	/** keyとなる文字列の正規表現 */
	private static final String DEFAULT_KEY_FORMAT_STRING = "^--?([A-Za-z_][A-Za-z0-9_]+)$";

	/** keyとなる文字列パターン */
	private static final Pattern KEY_FORMAT_PATTERN = Pattern.compile(DEFAULT_KEY_FORMAT_STRING);


	/** マッピング先のインスタンス */
	@Nonnull
	private final T target;

	/** マッピング元となる実行引数のリスト */
	@Nonnull
	private final String args[];


	/**
	 * コンストラクタ
	 * @param target マッピング先インスタンス
	 * @param args 実行引数
	 */
	public LyceeArgsMapExecutor(@Nonnull final T target, @Nonnull final String args[]) {
		this.target = target;
		this.args = args;
	}

	/**
	 *
	 * <b>マッピング処理</b>
	 * <p>
	 * </p>
	 *
	 * @return マッピングされたインスタンス
	 */
	public T execute() {

		// マッピング対象フィールドの一覧生成
		final List<FieldDescriptor> descriptors = createDescriptorList();
		if(descriptors.isEmpty()) {
			return target;
		}
		// マッピングさせるキーのフォーマット
		final Pattern keyPattern = Pattern.compile(DEFAULT_KEY_FORMAT_STRING);

		for(int ki=0, vi=1 ; vi<args.length ; ki++, vi++) {
			final String key = args[ki];
			final String value = args[vi];

			logger.info(String.format("verify %s=%s", key, value));

			final Matcher matcher = keyPattern.matcher(key);
			if(!matcher.find()) {
				// フォーマットに合致しない場合は次に進む
				continue;
			}

			final String trimmedKey = matcher.group(1);
			if(trimmedKey==null) {
				throw new NullPointerException();
			}
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

	/**
	 * <b>フィールド記述子のリストを生成するメソッド</b>
	 * <p>
	 *  ここで、生成されたリストがマッピング処理の要となる。
	 * </p>
	 *
	 * @return フィールド記述子のリスト
	 */
	private List<FieldDescriptor> createDescriptorList() {
		Stream<Field> fieldStream = Stream
				.of(target.getClass().getDeclaredFields())
				.filter(this::checkAccessor);

		if( !target.getClass().isAnnotationPresent(LyceeArgClass.class) ) {
			fieldStream = fieldStream.filter(f -> f.isAnnotationPresent(LyceeArg.class));
		}

		return fieldStream
				.peek(f->f.setAccessible(true))
				.map(LyceeArgsUtil::createDescriptor)
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

}
