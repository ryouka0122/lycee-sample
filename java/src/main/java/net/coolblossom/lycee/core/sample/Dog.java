package net.coolblossom.lycee.core.sample;

/**
 * <b>犬のモデル</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class Dog extends AnimalPet {

	public Dog(final String name, final int age) {
		super(name, age);
	}

	@Override
	public void say() {
		System.out.println("ワンワン");
	}

	@Override
	public void feed() {
		System.out.println("ドックフードを食べるよ");
	}

	@Override
	public boolean canSwim() {
		// 泳げるよ
		return true;
	}

	@Override
	public boolean canFly() {
		// 飛べない
		return false;
	}


}