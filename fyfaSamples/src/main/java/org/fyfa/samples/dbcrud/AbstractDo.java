package org.fyfa.samples.dbcrud;

import java.util.List;
import java.util.Map;

public abstract class AbstractDo {
	private final String edit = "", delete = "", view = "";
	
	abstract public String coulumnKey(); 
	abstract public String[] coulumnsForRowModifications(); 
	abstract public String[] coulumnsForAddingNewRows(); 
	abstract public String[] coulumnsForSearchFilter(); 
	abstract public String[] coulumnsForListView(); 
	abstract public CountryDo map(Map<String, Object> map);
	abstract public List<CountryDo> mapCountryDo(List<Map<String, Object>> rows);


	public static String getStringValue(Map<String, Object> map, String key) {
		Object o = map.get(key);
		return (o == null) ? null : o.toString();
	}

	public static long getLongValue(Map<String, Object> map, String key) {
		Object o = map.get(key);
		return (o == null) ? null : ((Number) o).longValue();
	}

	public static int getIntValue(Map<String, Object> map, String key) {
		Object o = map.get(key);
		return (o == null) ? null : ((Number) o).intValue();
	}

}
