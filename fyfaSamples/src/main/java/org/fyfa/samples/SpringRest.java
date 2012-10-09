package org.fyfa.samples;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import org.fyfa.Context;
import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.ParseException;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;

/**
 * Shows how to load the context form Spring.
 *
 * @author EinarValen@gmail.com */
@Path("/spring")
public class SpringRest {
	private final Context context;
	private final RenderingEngine renderingEngine;
	private final Form<SpringRestDo> form;
	private final Table<SpringRestDo> table;

	/** The DTO/domain object */
	static class SpringRestDo {
		@FieldDef(description = "Your full name", maxLength = 60)
		String name;

		@FieldDef(fieldId = "SpringRestDo_text")
		@ItemDefFormNew(templateId = "MyTextareaTemplateId")
		String text;

		@ItemDefFormNew(templateId = "TemplateTypeDefaultinputPassword")
		String password;

		@ItemDefFormNew(templateId = "MyBreak")
		String br;
	}

	/** See src/main/webapp/WEB-INF/springContext.xml */
	public SpringRest( RenderingEngine renderingEngine, Context context ) {
		this.context = context;
		this.renderingEngine = renderingEngine;
		this.form = newForm();
		this.table = newTable();
	}

	private Form<SpringRestDo> newForm() {
		FormParams<SpringRestDo> params = new FormParams<SpringRestDo>( "SpringRestDo", SpringRestDo.class, this.context );
		params.setFormActionUri( "/fyfaSamples/service/rest/template/response" );
		params.setColumnNamesInSequence( "password", "br", "name", "br", "text", "br" );
		return new Form<SpringRestDo>( params );
	}

	private Table<SpringRestDo> newTable() {
		TableParams<SpringRestDo> params = new TableParams<SpringRestDo>( "SpringRestDo", SpringRestDo.class, this.context );
		params.setColumnNamesInSequence( "password", "name", "text" );
		return new Table<SpringRestDo>( params );
	}

	/**	Renders html for an input form tailored to SpringRestDo */
	@GET
	@Path("/form")
	@Produces("text/html")
	public String form() {
		final String Description = "<p>Shows how to load the context form Spring.</p><p> <a href='context'>View&nbsp;Context</a>.  <a href='form/items'>View&nbsp;Form&nbsp;Items</a>.</p>";
		try {
			String html = Description + form.render( new SpringRestDo() );
			return this.renderingEngine.render( html, "Spring Form" );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/**	Parses input from the submitted html-form, into a SpringRestDo object,
	 *  duplicates it several times then renderes the list of duplicates as an html-table */
	@POST
	@Path("/response")
	@Produces("text/html")
	public String table( MultivaluedMap<String, String> multivaluedMap ) {
		SpringRestDo SpringRestDo = new SpringRestDo();
		try {
			SpringRestDo = this.form.parse( this.renderingEngine.toMap( multivaluedMap ), SpringRestDo );
			List<SpringRestDo> list = createList( SpringRestDo );
			String html = table.render( list );
			return this.renderingEngine.render( html, "Spring Table" );
		} catch (ParseException e) {
			String html = form.render( SpringRestDo, e );
			return this.renderingEngine.render( html, "Spring Form" );
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

	/** Downloads the Items in the Form */
	@GET
	@Path("form/items")
	@Produces("text/simple")
	public String viewFormItems() {
		return this.form.getItems().toString();
	}

	private List<SpringRestDo> createList( SpringRestDo SpringRestDo ) {
		List<SpringRestDo> list = new ArrayList<SpringRestDo>();
		for (int i = 0; i < 10; i++) {
			list.add( SpringRestDo );
		}
		return list;
	}

}
