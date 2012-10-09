package org.fyfa;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.fyfa.Component;
import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.Item;
import org.fyfa.Marshal;
import org.fyfa.Operation;
import org.fyfa.Selection;
import org.fyfa.components.CompFoo;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.SelectionId;
import org.fyfa.itemfactories.ItemFactoryForm;
import org.junit.Test;

public class ItemFactoryTest {
	private final Context context = new Context();

	@Test
	public void assessAnnotatedItems() {
		Selection selection = createSelection();
		context.registerSelecton( selection );
		CompFoo annotatedDto = new CompFoo();
		Component component = createComponent( annotatedDto );
		regiterFieldsToContext( component.assessFields( annotatedDto.getClass() ) );
		regiterItemsToComponent( component );
		verifyMarriageSelection( selection, component );
	}

	private void verifyMarriageSelection( Selection selection, Component component ) {
		Item item = component.getItems().get( new ItemId( "married" ) );
		assertEquals( selection.getId(), item.getSelectionId() );
	}

	private Component createComponent( CompFoo annotatedDto ) {
		List<String> columnIds = new Marshal().keys( annotatedDto );
		Component component = new Component( new ComponentId( "ItemFactoryTest" ), context, columnIds.toArray( new String[columnIds.size()] ) );
		return component;
	}

	private void regiterItemsToComponent( Component component ) {
		ItemFactoryForm<CompFoo> itemFactory = new ItemFactoryForm<CompFoo>( component );
		List<Item> items = itemFactory.assessItems( Operation.New, CompFoo.class );
		component.getItems().putAll( items );
	}

	private void regiterFieldsToContext( Map<ItemId, Field> fields ) {
		for (ItemId columnId : fields.keySet()) {
			context.registerField( fields.get( columnId ) );
		}
	}

	private Selection createSelection() {
		Selection selection = new Selection() {
			@Override
			public Map<String, String> getSelection() {
				Map<String, String> map = new HashMap<String, String>();
				map.put( "true", "Married" );
				map.put( "false", "Not married" );
				return map;
			}

			@Override
			public SelectionId getId() {
				return new SelectionId( "marriedSelectionId" );
			}
		};
		return selection;
	}
}
