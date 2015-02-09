package org.fyfa.samples.dbcrud.link;

import java.util.Map;

import org.apache.log4j.Logger;
import org.fyfa.samples.dbcrud.Dao;
import org.springframework.jdbc.core.JdbcTemplate;

public class LinkDao extends Dao<LinkDo> {
	final static Logger logger = Logger.getLogger(LinkDao.class);
	private final String[] columns = {"ssn", "name","city"};

	public LinkDao(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	protected LinkDo map(Map<String, Object> map) {
		if (map == null || map.size() == 0) return null;
		LinkDo linkDo = new LinkDo();
		linkDo.setCity(getStringValue(map, "city"));
		linkDo.setName(getStringValue(map, "name"));
		linkDo.setSsn(getStringValue(map, "ssn"));
		return linkDo;
	}

	@Override
	protected void init() {
		logger.info("init() begin");
		execute("CREATE TABLE LINK (SSN VARCHAR(60), name VARCHAR(60), city VARCHAR(60))");
		execute("INSERT INTO LINK (SSN,NAME,CITY) VALUES( '1', 'John Smith', 'London' )");
		execute("INSERT INTO LINK (SSN,NAME,CITY) VALUES( '2', 'Oluf Rallkattli', 'Tromsø' )");
		execute("INSERT INTO LINK (SSN,NAME,CITY) VALUES( '3', 'Frank L Wright', 'Scottsdale, Arizona' )");
		execute("INSERT INTO LINK (SSN,NAME,CITY) VALUES( '4', 'Ronald McDonald', 'Washington, D.C.' )");
		logger.info("init() end");
	}

	@Override
	public String getTableName() {
		return "LINK";
	}

	@Override
	public String getDatabaseName() {
		return "DBCRUD";
	}

	@Override
	public String getColumnKey() {
		return "ssn";
	}

	@Override
	public String[] getColumnsForRowModifications() {
		return columns;
	}

	@Override
	public String[] getColumnsForAddingNewRows() {
		return columns;
	}

	@Override
	public String[] getColumnsForSearchFilter() {
		return columns;
	}

	@Override
	public String[] getColumnsForListView() {
		return columns;
	}

}
