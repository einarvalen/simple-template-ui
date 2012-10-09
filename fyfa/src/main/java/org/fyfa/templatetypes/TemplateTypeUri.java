package org.fyfa.templatetypes;

import java.util.Map;

import org.fyfa.Template;
import org.fyfa.TemplateParams;
import org.fyfa.TemplateType;
import org.fyfa.ids.TemplateTypeId;


/** Used for rendering URLs.
 *
 * @author EinarValen@gmail.com */
public class TemplateTypeUri extends TemplateTypeDefault implements TemplateType {
	public static final TemplateTypeId ID = new TemplateTypeId( "TemplateTypeUri" );
	public static final String URI = "URI", ACTION = "ACTION", BUTTON_LABEL = "BUTTON_LABEL";

	/** Set up the data model for template expansion */
	@Override
	protected void updateModel( TemplateParams params ) {
		super.updateModel( params );
		Map<String, String> model = params.getModel();
		Map<String, String> valuesFormated = params.getValuesFormated();
		String uriTemplate = params.getItem().getUriTemplate();
		model.put( BUTTON_LABEL, params.getItem().getActionButtonLabel() );
		if (uriTemplate != null) model.put( URI, expandTemplate( uriTemplate, valuesFormated ) );
		String actionTemplate = params.getItem().getActionTemplate();
		if (actionTemplate != null) model.put( ACTION, expandTemplate( actionTemplate, valuesFormated ) );
	}

	private String expandTemplate( String format, Map<String, String> model ) {
		Template template = new Template( format );
		return template.expand( model );
	}

	@Override
	public TemplateTypeId getId() {
		return ID;
	}

}
