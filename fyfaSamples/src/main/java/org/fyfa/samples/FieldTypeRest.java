package org.fyfa.samples;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import org.fyfa.Context;
import org.fyfa.FieldType;
import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.ParseException;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;
import org.fyfa.ids.FieldTypeId;

/**
 * Defines a custom FieldType for FieldTypeDo.hourOfDay, MyHourOfDayFieldType.
 *
 * @author EinarValen@gmail.com */
@Path("/fieldtype")
public class FieldTypeRest {
	private final RenderingEngine renderingEngine;
	private final Context context;
	private final Form<FieldTypeDo> form;
	private final Table<FieldTypeDo> table;

	/** The DTO/domain object */
	static class FieldTypeDo {
		@FieldDef(description = "Your full name", maxLength = 60)

		@ItemDefFormNew(required = true)
		String name;

		@FieldDef(fieldTypeId = "HourOfDay", label = "Favorit time of day", description = "To the minute.", maxLength = 5)
		String hourOfDay;
	}

	/** Custom FieldType to handle hour-of-day type fields */
	static class MyHourOfDayFieldType implements FieldType<String> {
		public static FieldTypeId ID = new FieldTypeId( "HourOfDay" );
		private final FieldTypeId id;

		public MyHourOfDayFieldType() {
			this( ID );
		}

		public MyHourOfDayFieldType( FieldTypeId id ) {
			this.id = id;
		}

		@Override
		public String format( String value ) {
			if (value == null) return "";
			return value.toString();
		}

		@Override
		public String parse( String value ) {
			String[] strings = value.split( ":" );
			if (strings.length == 2) {
				int hour = Integer.parseInt( strings[0] );
				int minute = Integer.parseInt( strings[1] );
				if (0 <= hour && hour <= 24 && 0 <= minute && minute <= 59) {
					return value;
				}
			}
			throw new IllegalArgumentException( String.format( "HourOfDay expressed like this: %s Does not compute.", value ) );
		}

		@Override
		public String getHint() {
			return "13:59";
		}

		@Override
		public FieldTypeId getId() {
			return id;
		}

	}

	public FieldTypeRest( RenderingEngine renderingEngine ) {
		this.renderingEngine = renderingEngine;
		this.context = new Context();
		this.context.getFieldTypes().put( new MyHourOfDayFieldType() ); // Register the custom FieldType with the Context
		this.form = newForm();
		this.table = newTable();

	}

	private Form<FieldTypeDo> newForm() {
		FormParams<FieldTypeDo> params = new FormParams<FieldTypeDo>( "FieldType", FieldTypeDo.class, this.context );
		params.setFormActionUri( "/fyfaSamples/service/rest/fieldtype/response" );
		return new Form<FieldTypeDo>( params );
	}

	private Table<FieldTypeDo> newTable() {
		TableParams<FieldTypeDo> params = new TableParams<FieldTypeDo>( "FieldType", FieldTypeDo.class, this.context );
		return new Table<FieldTypeDo>( params );
	}

	/**	Renders html for an input form tailored to FieldTypeDo */
	@GET
	@Path("/form")
	@Produces("text/html")
	public String form() {
		final String Description = "<p>Defines a custom FieldType hour-of-day type fields (Ref. 'Favorit time of day').</p><p> <a href='context'>View&nbsp;Context</a>.</p>";
		try {
			String html = Description + form.render( new FieldTypeDo() );
			return this.renderingEngine.render( html, "FieldType Form" );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/**	Parses input from the submitted html-form, into a FieldTypeDo object,
	 *  duplicates it several times then renderes the list of duplicates as an html-table */
	@POST
	@Path("/response")
	@Produces("text/html")
	public String table( MultivaluedMap<String, String> multivaluedMap ) {
		FieldTypeDo fieldtypeDo = new FieldTypeDo();
		try {
			fieldtypeDo = this.form.parse( this.renderingEngine.toMap( multivaluedMap ), fieldtypeDo );
			List<FieldTypeDo> list = createList( fieldtypeDo );
			String html = table.render( list );
			return this.renderingEngine.render( html, "FieldType Table" );
		} catch (ParseException e) {
			String html = form.render( fieldtypeDo, e );
			return this.renderingEngine.render( html, "FieldType Form" );
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

	private List<FieldTypeDo> createList( FieldTypeDo fieldtypeDo ) {
		List<FieldTypeDo> list = new ArrayList<FieldTypeDo>();
		for (int i = 0; i < 10; i++) {
			list.add( fieldtypeDo );
		}
		return list;
	}

}
