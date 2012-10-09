package org.fyfa.samples;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import org.fyfa.Context;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.ParseException;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;

/**
 * The very simplest Fyfa usage -- exercising default behavior, only.
 * Context is populated with default values and info extracted from the SimpleDo fields.
 * The dialogs are tailored to SimpleDo and conducted according to default behavior.
 *
 * @author EinarValen@gmail.com */
@Path("/simple")
public class SimpleRest {
	private final Context context = new Context();
	private final RenderingEngine renderingEngine;
	private final Form<SimpleDo> form;
	private final Table<SimpleDo> table;

	/** The domain object */
	static class SimpleDo {
		String name;
		int age;
		double height;
		float width;
		Date date;
	}

	public SimpleRest( RenderingEngine renderingEngine ) {
		this.renderingEngine = renderingEngine;
		this.form = newForm();
		this.table = newTable();
	}

	private Form<SimpleDo> newForm() {
		FormParams<SimpleDo> params = new FormParams<SimpleDo>( "Simple", SimpleDo.class, this.context );
		params.setFormActionUri( "/fyfaSamples/service/rest/simple/response" );
		return new Form<SimpleDo>( params );
	}

	private Table<SimpleDo> newTable() {
		TableParams<SimpleDo> params = new TableParams<SimpleDo>( "Simple", SimpleDo.class, this.context );
		return new Table<SimpleDo>( params );
	}

	/**	Renders html for an input form tailored to SimpleDo */
	@GET
	@Path("/form")
	@Produces("text/html")
	public String form() {
		final String Description = "The very simplest Fyfa usage -- exercising default behavior, only.";
		try {
			String html = Description + form.render( new SimpleDo() );
			return this.renderingEngine.render( html, "Simple Form" );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/**	Parses input from the submitted html-form, into a SimpleDo object,
	 *  duplicates it several times then renderes the list of duplicates as an html-table */
	@POST
	@Path("/response")
	@Produces("text/html")
	public String table( MultivaluedMap<String, String> multivaluedMap ) {
		SimpleDo simpleDo = new SimpleDo();
		try {
			simpleDo = this.form.parse( this.renderingEngine.toMap( multivaluedMap ), simpleDo );
			List<SimpleDo> list = createList( simpleDo );
			String html = table.render( list );
			return this.renderingEngine.render( html, "Simple Table" );
		} catch (ParseException e) {
			String html = form.render( simpleDo, e );
			return this.renderingEngine.render( html, "Simple Form" );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	private List<SimpleDo> createList( SimpleDo simpleDo ) {
		List<SimpleDo> list = new ArrayList<SimpleDo>();
		for (int i = 0; i < 10; i++) {
			list.add( simpleDo );
		}
		return list;
	}

}
