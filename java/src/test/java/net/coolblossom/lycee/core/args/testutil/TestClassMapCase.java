package net.coolblossom.lycee.core.args.testutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.annotations.LyceeArgClass;

/**
 * <b>Map型用テストクラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class TestClassMapCase {

	// ------------------------------------------------------------
	// Map型1つ
	//
	public static class SingleMapCase {
		/** Map型 / 対応Descriptor：MapDescriptor / 対応Convertor：StringConvertor */
		@LyceeArg(HashMap.class)
		protected Map<String, String> argStrMap;
	}

	// ------------------------------------------------------------
	// Map型1つ（Mapのキーの型がStringを引数にしたコンストラクタを実装してあるとき）
	//
	public static class StringLikeKeyCase {
		/** Map型 / 対応Descriptor：MapDescriptor / 対応Convertor：StringConvertor */
		@LyceeArg(HashMap.class)
		protected Map<StringHolder, String> argStrMap;
	}

	// ------------------------------------------------------------
	// Map型1つ（Mapのキーの型がStringを引数にしたコンストラクタを実装してないとき）
	//
	public static class IllegalKeyCase {
		/** Map型 / 対応Descriptor：MapDescriptor / 対応Convertor：なし */
		@LyceeArg(HashMap.class)
		protected Map<IntegerHolder, String> argStrMap;
	}

	// ------------------------------------------------------------
	// HashMap型1つ（型を指定しないパターン）
	//
	public static class HashMapCase {
		/** Map型 / 対応Descriptor：MapDescriptor / 対応Convertor：DefaultConvertor */
		@LyceeArg
		protected HashMap<String, Integer> argStrMap;
	}


	// ------------------------------------------------------------
	// Map型2つ
	//
	public static class TwinMapCase {
		@LyceeArg(HashMap.class)
		protected Map<String, String> argStrMap1;

		@LyceeArg(TreeMap.class)
		protected Map<String, Integer> argStrMap2;

	}

	// ------------------------------------------------------------
	// LyceeArgClassを使ったパターン
	//
	@LyceeArgClass
	public static class ClassAnnotationCase {
		/** Map型 / 対応Descriptor：MapDescriptor / 対応Convertor：StringConvertor */
		@LyceeArg(LinkedHashMap.class)
		protected Map<String, String> argStrMap;
	}

	// ------------------------------------------------------------
	// LyceeArgClassを使ったパターン（HashMap型1つ）
	//
	@LyceeArgClass
	public static class ClassAnnotationCaseHashMap {
		/** Map型 / 対応Descriptor：MapDescriptor / 対応Convertor：StringConvertor */
		protected TreeMap<String, String> argStrMap;
	}


	// ------------------------------------------------------------
	// 複数種類含んでいるパターン
	//
	public static class ComplexCase {
		/** Map型 / 対応Descriptor：MapDescriptor / 対応Convertor：StringConvertor */
		@LyceeArg(HashMap.class)
		protected Map<String, String> argStrMap;

		@LyceeArg(ArrayList.class)
		protected List<String> strList;

		@LyceeArg(ArrayList.class)
		protected List<Integer> intList;
	}

	// ------------------------------------------------------------
	// 複数種類含んでいるパターン（デフォルト指定あり）
	//
	public static class ComplexCaseUseDefault {
		/** Map型 / 対応Descriptor：MapDescriptor / 対応Convertor：StringConvertor */
		@LyceeArg(value=HashMap.class, isDefault=true)
		protected Map<String, String> argStrMap;

		@LyceeArg(ArrayList.class)
		protected List<String> strList;

		@LyceeArg(ArrayList.class)
		protected List<Integer> intList;
	}




}
