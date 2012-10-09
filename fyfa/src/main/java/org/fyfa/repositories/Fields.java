package org.fyfa.repositories;

import java.util.Map;

import org.fyfa.Field;
import org.fyfa.Registry;
import org.fyfa.ids.FieldId;


/** Repository. A collection of Field objects.
*
* @author EinarValen@gmail.com */
public class Fields extends Registry<FieldId, Field> {
	public Fields() {}

	public Fields( Map<FieldId, Field> map ) {
		super( map );
	}

}
