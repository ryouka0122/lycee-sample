package net.coolblossom.lycee.core.args.descriptors;

import java.lang.reflect.Field;
import java.util.Map;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.LyceeArgsUtil;

/**
 * <b>マップ用記述子</b>
 * <p>
 * 他の記述子とは違い、名称一致の処理は無視されます。<br>
 * また、変換には同じものを使用するため、String型を使用することをお勧めします。<br>
 * <br>
 * MapをLyceeArgsで使用するには、以下の条件が存在します。<br>
 * <li>LyceeArgCollectionが付与されていること</li>
 * 次の条件のいずれかは該当すること
 * <li>他フィールドが存在しない</li>
 * <li>デフォルト設定になっていること</li>
 * </p>
 * @author ryouka
 *
 */
public class MapDescriptor extends CollectableDescriptor {

	/**
	 * コンストラクタ
	 * @param field 対象フィールド
	 * @param actualType Map型の2つ目の型パラメータの型クラス
	 */
	public MapDescriptor(@Nonnull final Field field, @Nonnull final Class<?> actualType) {
		super(field, actualType);
	}

	@Override
	protected Field verifyField(final Field field) {
		return LyceeArgsUtil.verifyField(field, Map.class);
	}


	@Override
	public boolean matches(@Nonnull final String key) {
		// Map型はすべて許容する
		return true;
	}

	@Override
	public boolean set(@Nonnull final Object obj, @Nonnull final String key, @Nonnull final String value)
			throws IllegalArgumentException, IllegalAccessException {
		final Map map = (Map) getFieldObject(obj);
		map.put(key, convertor.convert(value));
		field.set(obj, map);
		return true;
	}


	@Override
	public void setValue(@Nonnull final Object obj, @Nonnull final String value)
			throws IllegalArgumentException, IllegalAccessException {
		throw new LyceeRuntimeException("MapDescritptor#setValue()は使用できません");
	}

}
