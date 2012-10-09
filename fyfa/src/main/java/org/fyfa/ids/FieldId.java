package org.fyfa.ids;

import org.fyfa.Id;

/** A distinguisable identifyer type.
* @author EinarValen@gmail.com */
public class FieldId extends Id {
	public static final String Sep = "_";
	private final ItemId itemId;
	private final ComponentId componentId;

	public FieldId( String value ) {
		super( value );
		int indexOfSep = value.indexOf( Sep );
		if (indexOfSep == -1) throw new IllegalArgumentException( "FieldId must consist of componentId_itemId" );
		this.componentId = new ComponentId( value.substring( 0, indexOfSep ) );
		this.itemId = new ItemId( value.substring( indexOfSep + 1 ) );
	}

	public FieldId( ComponentId componentId, ItemId itemId ) {
		super( componentId.toString() + Sep + itemId.toString() );
		this.componentId = componentId;
		this.itemId = itemId;
	}

	public ItemId getItemId() {
		return this.itemId;
	}

	public ComponentId getComponentId() {
		return this.componentId;
	}
}
