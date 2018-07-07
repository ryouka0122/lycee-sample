package net.coolblossom.lycee.core.sample;

/**
 * <b>猫のモデル</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class Cat extends AnimalPet {

	public Cat(final String name, final int age) {
		super(name, age);
	}

	@Override
	public void say() {
		System.out.println("にゃー");
	}

	@Override
	public void feed() {
		System.out.println("キャットフードを食べるよ");
	}

	@Override
	public boolean canSwim() {
		// 泳がない
		return false;
	}

	@Override
	public boolean canFly() {
		// 飛ばない
		return false;
	}

}