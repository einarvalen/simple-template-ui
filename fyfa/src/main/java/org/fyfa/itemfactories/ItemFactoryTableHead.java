package org.fyfa.itemfactories;

import org.fyfa.Component;
import org.fyfa.IntrinsicValues;
import org.fyfa.ids.FieldTypeId;
import org.fyfa.ids.TemplateId;

/** Used  to create Item objects for Table components
*
* @author EinarValen@gmail.com */
public class ItemFactoryTableHead<T> extends ItemFactoryTableBody<T> {
	public ItemFactoryTableHead( Component component ) {
		super( component );
	}

	@Override
	protected TemplateId suggestTemplateId( FieldTypeId fieldTypeId, boolean uri ) {
		return (isNumericType( fieldTypeId )) ? IntrinsicValues.TemplateIdTableColumnHeadNumeric : IntrinsicValues.TemplateIdTableColumnHeadText;
	}
}
