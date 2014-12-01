package org.fyfa.samples.dbcrud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.log4j.Logger;
import org.fyfa.Marshal;

/**
 * Data Access class. Uses a memory map as database
 * 
 * @author EinarValen@gmail.com
 */
public class Dao {
	final static Logger logger = Logger.getLogger(Dao.class);
	// SELECT
	// COUNTRY_ID,COUNTRY_KEY,COUNTRY_NAME,CAPITAL,EU,SUBREGION_ID,LCC,EMERGING_MARKET
	// FROM EMEA_ODS.ODS.REF_COUNTRY ORDER BY COUNTRY_NAME;
	final String[] columnAry = {"COUNTRY_ID","COUNTRY_KEY","COUNTRY_NAME","CAPITAL,EU","SUBREGION_ID","LCC","EMERGING_MARKET"};
	final String[] columnSearchAry = {"CAPITAL","COUNTRY_NAME"};
	final String columnNames = "COUNTRY_ID,COUNTRY_KEY,COUNTRY_NAME,CAPITAL,EU,SUBREGION_ID,LCC,EMERGING_MARKET";
	private final JdbcTemplate jdbcTemplate;
	private final Marshal marshal = new Marshal();

	public Dao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public CountryDo get(String countryId) {
		String sql = "SELECT " + columnNames
				+ " FROM EMEA_ODS.ODS.REF_COUNTRY WHERE COUNTRY_ID=?";
		Object[] args = new Object[] { countryId };
		Map<String, Object> row = jdbcTemplate.queryForMap(sql, args);
		return mapCountryDo(row);
	}

	private CountryDo mapCountryDo(Map<String, Object> map) {
		if (map == null || map.size() == 0)
			return null;
		CountryDo countryDo = new CountryDo();
		countryDo.setCAPITAL(getStringValue(map, "CAPITAL"));
		countryDo.setCOUNTRY_ID(getStringValue(map, "COUNTRY_ID"));
		countryDo.setCOUNTRY_KEY(getIntValue(map, "COUNTRY_KEY"));
		countryDo.setCOUNTRY_NAME(getStringValue(map, "COUNTRY_NAME"));
		countryDo.setCAPITAL(getStringValue(map, "CAPITAL"));
		countryDo.setEU(getIntValue(map, "EU"));
		countryDo.setSUBREGION_ID(getIntValue(map, "SUBREGION_ID"));
		countryDo.setLCC(getIntValue(map, "LCC"));
		countryDo.setEMERGING_MARKET(getIntValue(map, "EMERGING_MARKET"));
		return countryDo;
	}

	private List<CountryDo> mapCountryDo(List<Map<String, Object>> rows) {
		List<CountryDo> list = new ArrayList<CountryDo>();
		for (Map<String, Object> row : rows) {
			CountryDo countryDo = mapCountryDo(row);
			if (countryDo != null)
				list.add(countryDo);
		}
		return list;
	}

	private static String getStringValue(Map<String, Object> map, String key) {
		Object o = map.get(key);
		return (o == null) ? null : o.toString();
	}

	private static long getLongValue(Map<String, Object> map, String key) {
		Object o = map.get(key);
		return (o == null) ? null : ((Number) o).longValue();
	}

	private static int getIntValue(Map<String, Object> map, String key) {
		Object o = map.get(key);
		return (o == null) ? null : ((Number) o).intValue();
	}

	public void remove(String countryId) {
		String sql = "DELETE FROM EMEA_ODS.ODS.REF_COUNTRY WHERE COUNTRY_ID=?";
		Object[] args = new Object[] { countryId };
		jdbcTemplate.update(sql, args);
	}

	public void putNew(CountryDo countryDo) {
		String sql = "insert into EMEA_ODS.ODS.REF_COUNTRY (" + columnNames
				+ ") values(?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, argsInsert(countryDo));
	}

	private Object[] argsInsert(CountryDo countryDo) {
		Object[] args = new Object[] { countryDo.getCOUNTRY_ID(),
				countryDo.getCOUNTRY_KEY(), countryDo.getCOUNTRY_NAME(),
				countryDo.getCAPITAL(), countryDo.getEU(),
				countryDo.getSUBREGION_ID(), countryDo.getLCC(),
				countryDo.getEMERGING_MARKET() };
		return args;
	}

	private Object[] argsUpdate(CountryDo countryDo) {
		Object[] args = new Object[] { countryDo.getCOUNTRY_ID(),
				countryDo.getCOUNTRY_KEY(), countryDo.getCOUNTRY_NAME(),
				countryDo.getCAPITAL(), countryDo.getEU(),
				countryDo.getSUBREGION_ID(), countryDo.getLCC(),
				countryDo.getEMERGING_MARKET() };
		return args;
	}

	public void putExisting(CountryDo countryDo) {
		String sql = "UPDATE EMEA_ODS.ODS.REF_COUNTRY set COUNTRY_KEY=?,COUNTRY_NAME=?,CAPITAL,EU=?,SUBREGION_ID=?,LCC=?,EMERGING_MARKET=?"
				+ "WHERE COUNTRY_ID=?";
		jdbcTemplate.update(sql, argsUpdate(countryDo));
	}

	public List<CountryDo> find() {
		String sql = "SELECT " + columnNames
				+ " FROM EMEA_ODS.ODS.REF_COUNTRY ORDER BY COUNTRY_NAME";
		logger.info("find()");
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		return mapCountryDo(rows);
	}

	public List<CountryDo> find(CountryDo match) {
		logger.info("find(match) " + match.toString());
		Map<String, ?> map = marshal.toMap(match);
		List<Object> argList = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String key : columnSearchAry) {
			Object value = map.get(key);
			if (value != null && !value.toString().trim().isEmpty()) {
				sb.append(String.format(" %s %s = ?", first ? "where " : "and ",
						key));
				argList.add(value);
				first = false;
			}
		}
		Object[] args = argList.toArray();
		String sql = String.format("SELECT %s FROM EMEA_ODS.ODS.REF_COUNTRY %s ORDER BY COUNTRY_NAME", columnNames, sb.toString());
		logger.info("find(match) SQL: " + sql);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, args);
		return mapCountryDo(rows);
	}

}
