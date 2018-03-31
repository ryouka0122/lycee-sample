package net.coolblossom.lycee.core.args.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;

/**
 * <b>配列/Collection/Mapのテストパターンを含むテスト用クラス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
public class TestClassCollectionCase {

	// ------------------------------------------------------------
	// LｙceeArgなし
	//
	protected String[] argIgnoreArray;

	protected List<String> argIgnoreList;

	protected Set<String> argIgnoreSet;

	protected Map<String, String> argIgnoreMap;

	// ------------------------------------------------------------
	// 配列型
	//
	/** 配列型 / 対応Descriptor：ArrayDescriptor / 対応Convertor：PrimitiveConvertor */
	@LyceeArg
	protected int[] argIntArray;

	/** 配列型 / 対応Descriptor：ArrayDescriptor / 対応Convertor：StringConvertor */
	@LyceeArg
	protected String[] argStrArray;

	/** 配列型 / 対応Descriptor：ArrayDescriptor / 対応Convertor：DefaultConvertor */
	@LyceeArg
	protected StringHolder[] argClsArray;

	// ------------------------------------------------------------
	// Collection型
	//
	/** List型（型指定あり） / 対応Descriptor：CollectionDescriptor / 対応Convertor：EnumConvertor */
	@LyceeArg(ArrayList.class)
	protected List<TestEnum> argEnumList;

	/** List型（型指定なし） / 対応Descriptor：CollectionDescriptor / 対応Convertor：StringConvertor */
	@LyceeArg
	protected ArrayList<String> argStrList;

	/** Set型 / 対応Descriptor：CollectionDescriptor / 対応Convertor：StringConvertor */
	@LyceeArg(HashSet.class)
	protected Set<String> argStrSet;


}
