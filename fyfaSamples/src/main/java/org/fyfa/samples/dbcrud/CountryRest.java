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
import org.springframework.jdbc.core.JdbcTemplate;

/** Link is a CRUD show-case.
 *
 * @author EinarValen@gmail.com */
@Path("/country")
public class CountryRest {
	private static final String QuitUri = "/fyfaSamples";
	private static final String TableUri = "/table";
	private static final String ModifyFormUri = "/form/modify/{COUNTRY_ID}";
	private static final String ModifyUri = "/modify";
	private static final String NewFormUri = "/form/new";
	private static final String NewUri = "/new";
	private static final String SearchFormUri = "/form/search";
	private static final String SearchUri = "/search";
	private static final String ViewFormUri = "/form/view/{COUNTRY_ID}";
	private static final String DeleteUri = "/delete/{COUNTRY_ID}";
	private static final String BaseUri = "/fyfaSamples/service/rest/country";
	private final Context context = new Context();
	private final Dao dao;
	private final ViewFactory viewFactory;
	private final RenderingEngine renderingEngine;
	private final Form<CountryDo> formModify;
	private final Form<CountryDo> formNew;
	private final Form<CountryDo> formSearch;
	private final Form<CountryDo> formView;
	private final Table<CountryDo> tableList;

	public CountryRest( RenderingEngine renderingEngine, JdbcTemplate jdbcTemplate ) {
		this.renderingEngine = renderingEngine;
		this.dao = new Dao(jdbcTemplate);
		viewFactory = new ViewFactory( this.context, newPathSetting() );
		formModify = viewFactory.createFormForRowModifications();
		formNew = viewFactory.createFormForAddingNewRows();
		formSearch = viewFactory.createFormForSearchFilter();
		formView = viewFactory.createFormForViewingRow();
		tableList = viewFactory.createTableForViewingRows();
	}

	private PathSetting newPathSetting() {
		PathSetting pathSetting = new PathSetting( BaseUri );
		pathSetting.setTableUri( TableUri );
		pathSetting.setModifyFormUri( ModifyFormUri );
		pathSetting.setModifyUri( ModifyUri );
		pathSetting.setNewFormUri( NewFormUri );
		pathSetting.setNewUri( NewUri );
		pathSetting.setSearchFormUri( SearchFormUri );
		pathSetting.setSearchUri( SearchUri );
		pathSetting.setViewFormUri( ViewFormUri );
		pathSetting.setDeleteUri( DeleteUri );
		return pathSetting;
	}

	private String render( Form<CountryDo> form, CountryDo countryDo ) {
		String html = form.render( countryDo );
		return this.renderingEngine.render( html, form.getTitle() );
	}

	private String renderWithErrors( Form<CountryDo> form, CountryDo countryDo, ParseException e ) {
		String html = form.render( countryDo, e );
		return this.renderingEngine.render( html, form.getTitle() );
	}

	private String render( Table<CountryDo> table, List<CountryDo> list ) {
		String html = table.render( list );
		return this.renderingEngine.render( html, table.getTitle() );
	}

	private CountryDo parse( Form<CountryDo> form, MultivaluedMap<String, String> multivaluedMap ) throws ParseException {
		return form.parse( this.renderingEngine.toMap( multivaluedMap ), new CountryDo() );
	}

	/** Renders formModify - extracts a CountryDo object from the database based on COUNTRY_ID. Then renders the object in a form ready for the user to alter the field values of choice  */
	@GET
	@Path(ModifyFormUri)
	@Produces("text/html")
	public String formModify( @PathParam("COUNTRY_ID") String COUNTRY_ID ) {
		try {
			return render( formModify, dao.get( COUNTRY_ID ) );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Parses user input submitted from formModify and updates the actual data in the database. Then renders the tableList ready for more user interaction */
	@POST
	@Path(ModifyUri)
	@Produces("text/html")
	public String modify( MultivaluedMap<String, String> multivaluedMap ) {
		CountryDo countryDo = null;
		try {
			countryDo = parse( formModify, multivaluedMap );
			dao.putExisting( countryDo );
			return render( tableList, dao.find() );
		} catch (ParseException e) {
			return renderWithErrors( formModify, countryDo, e );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders formSearch - a form to accept data to issue a database search for existing CountryDo objects  */
	@GET
	@Path(SearchFormUri)
	@Produces("text/html")
	public String formSearch() {
		try {
			return render( formSearch, new CountryDo() );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Parses user input submitted from formSearch performs a search from the database. Then renders the extracted list of CountryDo objects in tableList */
	@POST
	@Path(SearchUri)
	@Produces("text/html")
	public String search( MultivaluedMap<String, String> multivaluedMap ) {
		CountryDo countryDo = null;
		try {
			countryDo = parse( formSearch, multivaluedMap );
			return render( tableList, dao.find( countryDo ) );
		} catch (ParseException e) {
			return renderWithErrors( formSearch, countryDo, e );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders formView - a read only view of one existing CountryDo object */
	@GET
	@Path(ViewFormUri)
	@Produces("text/html")
	public String formView( @PathParam("COUNTRY_ID") String COUNTRY_ID ) {
		try {
			return render( formView, dao.get( COUNTRY_ID ) );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders formNew - a form to accept data for new CountryDo objects  */
	@GET
	@Path(NewFormUri)
	@Produces("text/html")
	public String formNew() {
		try {
			return render( formNew, new CountryDo() );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Parses user input submitted from formNew and inserts the data in the database. Then renders the newForm ready for more user input  */
	@POST
	@Path(NewUri)
	@Produces("text/html")
	public String create( MultivaluedMap<String, String> multivaluedMap ) {
		CountryDo countryDo = null;
		try {
			countryDo = parse( formNew, multivaluedMap );
			dao.putNew( countryDo );
			return render( formNew, new CountryDo() );
		} catch (ParseException e) {
			return renderWithErrors( formNew, countryDo, e );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Executes the remove from database and renders the table view  */
	@GET
	@Path(DeleteUri)
	@Produces("text/html")
	public String delete( @PathParam("COUNTRY_ID") String COUNTRY_ID ) {
		try {
			dao.remove( COUNTRY_ID );
			return render( tableList, dao.find() );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders the table view of a list of CountryDo objects  */
	@GET
	@Path(TableUri)
	@Produces("text/html")
	public String table() {
		try {
			return render( tableList, dao.find() );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Downloads the Context */
	@GET
	@Path("context")
	@Produces("text/simple")
	public String viewContext() {
		return this.context.toString();
	}

}
