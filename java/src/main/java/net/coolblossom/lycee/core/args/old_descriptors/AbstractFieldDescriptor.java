package net.coolblossom.lycee.core.args.old_descriptors;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.utils.StringUtil;

/**
 * フィールドの情報を管理するクラス
 *
 * @author ryouka
 *
 */
public abstract class AbstractFieldDescriptor implements FieldDescriptor {

	@Nonnull
	protected Field field;

	public AbstractFieldDescriptor(@Nonnull final Field field) {
		this.field = field;
	}

	@Override
	@Nonnull
	@SuppressWarnings("null")
	public Class<?> getFieldType() {
		return field.getType();
	}

	@Override
	@Nonnull
	@SuppressWarnings("null")
	public String getName() {
		final LyceeArg anno = field.getDeclaredAnnotation(LyceeArg.class);
		if(anno!=null && !StringUtil.isEmpty(anno.name())) {
			return anno.name();
		}
		return field.getName();
	}

	/**
	 * <b>checkFieldName</b>
	 * <p>
	 * 名称を確認するメソッド
	 * </p>
	 *
	 * @param key 確認する名称
	 * @return
	 */
	@Override
	public boolean checkFieldName(final @Nonnull String key) {
		String keyName = "";
		// FIXME: 正規表現使えばもっとよさげになりそう
		if( key.startsWith("--")) {
			keyName = key.substring(2);
		}else if(key.startsWith("-")) {
			keyName = key.substring(1);
		}
		return getName().equals(keyName);
	}

	/**
	 * デフォルト指定されているフィールドであるかを確認するメソッド
	 * @return LyceeArgのdefaultsにtrueが指定されている場合trueを返す
	 */
	/**
	@Override
	public boolean isDefaultField() {
		return false;
		final LyceeArg anno = field.getDeclaredAnnotation(LyceeArg.class);
		return anno!=null ? anno.defaults() : false;
	}
	 */

}
