package org.fyfa.samples.dbcrud.reference;

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
import org.fyfa.samples.dbcrud.DaoParams;
import org.fyfa.samples.dbcrud.PathSetting;
import org.fyfa.samples.dbcrud.Service;
import org.fyfa.samples.dbcrud.ViewFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/** Link is a CRUD show-case.
 *
 * @author EinarValen@gmail.com */
@Path("/sbu")
public class SbuRest {
	private static final String TableUri = "/table";
	private static final String ModifyFormUri = "/form/modify/{SBU_ID}";
	private static final String ModifyUri = "/modify";
	private static final String NewFormUri = "/form/new";
	private static final String NewUri = "/new";
	private static final String SearchFormUri = "/form/search";
	private static final String SearchUri = "/search";
	private static final String ViewFormUri = "/form/view/{SBU_ID}";
	private static final String DeleteUri = "/delete/{SBU_ID}";
	private static final String BaseUri = "/fyfaSamples/service/rest/sbu";
	private final Context context = new Context();
	private final Service<SbuDo> service;

	public SbuRest( RenderingEngine renderingEngine, JdbcTemplate jdbcTemplate ) throws Exception {
		DaoParams daoParams = new DaoParams();
		daoParams.setColumnsForAddingNewRows(new String[]{"SBU_ID", "SBU_KEY", "SBU_NAME"});
		daoParams.setColumnsForListView(new String[]{"SBU_ID", "SBU_KEY", "SBU_NAME"});
		daoParams.setColumnsForSearchFilter(new String[]{"SBU_ID", "SBU_KEY", "SBU_NAME"});
		daoParams.setColumnsForRowModifications(new String[]{"SBU_ID","SBU_KEY", "SBU_NAME"});
		daoParams.setColumnsForRowModificationsDao(new String[]{"SBU_KEY", "SBU_NAME"});
		daoParams.setTableName("ODS.REF_SBU");
		daoParams.setDatabaseName("EMEA_ODS");
		daoParams.setColumnKey("SBU_ID");
		ViewFactory<SbuDo> viewFactory = new ViewFactory<SbuDo>(SbuDo.class, this.context, newPathSetting(), daoParams );
		this.service = new Service<SbuDo>( context, renderingEngine, viewFactory, new SbuDao(jdbcTemplate, daoParams ));
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

	/** Renders formModify - extracts a SbuDo object from the database based on SBU_ID. Then renders the object in a form ready for the user to alter the field values of choice  */
	@GET
	@Path(ModifyFormUri)
	@Produces("text/html")
	public String formModify( @PathParam("SBU_ID") String key ) {
		return this.service.formModify( key );
	}

	/** Parses user input submitted from formModify and updates the actual data in the database. Then renders the tableList ready for more user interaction */
	@POST
	@Path(ModifyUri)
	@Produces("text/html")
	public String modify( MultivaluedMap<String, String> multivaluedMap ) {
		return this.service.modify(multivaluedMap, new SbuDo() );
	}

	/** Renders formSearch - a form to accept data to issue a database search for existing SbuDo objects  */
	@GET
	@Path(SearchFormUri)
	@Produces("text/html")
	public String formSearch() {
		return this.service.formSearch(new SbuDo());
	}

	/** Parses user input submitted from formSearch performs a search from the database. Then renders the extracted list of SbuDo objects in tableList */
	@POST
	@Path(SearchUri)
	@Produces("text/html")
	public String search( MultivaluedMap<String, String> multivaluedMap ) {
		return this.service.search(multivaluedMap, new SbuDo() );
	}

	/** Renders formView - a read only view of one existing SbuDo object */
	@GET
	@Path(ViewFormUri)
	@Produces("text/html")
	public String formView( @PathParam("SBU_ID") String key ) {
		return this.service.formView(key);
	}

	/** Renders formNew - a form to accept data for new SbuDo objects  */
	@GET
	@Path(NewFormUri)
	@Produces("text/html")
	public String formNew() {
		return this.service.formNew(new SbuDo() );
	}

	/** Parses user input submitted from formNew and inserts the data in the database. Then renders the newForm ready for more user input  */
	@POST
	@Path(NewUri)
	@Produces("text/html")
	public String create( MultivaluedMap<String, String> multivaluedMap ) {
		return this.service.create(multivaluedMap, new SbuDo() );
	}

	/** Executes the remove from database and renders the table view  */
	@GET
	@Path(DeleteUri)
	@Produces("text/html")
	public String delete( @PathParam("SBU_ID") String key ) {
		return this.service.delete(key);
	}

	/** Renders the table view of a list of SbuDo objects  */
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
