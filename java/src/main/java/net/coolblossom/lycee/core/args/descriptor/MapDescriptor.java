package net.coolblossom.lycee.core.args.descriptor;

import java.lang.reflect.Field;
import java.util.Map;

import javax.annotation.Nonnull;

import net.coolblossom.lycee.core.args.exceptions.LyceeRuntimeException;
import net.coolblossom.lycee.core.args.utils.ClassUtil;

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
		super(verifyField(field), actualType);
	}

	@Nonnull
	private static Field verifyField(@Nonnull final Field field) {
		if( !ClassUtil.isParent(field.getType(), Map.class)) {
			throw new LyceeRuntimeException(
					String.format("Map型のフィールドではありません[field=%s]",field.getName()));
		}
		return field;
	}

	@Override
	public void set(final Object obj, final String value) {
		try {
			// TODO マッピング処理未実装
			final Map map = (Map) field.get(obj);
			throw new LyceeRuntimeException("Map型の処理は未実装です");
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new LyceeRuntimeException(e);
		}
	}

}
