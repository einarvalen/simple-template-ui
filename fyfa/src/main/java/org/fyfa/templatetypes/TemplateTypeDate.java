package org.fyfa.templatetypes;

import org.fyfa.Context;
import org.fyfa.TemplateParams;
import org.fyfa.TemplateType;
import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.ids.TemplateTypeId;

/** Used for rendering Date fields.
*
* @author EinarValen@gmail.com */
public class TemplateTypeDate extends TemplateTypeDefault implements TemplateType {
	public static final TemplateTypeId ID = new TemplateTypeId( "TemplateTypeDate" );
	public static final String DATEFORMAT = "DATEFORMAT";

	/** Set up the data model for template expansion */
	@Override
	protected void updateModel( TemplateParams params ) {
		super.updateModel( params );
		params.getModel().put( DATEFORMAT, getDateformat( params.getContext() ) );
	}

	private String getDateformat( Context context ) {
		try {
			return getFieldTypeDate( context ).getFormatString();
		} catch (Exception e) {}
		return "dd.MM.yyyy";
	}

	private FieldTypeDate getFieldTypeDate( Context context ) {
		return (FieldTypeDate)context.getFieldTypes().get( FieldTypeDate.ID );
	}

	@Override
	public TemplateTypeId getId() {
		return ID;
	}

}
