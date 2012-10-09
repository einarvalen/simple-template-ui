package org.fyfa.samples;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import org.fyfa.Context;
import org.fyfa.Template;
import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.components.Form;
import org.fyfa.components.FormParams;
import org.fyfa.components.ParseException;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;
import org.fyfa.ids.TemplateId;
import org.fyfa.nls.NlsUs;
import org.fyfa.templatetypes.TemplateTypeDefault;

/**
 * Adds two custom html-templates and uses them in TemplateDo.text and TemplateDo.br,
 * and forces the use of a suitable template for TemplateDo.password.
 * Additionally, how to override template definitions using a property file is show-cased.
 *
 * @author EinarValen@gmail.com */
@Path("/template")
public class TemplateRest {
	private static final String MY_BREAK_TEMPLATE_ID = "MyBreak", MY_TEXTAREA_TEMPLATE_ID = "MyTemplateId";
	private final Context context;
	private final RenderingEngine renderingEngine;
	private final Form<TemplateDo> form;
	private final Table<TemplateDo> table;

	/** The DTO/domain object */
	static class TemplateDo {
		@FieldDef(description = "Your full name", maxLength = 60)
		String name;

		/* Sets the field hard. */
		@FieldDef(fieldId = "TemplateDo_text")
		/* Associate the new template created in addNewTemplates() */
		@ItemDefFormNew(templateId = MY_TEXTAREA_TEMPLATE_ID)
		String text;

		/* Requests another template than what is suggested by default */
		@ItemDefFormNew(templateId = "TemplateTypeDefaultinputPassword")
		String password;

		/* Associate the new template created in addNewTemplates() */
		@ItemDefFormNew(templateId = MY_BREAK_TEMPLATE_ID)
		String br;
	}

	public TemplateRest( RenderingEngine renderingEngine ) {
		this.renderingEngine = renderingEngine;
		// Override existing template definitions by those in this property file
		this.context = new Context( new NlsUs(), "template.properties" );
		addNewTemplates();
		this.form = newForm();
		this.table = newTable();
	}

	/** Creates two new custom templates and register them with the Context. */
	private void addNewTemplates() {
		String fmtNew = "<tr><td colspan=2 ><label for='$ID$'>$LABEL$(Addition)</label></td></tr><tr><td colspan=2><textarea name='$FIELD_ID$' title='$DESCRIPTION$' rows='2' cols='$MAXLENGTH$'>$VALUE$</textarea></td></tr><tr><td colspan=2 class='error'>$HINT$</td></tr>\n";
		Template template = new Template( new TemplateId( "MyTemplateId" ), TemplateTypeDefault.ID, fmtNew );
		List<Template> templateList = new ArrayList<Template>();
		templateList.add( template );
		templateList.add( new Template( new TemplateId( MY_BREAK_TEMPLATE_ID ), TemplateTypeDefault.ID, "<tr><td colspan=2><hr/></td></tr>" ) );
		this.context.setTemplateList( templateList );
	}

	private Form<TemplateDo> newForm() {
		FormParams<TemplateDo> params = new FormParams<TemplateDo>( "TemplateDo", TemplateDo.class, this.context );
		params.setFormActionUri( "/fyfaSamples/service/rest/template/response" );
		params.setColumnNamesInSequence( "password", "br", "name", "br", "text", "br" );
		return new Form<TemplateDo>( params );
	}

	private Table<TemplateDo> newTable() {
		TableParams<TemplateDo> params = new TableParams<TemplateDo>( "TemplateDo", TemplateDo.class, this.context );
		params.setColumnNamesInSequence( "password", "name", "text" );
		return new Table<TemplateDo>( params );
	}

	/**	Renders html for an input form tailored to TemplateRest */
	@GET
	@Path("/form")
	@Produces("text/html")
	public String form() {
		final String Description = "<p>Shows a number of different ways to customize templates - how to override existing templates using a property file, "
				+ "- Adds two custom html-templates and uses them in Text and the seperator lines. Also demonstrates how to select a suitable template for Password.</p><p align='center'>"
				+ "<a href='context'>View&nbsp;Context</a>.  <a href='form/items'>View&nbsp;Form&nbsp;Items</a>.</p>";
		try {
			String html = Description + form.render( new TemplateDo() );
			return this.renderingEngine.render( html, "Template Form" );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/**	Parses input from the submitted html-form, into a TemplateRest object,
	 *  duplicates it several times then renderes the list of duplicates as an html-table */
	@POST
	@Path("/response")
	@Produces("text/html")
	public String table( MultivaluedMap<String, String> multivaluedMap ) {
		TemplateDo templateDo = new TemplateDo();
		try {
			templateDo = this.form.parse( this.renderingEngine.toMap( multivaluedMap ), templateDo );
			List<TemplateDo> list = createList( templateDo );
			String html = table.render( list );
			return this.renderingEngine.render( html, "Template Table" );
		} catch (ParseException e) {
			String html = form.render( templateDo, e );
			return this.renderingEngine.render( html, "Template Form" );
		} catch (Exception e) {
			throw this.renderingEngine.handleExceptionHtml( e );
		}
	}

	/** Download the Context */
	@GET
	@Path("context")
	@Produces("text/simple")
	public String viewContext() {
		return this.context.toString();
	}

	/** Download the form.items */
	@GET
	@Path("form/items")
	@Produces("text/simple")
	public String viewFormItems() {
		return this.form.getItems().toString();
	}

	private List<TemplateDo> createList( TemplateDo templateDo ) {
		List<TemplateDo> list = new ArrayList<TemplateDo>();
		for (int i = 0; i < 10; i++) {
			list.add( templateDo );
		}
		return list;
	}

}
