package org.fyfa.samples.dbcrud.link;

import java.util.Map;

import org.apache.log4j.Logger;
import org.fyfa.samples.dbcrud.Dao;
import org.fyfa.samples.dbcrud.DaoParams;
import org.springframework.jdbc.core.JdbcTemplate;

public class LinkDao extends Dao<LinkDo> {
	final static Logger logger = Logger.getLogger(LinkDao.class);

	public LinkDao(JdbcTemplate jdbcTemplate, DaoParams params) {
		super(jdbcTemplate, params);
	}

	@Override
	protected LinkDo map(Map<String, Object> map) {
		if (map == null || map.size() == 0) return null;
		LinkDo linkDo = new LinkDo();
		linkDo.setCity(getStringValue(map, "CITY"));
		linkDo.setName(getStringValue(map, "NAME"));
		linkDo.setSsn(getStringValue(map, "SSN"));
		return linkDo;
	}

	@Override
	protected void init() {
		logger.info("init() begin");
		execute("CREATE TABLE LINK (SSN VARCHAR(60), NAME VARCHAR(60), CITY VARCHAR(60))");
		execute("INSERT INTO LINK (SSN,NAME,CITY) VALUES( '1', 'John Smith', 'London' )");
		execute("INSERT INTO LINK (SSN,NAME,CITY) VALUES( '2', 'Oluf Rallkattli', 'Tromsø' )");
		execute("INSERT INTO LINK (SSN,NAME,CITY) VALUES( '3', 'Frank L Wright', 'Scottsdale, Arizona' )");
		execute("INSERT INTO LINK (SSN,NAME,CITY) VALUES( '4', 'Ronald McDonald', 'Washington, D.C.' )");
		logger.info("init() end");
	}
}
