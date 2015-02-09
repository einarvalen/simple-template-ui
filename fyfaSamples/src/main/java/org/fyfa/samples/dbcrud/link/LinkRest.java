package org.fyfa.samples.dbcrud.link;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import org.fyfa.Context;
import org.fyfa.samples.RenderingEngine;
import org.fyfa.samples.dbcrud.PathSetting;
import org.fyfa.samples.dbcrud.Service;
import org.fyfa.samples.dbcrud.ViewFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/** Link is a CRUD show-case.
 *
 * @author EinarValen@gmail.com */
@Path("/dbcrud/link")
public class LinkRest {
	private static final String TableUri = "/table";
	private static final String ModifyFormUri = "/form/modify/{ssn}";
	private static final String ModifyUri = "/modify";
	private static final String NewFormUri = "/form/new";
	private static final String NewUri = "/new";
	private static final String SearchFormUri = "/form/search";
	private static final String SearchUri = "/search";
	private static final String ViewFormUri = "/form/view/{ssn}";
	private static final String DeleteUri = "/delete/{ssn}";
	private static final String BaseUri = "/fyfaSamples/service/rest/dbcrud/link";
	private final Context context = new Context();
	private final Service<LinkDo> service;

	public LinkRest( RenderingEngine renderingEngine, JdbcTemplate jdbcTemplate ) throws Exception {
		LinkDao dao = new LinkDao(jdbcTemplate);
		ViewFactory<LinkDo> viewFactory = new ViewFactory<LinkDo>(LinkDo.class, this.context, newPathSetting(), dao );
		this.service = new Service<LinkDo>( context, renderingEngine, viewFactory, dao);
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

	/** Renders formModify - extracts a LinkDo object from the database based on ssn. Then renders the object in a form ready for the user to alter the field values of choice  */
	@GET
	@Path(ModifyFormUri)
	@Produces("text/html")
	public String formModify( @PathParam("ssn") String key ) {
		return this.service.formModify( key );
	}

	/** Parses user input submitted from formModify and updates the actual data in the database. Then renders the tableList ready for more user interaction */
	@POST
	@Path(ModifyUri)
	@Produces("text/html")
	public String modify( MultivaluedMap<String, String> multivaluedMap ) {
		return this.service.modify(multivaluedMap, new LinkDo() );
	}

	/** Renders formSearch - a form to accept data to issue a database search for existing LinkDo objects  */
	@GET
	@Path(SearchFormUri)
	@Produces("text/html")
	public String formSearch() {
		return this.service.formSearch(new LinkDo());
	}

	/** Parses user input submitted from formSearch performs a search from the database. Then renders the extracted list of LinkDo objects in tableList */
	@POST
	@Path(SearchUri)
	@Produces("text/html")
	public String search( MultivaluedMap<String, String> multivaluedMap ) {
		return this.service.search(multivaluedMap, new LinkDo() );
	}

	/** Renders formView - a read only view of one existing LinkDo object */
	@GET
	@Path(ViewFormUri)
	@Produces("text/html")
	public String formView( @PathParam("ssn") String key ) {
		return this.service.formView(key);
	}

	/** Renders formNew - a form to accept data for new LinkDo objects  */
	@GET
	@Path(NewFormUri)
	@Produces("text/html")
	public String formNew() {
		return this.service.formNew(new LinkDo() );
	}

	/** Parses user input submitted from formNew and inserts the data in the database. Then renders the newForm ready for more user input  */
	@POST
	@Path(NewUri)
	@Produces("text/html")
	public String create( MultivaluedMap<String, String> multivaluedMap ) {
		return this.service.create(multivaluedMap, new LinkDo() );
	}

	/** Executes the remove from database and renders the table view  */
	@GET
	@Path(DeleteUri)
	@Produces("text/html")
	public String delete( @PathParam("ssn") String key ) {
		return this.service.delete(key);
	}

	/** Renders the table view of a list of LinkDo objects  */
	@GET
	@Path(TableUri)
	@Produces("text/html")
	public String table() {
		return this.service.table();
	}

	/** Downloads the Context */
	@GET
	@Path("context")
	@Produces("text/simple")
	public String viewContext() {
		return this.context.toString();
	}

}
