package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.convertors.Convertor;
import net.coolblossom.lycee.core.args.convertors.ConvertorFactory;
import net.coolblossom.lycee.core.args.utils.StringUtil;

public abstract class FieldDescriptor {

	@Nonnull
	protected Field field;

	@Nonnull
	protected Convertor convertor;

	@Nonnull
	protected List<String> matchingNameList;


	protected FieldDescriptor(@Nonnull final Field field, @Nonnull final Class<?> type) {
		this.field = field;
		convertor = getConvertor(type, field.getDeclaredAnnotation(LyceeArg.class));
		matchingNameList = makeNameList();
	}


	abstract public void set(@Nonnull Object obj, @Nullable String value);


	@Nonnull
	private Convertor getConvertor(@Nonnull final Class<?> clazz, @Nullable final LyceeArg lyceeArg) {
		final ConvertorFactory factory = ConvertorFactory.getInstance();
		return factory.createConvertor(clazz, lyceeArg);
	}

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
