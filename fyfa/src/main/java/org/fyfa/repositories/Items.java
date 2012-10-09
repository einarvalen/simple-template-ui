package org.fyfa.repositories;

import java.util.HashMap;
import java.util.Map;

import org.fyfa.Item;
import org.fyfa.Registry;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.ItemId;


/** Repository. A collection of Item objects.
*
* @author EinarValen@gmail.com */
public class Items extends Registry<ItemId, Item> {
	Map<FieldId, ItemId> fieldIds = new HashMap<FieldId, ItemId>();

	public Items() {
		super();
	}

	public Items( Map<ItemId, Item> map ) {
		super( map );
	}

	@Override
	public void put( Item item ) {
		super.put( item );
		fieldIds.put( item.getFieldId(), item.getId() );
	}

	public Item getItemByFieldId( FieldId fieldId ) {
		ItemId id = fieldIds.get( fieldId );
		return this.get( id );
	}

}
