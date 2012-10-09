package org.fyfa.templatetypes;

import java.util.Map;

import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.Item;
import org.fyfa.Template;
import org.fyfa.TemplateParams;
import org.fyfa.TemplateType;
import org.fyfa.ids.TemplateId;
import org.fyfa.ids.TemplateTypeId;


/** The default rendering engine.
 *
 * @author EinarValen@gmail.com */
public class TemplateTypeDefault implements TemplateType {
	public static final TemplateTypeId ID = new TemplateTypeId( "TemplateTypeDefault" );
	public static final String LABEL = "LABEL", FIELD_ID = "FIELD_ID", DESCRIPTION = "DESCRIPTION", MAXLENGTH = "MAXLENGTH", VALUE = "VALUE", HINT = "HINT", CONTENT = "CONTENT", STYLE = "STYLE", ACTION = "ACTION", METHOD = "METHOD",
			BUTTONS = "BUTTONS";

	@Override
	public String render( TemplateParams params ) {
		updateModel( params );
		return render( params.getContext(), params.getItem().getTemplateId(), params.getModel() );
	}

	@Override
	public TemplateTypeId getId() {
		return ID;
	}

	/** Set up the data model for template expansion */
	protected void updateModel( TemplateParams params ) {
		Field field = getField( params );
		Map<String, String> model = params.getModel();
		model.put( LABEL, field.getLabel() );
		model.put( FIELD_ID, field.getId().toString() );
		model.put( DESCRIPTION, field.getDescription() );
		model.put( MAXLENGTH, String.valueOf( field.getMaxLength() ) );
	}

	private Field getField( TemplateParams params ) {
		Item item = params.getItem();
		Context context = params.getContext();
		return context.getFields().get( item.getFieldId() );
	}

	@Override
	public String render( Context context, TemplateId templateId, Map<String, String> model ) {
		Template template = context.getTemplates().get( templateId );
		return template.expand( model );
	}

}
