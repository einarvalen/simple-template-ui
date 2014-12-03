package org.fyfa.samples.dbcrud;

import java.util.List;

import org.fyfa.Marshal;

public class DaoParams {
	private String tableName;
	private String databaseName;
	private String columnKey;
	private String[] columnsForRowModifications;
	private String[] columnsForRowModificationsDao;
	private String[] columnsForAddingNewRows;
	private String[] columnsForSearchFilter;
	private String[] columnsForListView;
	
	public String[] getColumnsForRowModificationsDao() {
		return columnsForRowModificationsDao;
	}

	public void setColumnsForRowModificationsDao(String[] columnsForRowModificationsDao) {
		this.columnsForRowModificationsDao = columnsForRowModificationsDao;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String[] getColumnsForRowModifications() {
		return columnsForRowModifications;
	}

	public void setColumnsForRowModifications(
			String[] columnsForRowModifications) {
		this.columnsForRowModifications = columnsForRowModifications;
	}

	public String[] getColumnsForAddingNewRows() {
		return columnsForAddingNewRows;
	}

	public void setColumnsForAddingNewRows(String[] columnsForAddingNewRows) {
		this.columnsForAddingNewRows = columnsForAddingNewRows;
	}

	public String[] getColumnsForSearchFilter() {
		return columnsForSearchFilter;
	}

	public void setColumnsForSearchFilter(String[] columnsForSearchFilter) {
		this.columnsForSearchFilter = columnsForSearchFilter;
	}

	public String[] getColumnsForListView() {
		return columnsForListView;
	}

	public void setColumnsForListView(String[] columnsForListView) {
		this.columnsForListView = columnsForListView;
	}
	
	public static <T> DaoParams assign( T domainObject, String databaseName, String tableName, String columnKey ) throws Exception {
		List<String> list = new Marshal().keys( domainObject );
		list.remove("new"); 
		list.remove("delete");
		list.remove("edit");
		list.remove("view");
		if (list == null || list.size() < 1) throw new IllegalArgumentException("daominObject has no usable fields.");
		String[] columnNames = list.toArray(new String[]{});
		DaoParams daoParams = new DaoParams();
		daoParams.setColumnsForAddingNewRows(columnNames);
		daoParams.setColumnsForListView(columnNames);
		daoParams.setColumnsForSearchFilter(columnNames);
		daoParams.setColumnsForRowModifications(columnNames);
		list.remove(columnKey);
		columnNames = list.toArray(new String[]{});
		daoParams.setColumnsForRowModificationsDao(columnNames);
		daoParams.setTableName(tableName);
		daoParams.setDatabaseName(databaseName);
		daoParams.setColumnKey(columnKey);
		return daoParams;
	}

}
