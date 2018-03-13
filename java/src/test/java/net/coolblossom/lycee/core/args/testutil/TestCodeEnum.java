package net.coolblossom.lycee.core.args.testutil;

import net.coolblossom.lycee.core.args.enums.LyceeCodeEnum;

/**
 * <b>テスト用列挙型（LyceeCodeEnum継承版）</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public enum TestCodeEnum implements LyceeCodeEnum {
	TEST1("1", "test 1")
	,TEST2("2", "test 2")
	,TEST3("3", "test 3")
	,TEST4("4", "test 4")
	,TEST5("5", "test 5")
	;

	String code;
	String display;

	private TestCodeEnum(final String code, final String display) {
		this.code = code;
		this.display = display;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDisplay() {
		return display;
	}

}