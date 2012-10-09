package org.fyfa.ids;

import org.fyfa.Id;

/** A distinguisable identifyer type.
* @author EinarValen@gmail.com */
public class ItemId extends Id {
	public ItemId( String value ) {
		super( value.replace( ' ', '_' ) );
	}
}
