package net.coolblossom.lycee.core.sample;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <b>継承のサンプル</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class SampleClass {

	public static void main(final String[] args) throws IOException {
		// ねこ
		final Cat tama = new Cat("たま", 3);

		// いぬ
		final Dog pochi = new Dog("ポチ", 2);

		// 鳥
		final Bird pee = new Bird("ぴーちゃん", 1);

		System.out.println("★★ それぞれ情報を出力してみる ★★");
		tama.printInfo();
		pochi.printInfo();
		pee.printInfo();
		System.out.println();

		System.out.println("★★ ご飯を食べる ★★");
		tama.feed();
		pochi.feed();
		pee.feed();
		System.out.println();

		// ねこ，いぬ，鳥は共通のAnimalPetという概念であるため，AnimalPetとして一緒にできる．
		final List<AnimalPet> animalPetList = Arrays.asList(tama, pochi, pee);

		System.out.println("★★ AnimalPetとしてそれぞれ出力してみる ★★");
		animalPetList.stream().forEach(AnimalPet::printInfo);	// forEach(pet -> pet.printInfo())と同じ
		System.out.println();

		System.out.println("★★ 鳴いてみる ★★");
		animalPetList.stream().forEach(AnimalPet::say);
		System.out.println();

		System.out.println("★★ 泳げるペットを探してみる ★★");
		animalPetList.stream().filter(AnimalPet::canSwim).forEach(AnimalPet::printInfo);
		System.out.println();

		System.out.println("★★ 飛べるペットを探してみる ★★");
		animalPetList.stream().filter(AnimalPet::canFly).forEach(AnimalPet::printInfo);
		System.out.println();

	}

}
