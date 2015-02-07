package org.fyfa.samples.dbcrud;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import org.fyfa.Context;
import org.fyfa.components.Form;
import org.fyfa.components.ParseException;
import org.fyfa.components.Table;
import org.fyfa.samples.RenderingEngine;
import org.fyfa.samples.dbcrud.link.LinkDao;
import org.fyfa.samples.dbcrud.link.LinkDo;
import org.springframework.jdbc.core.JdbcTemplate;

/** 
 * @author EinarValen@gmail.com */
public class Service<T extends DomainObject> {
	private final Dao<T> dao;
	private final RenderingEngine renderingEngine;
	private final Form<T> formModify;
	private final Form<T> formNew;
	private final Form<T> formSearch;
	private final Form<T> formView;
	private final Table<T> tableList;

	public Service( Context context, RenderingEngine renderingEngine, ViewFactory<T> viewFactory, Dao<T> dao) throws Exception {
		this.renderingEngine = renderingEngine;
		this.dao = dao;
		formModify = viewFactory.createFormForRowModifications();
		formNew = viewFactory.createFormForAddingNewRows();
		formSearch = viewFactory.createFormForSearchFilter();
		formView = viewFactory.createFormForViewingRow();
		tableList = viewFactory.createTableForViewingRows();
	}

		private String render( Form<T> form,  T t ) {
		String html = form.render( t );
		return this.renderingEngine.render( html, form.getTitle() );
	}

	private String renderWithErrors( Form<T> form, T t, ParseException e ) {
		String html = form.render( t, e );
		return this.renderingEngine.render( html, form.getTitle() );
	}

	private String render( Table<T> table, List<T> list ) {
		String html = table.render( list );
		return this.renderingEngine.render( html, table.getTitle() );
	}

	private T parse( Form<T> form, MultivaluedMap<String, String> multivaluedMap, T t ) throws ParseException {
		return form.parse( this.renderingEngine.toMap( multivaluedMap ), t );
	}

	/** Renders formModify - extracts a CountryDo object from the database based on COUNTRY_ID. Then renders the object in a form ready for the user to alter the field values of choice  */
	public String formModify( String key ) {
		try {
			return render( formModify, dao.get( key ) );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Parses user input submitted from formModify and updates the actual data in the database. Then renders the tableList ready for more user interaction */
	public String modify( MultivaluedMap<String, String> multivaluedMap, T t ) {
		try {
			t = parse( formModify, multivaluedMap, t );
			dao.putExisting( t );
			return render( tableList, dao.find() );
		} catch (ParseException e) {
			return renderWithErrors( formModify, t, e );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders formSearch - a form to accept data to issue a database search for existing CountryDo objects  */
	public String formSearch(T t) {
		try {
			return render( formSearch, t );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Parses user input submitted from formSearch performs a search from the database. Then renders the extracted list of CountryDo objects in tableList */
	public String search( MultivaluedMap<String, String> multivaluedMap, T t ) {
		try {
			t = parse( formSearch, multivaluedMap, t );
			return render( tableList, dao.find( t ) );
		} catch (ParseException e) {
			return renderWithErrors( formSearch, t, e );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders formView - a read only view of one existing CountryDo object */
	public String formView( String key ) {
		try {
			return render( formView, dao.get( key ) );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders formNew - a form to accept data for new CountryDo objects  */
	public String formNew(T t) {
		try {
			return render( formNew, t );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Parses user input submitted from formNew and inserts the data in the database. Then renders the newForm ready for more user input  */
	public String create( MultivaluedMap<String, String> multivaluedMap, T t ) {
		try {
			t = parse( formNew, multivaluedMap, t );
			dao.putNew( t );
			t.clear();
			return render( formNew, t );
		} catch (ParseException e) {
			return renderWithErrors( formNew, t, e );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Executes the remove from database and renders the table view  */
	public String delete( String key ) {
		try {
			dao.remove( key );
			return render( tableList, dao.find() );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders the table view of a list of CountryDo objects  */
	public String table() {
		try {
			return render( tableList, dao.find() );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

}
