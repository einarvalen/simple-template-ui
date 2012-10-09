package org.fyfa;

import java.util.List;

import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.fieldtypes.FieldTypeDouble;
import org.fyfa.fieldtypes.FieldTypeFloat;
import org.fyfa.fieldtypes.FieldTypeInteger;
import org.fyfa.fieldtypes.FieldTypeLong;
import org.fyfa.ids.FieldTypeId;


/** Creates instances of Item objects for components
*
* @author EinarValen@gmail.com */
public abstract class ItemFactory<T> {

	abstract public List<Item> assessItems( Operation operation, Class<T> clazz );

	public static boolean isNumericType( FieldTypeId fieldTypeId ) {
		if (FieldTypeInteger.ID.equals( fieldTypeId )) return true;
		if (FieldTypeLong.ID.equals( fieldTypeId )) return true;
		if (FieldTypeDouble.ID.equals( fieldTypeId )) return true;
		if (FieldTypeFloat.ID.equals( fieldTypeId )) return true;
		return false;
	}

	public static boolean isDate( FieldTypeId fieldTypeId ) {
		return FieldTypeDate.ID.equals( fieldTypeId );
	}

}
