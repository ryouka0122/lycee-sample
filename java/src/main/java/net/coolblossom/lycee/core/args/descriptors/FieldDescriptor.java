package net.coolblossom.lycee.core.args.descriptors;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.convertors.Convertor;
import net.coolblossom.lycee.core.args.utils.LyceeArgsUtil;
import net.coolblossom.lycee.core.args.utils.StringUtil;

/**
 * <b>フィールド記述子</b>
 * <p>
 * マッピングさせるフィールドの特性を識別し、適切に値を設定するためのクラス
 * </p>
 * @author ryouka
 *
 */
public abstract class FieldDescriptor {

	/** マッピングさせるフィールド */
	@Nonnull
	protected Field field;

	/** マッピングする際の変換処理クラス */
	@Nonnull
	protected Convertor convertor;

	/** マッピング用名称リスト（ここにある名前と同じ場合マッピングされる） */
	@Nonnull
	protected List<String> matchingNameList;

	/**
	 * コンストラクタ
	 * @param field マッピング対象フィールド
	 * @param type フィールドの型クラス（配列の場合配列となっているクラス、ジェネリック型の場合、型パラメータとなっている実クラスの型）
	 */
	protected FieldDescriptor(@Nonnull final Field field, @Nonnull final Class<?> type) {
		this.field = field;
		convertor = LyceeArgsUtil.createConvertor(type, field.getDeclaredAnnotation(LyceeArg.class));
		matchingNameList = makeNameList();
	}

	/**
	 * <b>値をセットするメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param obj セットする対象オブジェクト
	 * @param value セットする値
	 */
	abstract public void set(@Nonnull Object obj, @Nullable String value);

	/**
	 *
	 * <b>キーとフィールド名がマッチしているか確認するメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param key キー
	 * @return マッチしていればTRUEを返す
	 */
	public boolean matches(@Nonnull final String key) {
		return matchingNameList.contains(key);
	}

	/**
	 *
	 * <b>フィールドの名称リストを生成</b>
	 * <p>
	 * LyceeArgが付与されている場合、それを優先して名称を指定する。<br>
	 * もし、LyceeArgが付与されていなければ、フィールド名を使用する。<br>
	 * LyceeArg#aliasに指定されていれば、別名として名称リストに追加する<br>
	 * </p>
	 *
	 * @return 名称リスト
	 */
	@Nonnull
	private List<String> makeNameList() {
		final List<String> result = new ArrayList<>();

		String name = field.getName();
		if(field.isAnnotationPresent(LyceeArg.class)) {
			final LyceeArg lyceeArg = field.getDeclaredAnnotation(LyceeArg.class);
			if( !StringUtil.isEmpty(lyceeArg.name())) {
				name=lyceeArg.name();
			}

			if( !StringUtil.isEmpty(lyceeArg.alias()) ) {
				result.add(lyceeArg.alias());
			}
		}
		result.add(name);
		return result;
	}
}
