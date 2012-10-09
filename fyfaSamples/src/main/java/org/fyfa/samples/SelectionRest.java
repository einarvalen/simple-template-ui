package org.fyfa.samples;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import org.fyfa.Context;
import org.fyfa.Selection;
import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.ParseException;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;
import org.fyfa.ids.SelectionId;

/**
 * Like AnnotationRest.java, but adds a drop down selection list.
 * SelectionMarried implements Selection and is registered with the Context.
 * By using the annotation ItemDefFormNew, SelectionMarried is associated with SelecftionDo.married in forms whith the FprmParams.operation set to Operation.New
 *
 * @author EinarValen@gmail.com */
@Path("/selection")
public class SelectionRest {
	private final Context context = new Context();
	private final RenderingEngine renderingEngine;
	private final static String[] fieldnamesInSequence = { "name", "age", "height", "width", "date", "married" };
	private final Form<SelectionDo> form = newForm();
	private final Table<SelectionDo> table = newTable();

	/** The DTO/domain object */
	static class SelectionDo {
		@FieldDef(description = "Your full name", maxLength = 60)
		@ItemDefFormNew(required = true)
		String name;

		@FieldDef(label = "Age", description = "Preferred age of dating partner. In years.", maxLength = 2)
		Integer age;

		@FieldDef(label = "Favorit height", description = "for dating partner. In cm.", maxLength = 3)
		@ItemDefFormNew(required = true)
		double height;

		@FieldDef(label = "Favorit width", description = " for dating partner. In cm.", maxLength = 3)
		float width;

		@FieldDef(label = "Favorit date", description = "Best date so far", maxLength = 10)
		Date date;

		@ItemDefFormNew(selectionId = "marriedSelectionId")
		boolean married;
	}

	/** The selection backing class */
	static class SelectionMarried implements Selection {
		@Override
		public Map<String, String> getSelection() {
			Map<String, String> map = new HashMap<String, String>();
			map.put( "true", "Married" );
			map.put( "false", "Not married" );
			return map;
		}

		@Override
		public SelectionId getId() {
			return new SelectionId( "marriedSelectionId" );
		}
	}

	public SelectionRest( RenderingEngine renderingEngine ) {
		this.renderingEngine = renderingEngine;
		context.registerSelecton( new SelectionMarried() ); // The Selection must be registered with the Context
	}

	private Form<SelectionDo> newForm() {
		FormParams<SelectionDo> params = new FormParams<SelectionDo>( "Selection", SelectionDo.class, this.context );
		params.setColumnNamesInSequence( fieldnamesInSequence );
		params.setFormActionUri( "/fyfaSamples/service/rest/selection/response" );
		return new Form<SelectionDo>( params );
	}

	private Table<SelectionDo> newTable() {
		TableParams<SelectionDo> params = new TableParams<SelectionDo>( "Selection", SelectionDo.class, this.context );
		params.setColumnNamesInSequence( fieldnamesInSequence );
		return new Table<SelectionDo>( params );
	}

	/**	Renders html for an input form tailored to SelectionDo */
	@GET
	@Path("/form")
	@Produces("text/html")
	public String form() {
		String Description = "Like AnnotationRest.java, but adds a drop down selection list.";
		try {
			String html = Description + form.render( new SelectionDo() );
			return this.renderingEngine.render( html, "Selection Form" );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/**	Parses input from the submitted html-form, into a SelectionDo object,
	 *  duplicates it several times then renderes the list of duplicates as an html-table */
	@POST
	@Path("/response")
	@Produces("text/html")
	public String table( MultivaluedMap<String, String> multivaluedMap ) {
		SelectionDo selectionDo = new SelectionDo();
		try {
			selectionDo = this.form.parse( this.renderingEngine.toMap( multivaluedMap ), selectionDo );
			List<SelectionDo> list = createList( selectionDo );
			String html = table.render( list );
			return this.renderingEngine.render( html, "Selection Table" );
		} catch (ParseException e) {
			String html = form.render( selectionDo, e );
			return this.renderingEngine.render( html, "Selection Form" );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	@GET
	@Path("context")
	@Produces("text/simple")
	public String viewContext() {
		return this.context.toString();
	}

	private List<SelectionDo> createList( SelectionDo selectionDo ) {
		List<SelectionDo> list = new ArrayList<SelectionDo>();
		for (int i = 0; i < 10; i++) {
			list.add( selectionDo );
		}
		return list;
	}

}
