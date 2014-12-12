package org.fyfa.samples.dbcrud.reference;

import java.util.Map;

import org.fyfa.samples.dbcrud.Dao;
import org.fyfa.samples.dbcrud.DaoParams;
import org.springframework.jdbc.core.JdbcTemplate;

public class SbuDao extends Dao<SbuDo> {

	public SbuDao(JdbcTemplate jdbcTemplate, DaoParams params) {
		super(jdbcTemplate, params);
	}

	@Override
	protected SbuDo map(Map<String, Object> map) {
		if (map == null || map.size() == 0) return null;
		SbuDo sbuDo = new SbuDo();
		sbuDo.setSBU_ID(getStringValue(map, "SBU_ID"));
		sbuDo.setSBU_KEY(getIntValue(map, "SBU_KEY"));
		sbuDo.setSBU_NAME(getStringValue(map, "SBU_NAME"));
		return sbuDo;
	}

}
