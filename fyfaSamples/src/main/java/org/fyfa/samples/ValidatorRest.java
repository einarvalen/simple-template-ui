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
import org.fyfa.InvalidInputException;
import org.fyfa.Validator;
import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.ParseException;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;
import org.fyfa.ids.ValidatorId;

/**
 * Defines a Validator for ValidatorDo.age that will require ages entered to be between 18 and 35
 *
 * @author EinarValen@gmail.com */
@Path("/validator")
public class ValidatorRest {
	private final Context context = new Context();
	private final RenderingEngine renderingEngine;
	private final static String[] fieldnamesInSequence = { "name", "age", "height", "width", "date", "married" };
	private final Form<ValidatorDo> form = newForm();
	private final Table<ValidatorDo> table = newTable();

	/** The DTO/domain object */
	static class ValidatorDo {
		@FieldDef(description = "Your full name", maxLength = 60)
		@ItemDefFormNew(required = true)
		String name;

		@FieldDef(label = "Age", description = "Preferred age of dating partner. In years.", maxLength = 2)
		@ItemDefFormNew(required = true, validatorId = "AgeValidator")
		Integer age;

		@FieldDef(label = "Favorit height", description = "for dating partner. In cm.", maxLength = 3)
		double height;

		@FieldDef(label = "Favorit width", description = " for dating partner. In cm.", maxLength = 3)
		float width;

		@FieldDef(label = "Favorit date", description = "Best date so far", maxLength = 10)
		Date date;

		boolean married;
	}

	/** Custom Validator that require ages entered to be between 18 and 35 */
	static class AgeValidator implements Validator {
		@Override
		public void check( Object value ) throws InvalidInputException {
			if (value instanceof Integer) {
				int age = (Integer)value;
				if (18 <= age && age <= 35) return;
			}
			throw new InvalidInputException( "Must be between 18 and 35." );
		}

		@Override
		public ValidatorId getId() {
			return new ValidatorId( "AgeValidator" );
		}
	}

	public ValidatorRest( RenderingEngine renderingEngine ) {
		this.renderingEngine = renderingEngine;
		context.registerValidator( new AgeValidator() ); // The validator must be registerd with the Context
	}

	private Form<ValidatorDo> newForm() {
		FormParams<ValidatorDo> params = new FormParams<ValidatorDo>( "Validator", ValidatorDo.class, this.context );
		params.setColumnNamesInSequence( fieldnamesInSequence );
		params.setFormActionUri( "/fyfaSamples/service/rest/validator/response" );
		return new Form<ValidatorDo>( params );
	}

	private Table<ValidatorDo> newTable() {
		TableParams<ValidatorDo> params = new TableParams<ValidatorDo>( "Validator", ValidatorDo.class, this.context );
		params.setColumnNamesInSequence( fieldnamesInSequence );
		return new Table<ValidatorDo>( params );
	}

	/**	Renders html for an input form tailored to FieldTypeDo */
	@GET
	@Path("/form")
	@Produces("text/html")
	public String form() {
		final String Description = "<p>Defines a Validator for Age that will require ages entered to be between 18 and 35.</p><p> <a href='context'>View&nbsp;Context</a>.</p>";
		try {
			String html = Description + form.render( new ValidatorDo() );
			return this.renderingEngine.render( html, "Validator Form" );
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
		ValidatorDo validatorDo = new ValidatorDo();
		try {
			validatorDo = this.form.parse( this.renderingEngine.toMap( multivaluedMap ), validatorDo );
			List<ValidatorDo> list = createList( validatorDo );
			String html = table.render( list );
			return this.renderingEngine.render( html, "Validator Table" );
		} catch (ParseException e) {
			String html = form.render( validatorDo, e );
			return this.renderingEngine.render( html, "Validator Form" );
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

	private List<ValidatorDo> createList( ValidatorDo validatorDo ) {
		List<ValidatorDo> list = new ArrayList<ValidatorDo>();
		for (int i = 0; i < 10; i++) {
			list.add( validatorDo );
		}
		return list;
	}

}
