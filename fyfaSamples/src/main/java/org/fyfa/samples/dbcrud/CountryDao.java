package org.fyfa.samples.dbcrud;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class CountryDao extends Dao<CountryDo> {

	public CountryDao(JdbcTemplate jdbcTemplate, DaoParams params) {
		super(jdbcTemplate, params);
	}

	@Override
	protected CountryDo map(Map<String, Object> map) {
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


}
