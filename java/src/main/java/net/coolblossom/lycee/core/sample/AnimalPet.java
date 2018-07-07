package net.coolblossom.lycee.core.sample;

/**
 * <b>ペット（動物）の概念モデル</b>
 * <p>
 * 動物に共通の動作や情報を取り扱うクラス
 * </p>
 * @author ryouka
 *
 */
public abstract class AnimalPet {
	/** 名前 */
	private final String name;

	/** 年齢 */
	private final int age;

	/**
	 * <b>コンストラクタ</b>
	 * <p>
	 * 抽象クラスは概念なので実体が作れないけど，
	 * 情報は実体化するときに共通でもっているものなので，
	 * コンストラクタで設定しておきます．
	 * </p>
	 * @param name ペットの名前
	 * @param age ペットの年齢
	 */
	protected AnimalPet(final String name, final int age) {
		this.name = name;
		this.age = age;
	}

	/**
	 * <b>名前の取得</b>
	 * @return 名前
	 */
	public String getName() {
		return name;
	}

	/**
	 * <b>年齢の取得</b>
	 * @return 年齢
	 */
	public int getAge() {
		return age;
	}

	/**
	 * <b>情報の出力</b>
	 * <p>
	 * 共通的な処理は抽象クラスに作る．
	 * </p>
	 */
	public void printInfo() {
		System.out.println(String.format("{name='%s', age=%d}", name, age));
	}

	/** [抽象的な動作] 鳴く */
	abstract public void say();

	/** [抽象的な動作] 食べる */
	abstract public void feed();

	/** [抽象的な動作] 泳げる？ */
	abstract public boolean canSwim();

	/** [抽象的な動作] 飛べる？ */
	abstract public boolean canFly();
}