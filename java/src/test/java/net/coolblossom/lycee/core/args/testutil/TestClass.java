package net.coolblossom.lycee.core.args.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.coolblossom.lycee.core.args.annotations.LyceeArgClass;
import net.coolblossom.lycee.core.args.annotations.LyceeArgCollection;

@LyceeArgClass
public class TestClass {
	public Integer intArg;
	public String strArg;
	public long longArg;

	@LyceeArgCollection(ArrayList.class)
	public List<String> strList;
	@LyceeArgCollection(ArrayList.class)
	public List<Integer> intList;

	@LyceeArgCollection(HashSet.class)
	public Set<String> strSet;
	@LyceeArgCollection(HashSet.class)
	public Set<Integer> intSet;
	@LyceeArgCollection(HashSet.class)
	public Set<Character> charSet;

	@LyceeArgCollection(LinkedHashMap.class)
	public Map<String, String> strMap;
	@LyceeArgCollection(LinkedHashMap.class)
	public Map<String, Integer> intMap;

	public String[] strAry;

	public StringHolder clsArg;
	public StringHolder[] clsAry;

	@LyceeArgCollection(ArrayList.class)
	public List<StringHolder> clsList;

	public TestClass() {
	}
}