package net.coolblossom.lycee.core;

import org.junit.Test;

import net.coolblossom.lycee.core.args.mappers.LyceeArgsMapper;

/**
 * <b>カバレッジ用テストケース</b>
 * <p>
 * 通常のテストケースとは違い、カバレッジを満たすことを目的としたテストケースです。
 * </p>
 * @author ryouka
 *
 */
public class JunkTest {

	@Test
	public void test_public_constructor() {
		// for LyceeArgsMapper#LyceeArgsMapper()
		new LyceeArgsMapper();
	}


}
