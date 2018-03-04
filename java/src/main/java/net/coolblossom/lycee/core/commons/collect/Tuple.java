package net.coolblossom.lycee.core.commons.collect;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.coolblossom.lycee.core.commons.iterators.ArrayIterator;

/**
 * Tuple型
 * @author ryouka
 *
 */
public class Tuple implements Iterable<Object> {
	public static final Collector<CharSequence, ?, String> TUPLE_COLLECTOR = Collectors.joining(", ", "(", ")");

	/**
	 * Generator
	 * @param objects Tupleで管理するデータ
	 * @return Tupleオブジェクト
	 */
	public static Tuple make(final Object ...objects) {
		return new Tuple(objects);
	}

	/** Tupleデータ */
	private final Object[] data;

	/**
	 * コンストラクタ
	 * @param objects
	 */
	public Tuple(final Object ...objects) {
		data = Objects.requireNonNull(objects);
	}

	/**
	 * データの取得
	 * @param index インデックス
	 * @return 型変換後のオブジェクト
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(final int index) {
		return (T) data[index];
	}

	/**
	 * データの取得（型不一致時のデフォルト値付き）
	 * @param index インデックス
	 * @param defValue デフォルト値
	 * @return 型変換後のオブジェクト
	 */
	@SuppressWarnings("unchecked")
	public <T> T getIfBadCast(final int index, final T defValue) {
		return check(index, defValue.getClass()) ? (T) data[index] : defValue;
	}

	/**
	 * 型チェック
	 * @param index インデックス
	 * @param clazz 型
	 * @return 変換可能な場合trueを返す
	 */
	public <T> boolean check(final int index, final Class<T> clazz) {
		return clazz.isInstance(data[index]);
	}

	/**
	 * データ数
	 * @return サイズ
	 */
	public int size() {
		return data.length;
	}

	/**
	 * データの型のリストを取得
	 * @return 型のリスト
	 */
	public List<Class<?>> getTypeList() {
		return Arrays.stream(data).map(o->o.getClass()).collect(Collectors.toList());
	}

	/**
	 * Iteratorの取得
	 * @return
	 */
	@Override
	public Iterator<Object> iterator() {
		return ArrayIterator.make(data, 0, data.length);
	}

	@Override
	public boolean equals(final Object obj) {
		if(this==obj) {
			return true;
		}
		if( !(obj instanceof Tuple) ) {
			return false;
		}
		final Tuple rhs = (Tuple) obj;
		if(data.length!=rhs.data.length) {
			return false;
		}
		return IntStream.range(0, data.length)
				.mapToObj(i -> data[i].equals(rhs.data[i]))
				.reduce(true, Boolean::logicalAnd);
	}

	@Override
	public String toString() {
		return Arrays.stream(data).map(o->o.toString()).collect(TUPLE_COLLECTOR);
	}

}