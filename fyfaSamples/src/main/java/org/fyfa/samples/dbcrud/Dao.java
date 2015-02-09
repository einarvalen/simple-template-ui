package org.fyfa.samples.dbcrud;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.fyfa.Marshal;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Data Access class. Uses a memory map as database
 * 
 * @author EinarValen@gmail.com
 */
abstract public class Dao<T> {
	final static Logger logger = Logger.getLogger(Dao.class);
	private final JdbcTemplate jdbcTemplate;
	private final Marshal marshal = new Marshal();

	public Dao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		init();
	}

	protected void init() {}

	public void execute(String sql) {
		jdbcTemplate.update(sql);
	}

	private Object toCommaList(String[] columnNames) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String fieldName : columnNames) {
			if (!first) sb.append(",");
			sb.append(fieldName);
			first = false;
		}
		return sb.toString();
	}

	private Object toQuestionMarkList(String[] columnNames) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String str : columnNames) {
			if (!first) sb.append(",");
			sb.append("?");
			first = false;
		}
		return sb.toString();
	}

	private Object toUpdateSetList(String[] columnNames) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String fieldName : columnNames) {
			if (!first) sb.append(",");
			sb.append(fieldName + "=?");
			first = false;
		}
		return sb.toString();
	}

	private Object[] toArgumentAry(T t, String[] columnNames) {
		List<Object> list = new ArrayList<Object>();
		for (String fieldName : columnNames) {
			list.add(marshal.getValue(t, fieldName));
		}
		return list.toArray();
	}

	abstract protected T map(Map<String, Object> map);

	private List<T> map(List<Map<String, Object>> rows) {
		List<T> list = new ArrayList<T>();
		for (Map<String, Object> row : rows) {
			T t = map(row);
			if (t != null) list.add(t);
		}
		return list;
	}

	public static String getStringValue(Map<String, Object> map, String key) {
		Object o = map.get(key);
		return (o == null) ? null : o.toString();
	}

	public static long getLongValue(Map<String, Object> map, String key) {
		Object o = map.get(key);
		return (o == null) ? null : ((Number) o).longValue();
	}

	public static Double getDoubleValue(Map<String, Object> map, String key) {
		Object o = map.get(key);
		return (o == null) ? null : ((Number) o).doubleValue();
	}

	public static int getIntValue(Map<String, Object> map, String key) {
		Object o = map.get(key);
		return (o == null) ? null : ((Number) o).intValue();
	}


	public static <T> List<String> columnNames(T domainObject) throws IllegalArgumentException {
		List<String> list = new Marshal().keys(domainObject);
		list.remove("new");
		list.remove("delete");
		list.remove("edit");
		list.remove("view");
		if (list == null || list.size() < 1) throw new IllegalArgumentException("daominObject has no usable fields.");
		return list;
	}

	public T get(String id) {
		String sql = String.format("SELECT %s FROM %s WHERE %s=?", toCommaList(getColumnsForListView()), getTableName(), getColumnKey());
		Object[] args = new Object[] { id.trim() };
		logger.info("get() SQL: " + sql);
		logger.info("get() ID: " + id);
		Map<String, Object> row = jdbcTemplate.queryForMap(sql, args);
		return map(row);
	}

	public void remove(String id) {
		String sql = String.format("DELETE FROM %s WHERE %s=?", getTableName(), getColumnKey());
		Object[] args = new Object[] { id };
		jdbcTemplate.update(sql, args);
	}

	public void putNew(T t) {
		String sql = String.format("insert into %s (%s) values(%s)", getTableName(),
				toCommaList(getColumnsForAddingNewRows()), toQuestionMarkList(getColumnsForAddingNewRows()));
		jdbcTemplate.update(sql, toArgumentAry(t, getColumnsForAddingNewRows()));
	}

	public void putExisting(T t) {
		String sql = String.format("UPDATE %s set %s WHERE %s=?", getTableName(), toUpdateSetList(getColumnsForRowModifications()), getColumnKey());
		logger.info("putExisting() T: " + t.toString());
		logger.info("putExisting() SQL: " + sql);
		jdbcTemplate.update(sql, toArgumentAry(t, concat(getColumnsForRowModifications(), new String[] { getColumnKey() })));
	}

	private String[] concat(String[] a, String[] b) {
		int aLen = a.length;
		int bLen = b.length;
		String[] c = new String[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}

	public List<T> find() {
		String sql = String.format("SELECT %s FROM %s ORDER BY %s", toCommaList(getColumnsForListView()), getTableName(), getColumnKey());
		logger.info("find() SQL: " + sql);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		return map(rows);
	}

	public List<T> find(T match) {
		logger.info("find(match) " + match.toString());
		Map<String, ?> map = marshal.toMap(match);
		List<Object> argList = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String key : getColumnsForSearchFilter()) {
			Object value = map.get(key);
			if (value != null && !value.toString().trim().isEmpty() && !value.toString().trim().equals("0")) {
				sb.append(String.format(" %s %s like (?)", first ? "where " : "and ", key));
				argList.add(value);
				first = false;
			}
		}
		Object[] args = argList.toArray();
		String sql = String.format("SELECT %s FROM %s %s ORDER BY %s", toCommaList(getColumnsForListView()), getTableName(), sb.toString(), getColumnKey());
		logger.info("find(match) SQL: " + sql);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, args);
		return map(rows);
	}

	abstract protected String getTableName();
	abstract protected String getDatabaseName();
	abstract protected String getColumnKey();
	abstract protected String[] getColumnsForRowModifications();
	abstract protected String[] getColumnsForAddingNewRows();
	abstract protected String[] getColumnsForSearchFilter();
	abstract protected String[] getColumnsForListView();

}
