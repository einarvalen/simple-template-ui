package org.fyfa.samples.dbcrud;

import org.fyfa.Button;
import org.fyfa.Context;
import org.fyfa.Operation;
import org.fyfa.SubmitButton;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.RowAction;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;
import org.fyfa.ids.ItemId;
import org.springframework.jdbc.core.JdbcTemplate;

/** Cretes all views
 *
 * @author EinarValen@gmail.com */
public class ViewFactory<T> {
	private final PathSetting pathSetting;
	private final Context context;
	private final Class<T> clazz;

	private final String[] columnsForRowModifications;
	private final String[] columnsForAddingNewRows;
	private final String[] columnsForSearchFilter;
	private final String[] columnsForListView;
	private final String tableName;

	public ViewFactory(Class<T> clazz, Context context, PathSetting pathSetting, Dao<T> dao) {
		this.clazz = clazz;
		this.pathSetting = pathSetting;
		this.context = context;
		this.columnsForAddingNewRows = dao.getColumnsForAddingNewRows();
		this.columnsForRowModifications = dao.getColumnsForRowModifications();
		this.columnsForSearchFilter = dao.getColumnsForSearchFilter();
		this.columnsForListView = dao.getColumnsForListView();
		this.tableName = dao.getTableName();
	}

	public Form<T> createFormForRowModifications() {
		FormParams<T> params = new FormParams<T>("Modify" + tableName, clazz, this.context);
		params.setOperation(Operation.Modify);
		params.setColumnNamesInSequence(this.columnsForRowModifications);
		params.setFormActionUri(this.pathSetting.getModifyUri());
		params.addButton(new SubmitButton("Update", "Submit form data for update"));
		params.addButton(new Button("Quit", "Quit this form", getQuitAction()));
		params.setTitle(tableName + " - Modify Existing");
		return new Form<T>(params);
	}

	public Form<T> createFormForAddingNewRows() {
		FormParams<T> params = new FormParams<T>("New" + tableName, clazz, this.context);
		params.setOperation(Operation.New);
		params.setColumnNamesInSequence(this.columnsForAddingNewRows);
		params.setFormActionUri(this.pathSetting.getNewUri());
		params.addButton(new SubmitButton("Insert", "Submit form data for insertion"));
		params.addButton(new Button("Quit", "Quit this form", getQuitAction()));
		params.setTitle(tableName + " - Enter New");
		return new Form<T>(params);
	}

	public Form<T> createFormForSearchFilter() {
		FormParams<T> params = new FormParams<T>("Search" + tableName, clazz, this.context);
		params.setOperation(Operation.Search);
		params.setColumnNamesInSequence(this.columnsForSearchFilter);
		params.setFormActionUri(this.pathSetting.getSearchUri());
		params.addButton(new SubmitButton("Search", "Submit form to search for data"));
		params.addButton(new Button("Quit", "Quit this form", getQuitAction()));
		params.setTitle(tableName + " - Search");
		return new Form<T>(params);
	}

	public Form<T> createFormForViewingRow() {
		FormParams<T> params = new FormParams<T>("View" + tableName, clazz, this.context);
		params.setColumnNamesInSequence(this.columnsForRowModifications);
		params.setOperation(Operation.View);
		params.setHtmlMethod("POST");
		params.addButton(new Button("Quit", "Quit this form", getQuitAction()));
		params.setTitle(tableName + " - Row View");
		return new Form<T>(params);
	}

	public Table<T> createTableForViewingRows() {
		TableParams<T> params = new TableParams<T>("List" + tableName, clazz, this.context);
		params.setColumnNamesInSequence(concat(new String[] { "view", "edit", "delete" }, this.columnsForListView));
		params.addButton(new Button("New", "Enter new row", getAction(this.pathSetting.getNewFormUri())));
		params.addButton(new Button("Search", "Seach filter", getAction(this.pathSetting.getSearchFormUri())));
		params.addRowAction(new RowAction(new ItemId("edit"), "Edit", getAction(this.pathSetting.getModifyFormUri())));
		params.addRowAction(new RowAction(new ItemId("delete"), "Delete", getAction(this.pathSetting.getDeleteUri())));
		params.addRowAction(new RowAction(new ItemId("view"), "View", getAction(this.pathSetting.getViewFormUri())));
		params.setTitle(tableName + " - Table View. -- is a CRUD show-case");
		return new Table<T>(params);
	}

	private String getQuitAction() {
		return getAction(this.pathSetting.getTableUri());
	}

	private String getAction(String uri) {
		return String.format("document.location=\"%s\"", uri);
	}

	private String[] concat(String[] a, String[] b) {
		int aLen = a.length;
		int bLen = b.length;
		String[] c = new String[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}

}
