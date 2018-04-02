package net.coolblossom.lycee.core.args.mappers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;

/**
 * <b>LyceeArgsのマッピング処理クラス</b>
 * <p>
 * LyceeArgsのマッピング処理を行う実クラス
 * </p>
 * @author ryouka
 *
 * @param <T> マッピングしたい型
 */
public class LyceeArgsMapper<T> {
	/** ロガー */
	private static Logger logger = Logger.getLogger(LyceeArgsMapper.class);

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
	public LyceeArgsMapper(@Nonnull final T target, @Nullable final String args[]) {
		this.target = target;
		if(args==null || args.length==0) {
			throw new LyceeRuntimeException("実行引数にnullや空配列を指定することはできません");
		}
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
		final LyceeArgsProfile profile = new LyceeArgsProfile(target.getClass());

		// マッピング対象フィールドの確認
		if(profile.isEmpty()) {
			return target;
		}

		// マッピングさせるキーのフォーマット
		final Pattern keyPattern = Pattern.compile(DEFAULT_KEY_FORMAT_STRING);
		for(int ki=0, vi=1 ; vi<args.length ; ki++, vi++) {
			final String key = args[ki];
			final String value = args[vi];

			if(key==null || value==null) {
				throw new IllegalArgumentException("argsにはnullの値を使用することができません。");
			}

			logger.info(String.format("verify %s=%s", key, value));

			final Matcher matcher = keyPattern.matcher(key);
			if(!matcher.find()) {
				// フォーマットに合致しない場合は次に進む
				continue;
			}

			final String trimmedKey = matcher.group(1);
			if(trimmedKey==null) {
				// フォーマットが指定できるようになるとこのケースが発生する
				throw new NullPointerException();
			}

			try {
				if(profile.set(target, trimmedKey, value)) {
					logger.info(String.format("map %s(%s)=%s", key, trimmedKey, value));
					ki++;
					vi++;
				}
			}catch(final IllegalArgumentException | IllegalAccessException e) {
				throw new LyceeRuntimeException(
						String.format("オブジェクトへのマッピングに失敗しました[key=%s/value=%s]", key, value),
						e);
			}
		}
		return target;
	}

}
