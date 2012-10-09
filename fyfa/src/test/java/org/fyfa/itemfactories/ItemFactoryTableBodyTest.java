package org.fyfa.itemfactories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.fyfa.Context;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.annotations.ItemDefTable;
import org.fyfa.components.Table;
import org.fyfa.components.TableParams;
import org.fyfa.ids.ItemId;
import org.fyfa.repositories.Items;
import org.junit.Test;

public class ItemFactoryTableBodyTest {
	static class NoAnnotations {
		int myInt;
		String myString;
		Date myDate;
		double myDouble;
	}

	static class WithAnnotations {
		Long myLong;

		@ItemDefTable(templateId = "myTemplateId")
		String myString;

		@ItemDefTable(uriTemplate = "myUriTemplate")
		Float myFloat;

		@ItemDefTable(uriTemplate = "myUriTemplate", templateId = "myTemplateId")
		Boolean myBoolean;
	}

	private final Context context = new Context();

	@Test
	public void noAnnotations() {
		List<Table<NoAnnotations>> list = createTablesForNoAnnotations();
		for (Table<NoAnnotations> table : list) {
			Assert.assertEquals( 4, table.getItems().getMap().size() );
			verifyItem( table, "myInt", IntrinsicValues.TemplateIdTableColumnNumeric.toString() );
			verifyItem( table, "myString", IntrinsicValues.TemplateIdTableColumnText.toString() );
			verifyItem( table, "myDate", IntrinsicValues.TemplateIdTableColumnText.toString() );
			verifyItem( table, "myDouble", IntrinsicValues.TemplateIdTableColumnNumeric.toString() );
		}
	}

	@Test
	public void withAnnotations() {
		List<Table<WithAnnotations>> list = createFormsForWithAnnotations();
		for (Table<WithAnnotations> table : list) {
			Assert.assertEquals( 4, table.getItems().getMap().size() );
			verifyAnnotatedItem( table, "myLong", IntrinsicValues.TemplateIdTableColumnNumeric.toString() );
			verifyAnnotatedItem( table, "myString", "myTemplateId" );
			verifyAnnotatedItem( table, "myFloat", IntrinsicValues.TemplateIdTableColumnUri.toString() );
			verifyAnnotatedItem( table, "myBoolean", "myTemplateId" );
			Assert.assertEquals( "myUriTemplate", getItem( table, "myFloat" ).getUriTemplate().toString() );
		}
	}

	private List<Table<NoAnnotations>> createTablesForNoAnnotations() {
		List<Table<NoAnnotations>> list = new ArrayList<Table<NoAnnotations>>();
		TableParams<NoAnnotations> params = new TableParams<NoAnnotations>( "NoAnnotationsTable", NoAnnotations.class, context );
		params.setColumnNamesInSequence( new String[] { "myInt", "myString", "myDate", "myDouble" } );
		for (int i = 0; i < 3; i++) {
			list.add( new Table<NoAnnotations>( params ) );
		}
		return list;
	}

	private List<Table<WithAnnotations>> createFormsForWithAnnotations() {
		List<Table<WithAnnotations>> list = new ArrayList<Table<WithAnnotations>>();
		TableParams<WithAnnotations> params = new TableParams<WithAnnotations>( "WithAnnotationsTable", WithAnnotations.class, context );
		params.setColumnNamesInSequence( new String[] { "myLong", "myString", "myFloat", "myBoolean" } );
		for (int i = 0; i < 3; i++) {
			list.add( new Table<WithAnnotations>( params ) );
		}
		return list;
	}

	private Item getItem( Table<?> table, String name ) {
		Items items = table.getItems();
		Assert.assertNotNull( items );
		Item item = items.get( new ItemId( name ) );
		Assert.assertNotNull( item );
		return item;
	}

	private void verifyAnnotatedItem( Table<WithAnnotations> table, String name, String templateTypeId ) {
		Item item = getItem( table, name );
		Assert.assertEquals( name, item.getId().toString() );
		Assert.assertEquals( "WithAnnotationsTable_" + name, item.getFieldId().toString() );
		Assert.assertEquals( templateTypeId, item.getTemplateId().toString() );
	}

	private void verifyItem( Table<NoAnnotations> table, String name, String templateTypeId ) {
		Item item = getItem( table, name );
		Assert.assertEquals( name, item.getId().toString() );
		Assert.assertEquals( "NoAnnotationsTable_" + name, item.getFieldId().toString() );
		Assert.assertEquals( templateTypeId, item.getTemplateId().toString() );
	}
}
