package net.coolblossom.lycee.core.sample;

/**
 * <b>鳥のモデル</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class Bird extends AnimalPet {

	/**
	 * コンストラクタ
	 * @param name 名前
	 * @param age 年齢
	 */
	public Bird(final String name, final int age) {
		super(name, age);
	}

	@Override
	public void say() {
		System.out.println("ピーピーピー");
	}

	@Override
	public void feed() {
		System.out.println("種を食べるよ");
	}
	@Override
	public boolean canSwim() {
		// 泳がない
		return false;
	}

	@Override
	public boolean canFly() {
		// 飛べる
		return true;
	}

}