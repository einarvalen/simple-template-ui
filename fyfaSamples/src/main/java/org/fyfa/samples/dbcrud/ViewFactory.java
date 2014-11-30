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

/** Cretes all views
 *
 * @author EinarValen@gmail.com */
public class ViewFactory {
	private final PathSetting pathSetting;
	private final Context context;

	public ViewFactory( Context context, PathSetting pathSetting ) {
		this.pathSetting = pathSetting;
		this.context = context;
	}

	public Form<CountryDo> createFormForRowModifications() {
		FormParams<CountryDo> params = new FormParams<CountryDo>( "ModifyCountry", CountryDo.class, this.context );
		params.setOperation( Operation.Modify );
		params.setColumnNamesInSequence( new String[] { "COUNTRY_ID", "COUNTRY_NAME", "COUNTRY_KEY" } );
		params.setFormActionUri( this.pathSetting.getModifyUri() );
		params.addButton( new SubmitButton( "Update", "Submit form data for update" ) );
		params.addButton( new Button( "Quit", "Quit this form", getQuitAction() ) );
		params.setTitle( "Country - Modify Existing" );
		return new Form<CountryDo>( params );
	}

	public Form<CountryDo> createFormForAddingNewRows() {
		FormParams<CountryDo> params = new FormParams<CountryDo>( "NewCountry", CountryDo.class, this.context );
		params.setOperation( Operation.New );
		params.setColumnNamesInSequence( new String[] { "COUNTRY_ID", "COUNTRY_NAME", "COUNTRY_KEY" } );
		params.setFormActionUri( this.pathSetting.getNewUri() );
		params.addButton( new SubmitButton( "Insert", "Submit form data for insertion" ) );
		params.addButton( new Button( "Quit", "Quit this form", getQuitAction() ) );
		params.setTitle( "Country - Enter New" );
		return new Form<CountryDo>( params );
	}

	public Form<CountryDo> createFormForSearchFilter() {
		FormParams<CountryDo> params = new FormParams<CountryDo>( "SearchCountry", CountryDo.class, this.context );
		params.setOperation( Operation.Search );
		params.setColumnNamesInSequence( new String[] { "COUNTRY_NAME", "COUNTRY_KEY" } );
		params.setFormActionUri( this.pathSetting.getSearchUri() );
		params.addButton( new SubmitButton( "Search", "Submit form to search for data" ) );
		params.addButton( new Button( "Quit", "Quit this form", getQuitAction() ) );
		params.setTitle( "Country - Search" );
		return new Form<CountryDo>( params );
	}

	public Form<CountryDo> createFormForViewingRow() {
		FormParams<CountryDo> params = new FormParams<CountryDo>( "ViewCountry", CountryDo.class, this.context );
		params.setColumnNamesInSequence( new String[] { "COUNTRY_ID", "COUNTRY_NAME", "COUNTRY_KEY" } );
		params.setOperation( Operation.View );
		params.setHtmlMethod( "POST" );
		params.addButton( new Button( "Quit", "Quit this form", getQuitAction() ) );
		params.setTitle( "Country - Row View" );
		return new Form<CountryDo>( params );
	}

	public Table<CountryDo> createTableForViewingRows() {
		TableParams<CountryDo> params = new TableParams<CountryDo>( "Country List", CountryDo.class, this.context );
		params.setColumnNamesInSequence( new String[] { "view", "edit", "delete", "COUNTRY_ID", "COUNTRY_NAME", "COUNTRY_KEY" } );
		params.addButton( new Button( "New", "Enter new row", getAction( this.pathSetting.getNewFormUri() ) ) );
		params.addButton( new Button( "Search", "Seach filter", getAction( this.pathSetting.getSearchFormUri() ) ) );
		params.addRowAction( new RowAction( new ItemId( "edit" ), "Edit", getAction( this.pathSetting.getModifyFormUri() ) ) );
		params.addRowAction( new RowAction( new ItemId( "delete" ), "Delete", getAction( this.pathSetting.getDeleteUri() ) ) );
		params.addRowAction( new RowAction( new ItemId( "view" ), "View", getAction( this.pathSetting.getViewFormUri() ) ) );
		params.setTitle( "Country - Table View. -- Countrys is a CRUD show-case" );
		return new Table<CountryDo>( params );
	}

	private String getQuitAction() {
		return getAction( this.pathSetting.getTableUri() );
	}

	private String getAction( String uri ) {
		return String.format( "document.location=\"%s\"", uri );
	}

}
