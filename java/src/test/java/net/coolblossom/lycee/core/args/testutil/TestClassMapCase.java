package net.coolblossom.lycee.core.args.testutil;

import java.util.HashMap;
import java.util.Map;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.annotations.LyceeArgCollection;

/**
 * <b>Map型用テストクラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class TestClassMapCase {

	// ------------------------------------------------------------
	// Map型
	//
	/** Map型 / 対応Descriptor：MapDescriptor / 対応Convertor：StringConvertor */
	@LyceeArg
	@LyceeArgCollection(HashMap.class)
	protected Map<String, String> argStrMap;

}
