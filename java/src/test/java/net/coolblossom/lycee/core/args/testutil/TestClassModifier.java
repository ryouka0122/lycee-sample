package net.coolblossom.lycee.core.args.testutil;

/**
 * <b>アクセス修飾子確認のテスト用クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class TestClassModifier {

	// ===========================================================
	// 修飾子なし（packageスコープ）
	//
	public static class TestClassPackage {
		int argPackage;
	}

	public static class TestClassPackageFinal {
		final int argPackageFinal = 100;
	}

	// ===========================================================
	// public修飾子
	//
	public static class TestClassPublic {
		public int argPublic;
	}

	public static class TestClassPublicFinal {
		public final int argPublicFinal = 100;
	}

	// ===========================================================
	// protected修飾子
	//
	public static class TestClassProtected {
		protected int argProtected;
	}

	public static class TestClassProtectedFinal {
		protected final int argProtectedFinal = 100;
	}

	// ===========================================================
	// private修飾子
	//
	public static class TestClassPrivate {
		private int argPrivate;
	}

	public static class TestClassPrivateFinal {
		private final int argPrivateFinal = 100;
	}

}
