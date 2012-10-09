package org.fyfa.samples.link;

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

/** Link is a CRUD show-case.
 *
 * @author EinarValen@gmail.com */
@Path("/link")
public class LinkRest {
	private static final String QuitUri = "/fyfaSamples";
	private static final String TableUri = "/table";
	private static final String ModifyFormUri = "/form/modify/{ssn}";
	private static final String ModifyUri = "/modify";
	private static final String NewFormUri = "/form/new";
	private static final String NewUri = "/new";
	private static final String SearchFormUri = "/form/search";
	private static final String SearchUri = "/search";
	private static final String ViewFormUri = "/form/view/{ssn}";
	private static final String DeleteUri = "/delete/{ssn}";
	private static final String BaseUri = "/fyfaSamples/service/rest/link";
	private final Context context = new Context();
	private final Dao dao = new Dao();
	private final ViewFactory viewFactory;
	private final RenderingEngine renderingEngine;
	private final Form<LinkDo> formModify;
	private final Form<LinkDo> formNew;
	private final Form<LinkDo> formSearch;
	private final Form<LinkDo> formView;
	private final Table<LinkDo> tableList;

	public LinkRest( RenderingEngine renderingEngine ) {
		this.renderingEngine = renderingEngine;
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

	private String render( Form<LinkDo> form, LinkDo linkDo ) {
		String html = form.render( linkDo );
		return this.renderingEngine.render( html, form.getTitle() );
	}

	private String renderWithErrors( Form<LinkDo> form, LinkDo linkDo, ParseException e ) {
		String html = form.render( linkDo, e );
		return this.renderingEngine.render( html, form.getTitle() );
	}

	private String render( Table<LinkDo> table, List<LinkDo> list ) {
		String html = table.render( list );
		return this.renderingEngine.render( html, table.getTitle() );
	}

	private LinkDo parse( Form<LinkDo> form, MultivaluedMap<String, String> multivaluedMap ) throws ParseException {
		return form.parse( this.renderingEngine.toMap( multivaluedMap ), new LinkDo() );
	}

	/** Renders formModify - extracts a LinkDo object from the database based on ssn. Then renders the object in a form ready for the user to alter the field values of choice  */
	@GET
	@Path(ModifyFormUri)
	@Produces("text/html")
	public String formModify( @PathParam("ssn") String ssn ) {
		try {
			return render( formModify, dao.get( ssn ) );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Parses user input submitted from formModify and updates the actual data in the database. Then renders the tableList ready for more user interaction */
	@POST
	@Path(ModifyUri)
	@Produces("text/html")
	public String modify( MultivaluedMap<String, String> multivaluedMap ) {
		LinkDo linkDo = null;
		try {
			linkDo = parse( formModify, multivaluedMap );
			dao.put( linkDo );
			return render( tableList, dao.find() );
		} catch (ParseException e) {
			return renderWithErrors( formModify, linkDo, e );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders formSearch - a form to accept data to issue a database search for existing LinkDo objects  */
	@GET
	@Path(SearchFormUri)
	@Produces("text/html")
	public String formSearch() {
		try {
			return render( formSearch, new LinkDo() );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Parses user input submitted from formSearch performs a search from the database. Then renders the extracted list of LinkDo objects in tableList */
	@POST
	@Path(SearchUri)
	@Produces("text/html")
	public String search( MultivaluedMap<String, String> multivaluedMap ) {
		LinkDo linkDo = null;
		try {
			linkDo = parse( formSearch, multivaluedMap );
			return render( tableList, dao.find( linkDo ) );
		} catch (ParseException e) {
			return renderWithErrors( formSearch, linkDo, e );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders formView - a read only view of one existing LinkDo object */
	@GET
	@Path(ViewFormUri)
	@Produces("text/html")
	public String formView( @PathParam("ssn") String ssn ) {
		try {
			return render( formView, dao.get( ssn ) );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders formNew - a form to accept data for new LinkDo objects  */
	@GET
	@Path(NewFormUri)
	@Produces("text/html")
	public String formNew() {
		try {
			return render( formNew, new LinkDo() );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Parses user input submitted from formNew and inserts the data in the database. Then renders the newForm ready for more user input  */
	@POST
	@Path(NewUri)
	@Produces("text/html")
	public String create( MultivaluedMap<String, String> multivaluedMap ) {
		LinkDo linkDo = null;
		try {
			linkDo = parse( formNew, multivaluedMap );
			dao.put( linkDo );
			return render( formNew, new LinkDo() );
		} catch (ParseException e) {
			return renderWithErrors( formNew, linkDo, e );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Executes the remove from database and renders the table view  */
	@GET
	@Path(DeleteUri)
	@Produces("text/html")
	public String delete( @PathParam("ssn") String ssn ) {
		try {
			dao.remove( ssn );
			return render( tableList, dao.find() );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Renders the table view of a list of LinkDo objects  */
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
