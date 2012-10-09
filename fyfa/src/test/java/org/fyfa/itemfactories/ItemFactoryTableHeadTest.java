package org.fyfa.itemfactories;

import java.util.Date;

import junit.framework.Assert;

import org.fyfa.Component;
import org.fyfa.Context;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;
import org.fyfa.ids.ItemId;
import org.fyfa.ids.TemplateId;
import org.fyfa.repositories.Items;
import org.junit.Test;

public class ItemFactoryTableHeadTest {
	static class NoAnnotations {
		int myInt;
		String myString;
		Date myDate;
		double myDouble;
	}

	private final Context context = new Context();

	@Test
	public void noAnnotations() {
		TableParams<NoAnnotations> params = new TableParams<NoAnnotations>( "NoAnnotationsTable", NoAnnotations.class, context );
		params.setColumnNamesInSequence( new String[] { "myInt", "myString", "myDate", "myDouble" } );
		Table<NoAnnotations> table = new Table<NoAnnotations>( params );
		Assert.assertEquals( 4, table.getItems().getMap().size() );
		verifyItem( table, "myInt", IntrinsicValues.TemplateIdTableColumnHeadNumeric );
		verifyItem( table, "myString", IntrinsicValues.TemplateIdTableColumnHeadText );
		verifyItem( table, "myDate", IntrinsicValues.TemplateIdTableColumnHeadText );
		verifyItem( table, "myDouble", IntrinsicValues.TemplateIdTableColumnHeadNumeric );
		Assert.assertEquals( false, getItem( table, "myDouble" ).isRequired() );
		Assert.assertNull( getItem( table, "myDouble" ).getValidatorId() );
		Assert.assertNull( getItem( table, "myDouble" ).getSelectionId() );
		Assert.assertNull( getItem( table, "myDouble" ).getUriTemplate() );
	}

	private Item getItem( Table<?> table, String name ) {
		Component head = table.getHead();
		Items items = head.getItems();
		Assert.assertNotNull( items );
		Item item = items.get( new ItemId( name ) );
		Assert.assertNotNull( item );
		return item;
	}

	private void verifyItem( Table<NoAnnotations> table, String name, TemplateId templateId ) {
		Item item = getItem( table, name );
		Assert.assertEquals( name, item.getId().toString() );
		Assert.assertEquals( "NoAnnotationsTable_" + name, item.getFieldId().toString() );
		Assert.assertEquals( templateId, item.getTemplateId() );
	}
}
