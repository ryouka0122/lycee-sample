package net.coolblossom.lycee.core.sample;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <b>Streamのサンプル集</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class SampleStream {

	/**
	 * <b>エントリポイント</b>
	 *
	 * @param args
	 */
	public static void main(final String[] args) {
		// 対象となる配列
		final int[] intAry = new int[]{
				5, 2, 6, 3, 7, 4, 9, 7, 0, 1, 2
		};
		printIntArray("初期値", intAry);

		// int -> String への変換
		// intはプリミティブ型で，Stringはオブジェクト型
		// そのため，int -> String への変換はmapToObjを使います．
		// その結果を配列にしたいので，toArrayを使ってStringの配列を取得します．
		final String[] strAry = Arrays.stream(intAry)
				.mapToObj(i -> String.valueOf(i)).toArray(String[]::new);
		printStrArray("Stringに変換した結果", strAry);

		// int配列の昇順
		// 昇順は，sortedを挟むとできます．
		final int[] intAscAry = Arrays.stream(intAry).sorted().toArray();
		printIntArray("昇順に並び替えた結果", intAscAry);

		// Streamを使って，合計値を出す
		System.out.println("合計値を出す");
		final int intSum = Arrays.stream(intAry)
				.reduce(0, (a, b) -> {
					// 途中結果を出力
					System.out.println(String.format("a=%d / b=%d", a, b));
					return a + b;
				});
		System.out.println("合計値 : " + intSum);

		// String配列 -> Stringリスト への変換
		final List<String> strList = Arrays.stream(strAry).collect(Collectors.toList());
		System.out.println("一行書き : " + strList.stream().collect(Collectors.joining(", ")));

		// 偶数だけ出力する方法
		final String strComma = Arrays.stream(intAry)
				.filter(i -> i%2==0)					// 偶数だけ次の処理に渡す（フィルタリング）
				.mapToObj(String::valueOf)				// 文字列に変換
				.collect(Collectors.joining(", "));		// カンマ区切りの文字列にする
		System.out.println("偶数だけ出力 : " + strComma);



	}

	/**
	 * <b>カンマ区切りで標準出力するメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param label ラベル
	 * @param ary 出力したい配列
	 */
	private static void printIntArray(final String label, final int[] ary) {
		// aryをStreamを使ってカンマ区切りの文字列にさせる
		final String result = Arrays.stream(ary)
				.mapToObj(String::valueOf)				// Collectors.joiningを使うには，Stringにしておく必要がある．（メソッド参照での書き方）
				.collect(Collectors.joining(", "));
		System.out.println(label + " : " + result);
	}

	/**
	 * <b>カンマ区切りで標準出力するメソッド</b>
	 * <p>
	 * </p>
	 *
	 * @param label ラベル
	 * @param ary 出力したい配列
	 */
	private static void printStrArray(final String label, final String[] ary) {
		// aryをStreamを使ってカンマ区切りの文字列にさせる
		final String result = Arrays.stream(ary)
				.collect(Collectors.joining(", "));
		System.out.println(label + " : " + result);
	}


}
