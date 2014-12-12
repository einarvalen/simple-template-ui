package org.fyfa.samples.dbcrud.reference;

import java.util.Map;

import org.fyfa.samples.dbcrud.Dao;
import org.fyfa.samples.dbcrud.DaoParams;
import org.springframework.jdbc.core.JdbcTemplate;

public class CurrencyExchangeRateDao extends Dao<CurrencyExchangeRateDo> {

	public CurrencyExchangeRateDao(JdbcTemplate jdbcTemplate, DaoParams params) {
		super(jdbcTemplate, params);
	}

	@Override
	protected CurrencyExchangeRateDo map(Map<String, Object> map) {
		if (map == null || map.size() == 0) return null;
		CurrencyExchangeRateDo domainObject = new CurrencyExchangeRateDo();
		domainObject.setFROM_CURRENCY(getStringValue(map, "FROM_CURRENCY"));
		domainObject.setTO_CURRENCY(getStringValue(map, "TO_CURRENCY"));
		domainObject.setPERIOD(getIntValue(map, "PERIOD"));
		domainObject.setRATE(getDoubleValue(map, "RATE"));
		return domainObject;
	}

}
