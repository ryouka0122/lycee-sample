package net.coolblossom.lycee.core.args.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.coolblossom.lycee.core.args.annotations.LyceeArg;
import net.coolblossom.lycee.core.args.annotations.LyceeArgClass;

@LyceeArgClass
public class TestClass {
	public Integer intArg;
	public String strArg;
	public long longArg;

	@LyceeArg(ArrayList.class)
	public List<String> strList;
	@LyceeArg(ArrayList.class)
	public List<Integer> intList;

	@LyceeArg(HashSet.class)
	public Set<String> strSet;
	@LyceeArg(HashSet.class)
	public Set<Integer> intSet;
	@LyceeArg(HashSet.class)
	public Set<Character> charSet;

	@LyceeArg(LinkedHashMap.class)
	public Map<String, String> strMap;
	@LyceeArg(LinkedHashMap.class)
	public Map<String, Integer> intMap;

	public String[] strAry;

	public StringHolder clsArg;
	public StringHolder[] clsAry;

	@LyceeArg(ArrayList.class)
	public List<StringHolder> clsList;

	public TestClass() {
	}
}