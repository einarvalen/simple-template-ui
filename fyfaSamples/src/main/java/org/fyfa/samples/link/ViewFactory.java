package org.fyfa.samples.link;

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

	public Form<LinkDo> createFormForRowModifications() {
		FormParams<LinkDo> params = new FormParams<LinkDo>( "ModifyLink", LinkDo.class, this.context );
		params.setOperation( Operation.Modify );
		params.setColumnNamesInSequence( new String[] { "ssn", "name", "city" } );
		params.setFormActionUri( this.pathSetting.getModifyUri() );
		params.addButton( new SubmitButton( "Update", "Submit form data for update" ) );
		params.addButton( new Button( "Quit", "Quit this form", getQuitAction() ) );
		params.setTitle( "Link - Modify Existing" );
		return new Form<LinkDo>( params );
	}

	public Form<LinkDo> createFormForAddingNewRows() {
		FormParams<LinkDo> params = new FormParams<LinkDo>( "NewLink", LinkDo.class, this.context );
		params.setOperation( Operation.New );
		params.setColumnNamesInSequence( new String[] { "ssn", "name", "city" } );
		params.setFormActionUri( this.pathSetting.getNewUri() );
		params.addButton( new SubmitButton( "Insert", "Submit form data for insertion" ) );
		params.addButton( new Button( "Quit", "Quit this form", getQuitAction() ) );
		params.setTitle( "Link - Enter New" );
		return new Form<LinkDo>( params );
	}

	public Form<LinkDo> createFormForSearchFilter() {
		FormParams<LinkDo> params = new FormParams<LinkDo>( "SearchLink", LinkDo.class, this.context );
		params.setOperation( Operation.Search );
		params.setColumnNamesInSequence( new String[] { "name", "city" } );
		params.setFormActionUri( this.pathSetting.getSearchUri() );
		params.addButton( new SubmitButton( "Search", "Submit form to search for data" ) );
		params.addButton( new Button( "Quit", "Quit this form", getQuitAction() ) );
		params.setTitle( "Link - Search" );
		return new Form<LinkDo>( params );
	}

	public Form<LinkDo> createFormForViewingRow() {
		FormParams<LinkDo> params = new FormParams<LinkDo>( "ViewLink", LinkDo.class, this.context );
		params.setColumnNamesInSequence( new String[] { "ssn", "name", "city" } );
		params.setOperation( Operation.View );
		params.setHtmlMethod( "POST" );
		params.addButton( new Button( "Quit", "Quit this form", getQuitAction() ) );
		params.setTitle( "Link - Row View" );
		return new Form<LinkDo>( params );
	}

	public Table<LinkDo> createTableForViewingRows() {
		TableParams<LinkDo> params = new TableParams<LinkDo>( "Link List", LinkDo.class, this.context );
		params.setColumnNamesInSequence( new String[] { "view", "edit", "delete", "ssn", "name", "city" } );
		params.addButton( new Button( "New", "Enter new row", getAction( this.pathSetting.getNewFormUri() ) ) );
		params.addButton( new Button( "Search", "Seach filter", getAction( this.pathSetting.getSearchFormUri() ) ) );
		params.addRowAction( new RowAction( new ItemId( "edit" ), "Edit", getAction( this.pathSetting.getModifyFormUri() ) ) );
		params.addRowAction( new RowAction( new ItemId( "delete" ), "Delete", getAction( this.pathSetting.getDeleteUri() ) ) );
		params.addRowAction( new RowAction( new ItemId( "view" ), "View", getAction( this.pathSetting.getViewFormUri() ) ) );
		params.setTitle( "Link - Table View. -- Links is a CRUD show-case" );
		return new Table<LinkDo>( params );
	}

	private String getQuitAction() {
		return getAction( this.pathSetting.getTableUri() );
	}

	private String getAction( String uri ) {
		return String.format( "document.location=\"%s\"", uri );
	}

}
