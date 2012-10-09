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
import org.fyfa.Operation;
import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.ParseException;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;

/**
 * Like SimpleRest.java, but adds annotation based overrides of the default assumptions.
 * Context is populated with values extracted from AnnotationDo and FieldDef annotations in concert.
 * The dialogs are tailored accordingly and conducted according to default behavior except in forms with Operation.New, name and age will be required input fields.
 * The FieldDef annotations defines label and description of a data-item in any situation.
 * ItemDef annotations defines behavior related to a data-item i certain situations; here: in a NewForm - I.E a form that has the operation parameter set to New.
 *
 * @author EinarValen@gmail.com */
@Path("/annotation")
public class AnnotationRest {
	private final Context context = new Context();
	private final RenderingEngine renderingEngine;
	private final Form<AnnotationDo> form;
	private final Table<AnnotationDo> table;

	/** The DTO/domain object */
	static class AnnotationDo {
		@FieldDef(description = "Your full name", maxLength = 60)
		@ItemDefFormNew(required = true)
		// name is required in forms that has the operation parameter set to New
		String name;
		@FieldDef(label = "Age", description = "Preferred age of dating partner. In years.", maxLength = 2)
		@ItemDefFormNew(required = true)
		// age is required in forms that has the operation parameter set to New
		Integer age;
		@FieldDef(label = "Favorit height", description = "for dating partner. In cm.", maxLength = 3)
		double height;
		@FieldDef(label = "Favorit width", description = " for dating partner. In cm.", maxLength = 3)
		float width;
		@FieldDef(label = "Favorit date", description = "Best date so far", maxLength = 10)
		Date date;
	}

	public AnnotationRest( RenderingEngine renderingEngine ) {
		this.renderingEngine = renderingEngine;
		this.form = newForm();
		this.table = newTable();
	}

	private Form<AnnotationDo> newForm() {
		FormParams<AnnotationDo> params = new FormParams<AnnotationDo>( "Annotation", AnnotationDo.class, this.context );
		params.setColumnNamesInSequence( new String[] { "name", "age", "height", "width", "date" } );
		params.setFormActionUri( "/fyfaSamples/service/rest/annotation/response" );
		params.setOperation( Operation.New ); // The operation parameter is default set to New
		return new Form<AnnotationDo>( params );
	}

	private Table<AnnotationDo> newTable() {
		TableParams<AnnotationDo> params = new TableParams<AnnotationDo>( "Annotation", AnnotationDo.class, this.context );
		return new Table<AnnotationDo>( params );
	}

	/**	Renders html for an input form tailored to AnnotationDo */
	@GET
	@Path("/form")
	@Produces("text/html")
	public String form() {
		final String Description = "Like SimpleRest.java, but adds annotation based overrides of the default assumptions.";
		try {
			String html = Description + form.render( new AnnotationDo() );
			return this.renderingEngine.render( html, "Annotation Form" );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/**	Parses input from the submitted html-form, into a AnnotationDo object,
	 *  duplicates it several times then renderes the list of duplicates as an html-table */
	@POST
	@Path("/response")
	@Produces("text/html")
	public String table( MultivaluedMap<String, String> multivaluedMap ) {
		AnnotationDo annotationDo = new AnnotationDo();
		try {
			annotationDo = this.form.parse( this.renderingEngine.toMap( multivaluedMap ), annotationDo );
			List<AnnotationDo> list = createList( annotationDo );
			String html = table.render( list );
			return this.renderingEngine.render( html, "Annotation Table" );
		} catch (ParseException e) {
			String html = form.render( annotationDo, e );
			return this.renderingEngine.render( html, "Annotation Form" );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	private List<AnnotationDo> createList( AnnotationDo annotationDo ) {
		List<AnnotationDo> list = new ArrayList<AnnotationDo>();
		for (int i = 0; i < 10; i++) {
			list.add( annotationDo );
		}
		return list;
	}

}
