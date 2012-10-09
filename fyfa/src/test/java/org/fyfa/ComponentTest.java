package org.fyfa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.fyfa.Component;
import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.FieldFactory;
import org.fyfa.IntrinsicValues;
import org.fyfa.Item;
import org.fyfa.Marshal;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.ItemId;
import org.junit.Before;
import org.junit.Test;

public class ComponentTest {
	private Context context;
	private Bar bar;

	static class MyFieldFactory extends FieldFactory {
		public MyFieldFactory( Context context ) {
			super( context );
		}
	}

	@Before
	public void setup() {
		context = new Context();
		bar = new Bar( 1, 1000L, 1.2F, 1.1, true, newSampleDateTime(), newSampleDate(), "qwe123-=/æøå" );
	}

	@Test
	public void renderShouldFormatAllExpectedValues() {
		List<String> columnIds = new Marshal().keys( bar );
		Map<String, ?> valuesToExpect = new Marshal().toMap( bar );
		Component component = createComponent( columnIds );
		Map<String, String> formatedValues = formatValues( component, columnIds, valuesToExpect );
		String rendered = component.render( valuesToExpect );
		for (String colId : valuesToExpect.keySet()) {
			assertTrue( rendered.contains( colId ) );
			String formatedValue = formatedValues.get( colId );
			assertTrue( rendered.contains( formatedValue ) );
		}
	}

	@Test
	public void getFieldFactoryShouldReturnMyfieldFactory() {
		Component component = createComponent( new Marshal().keys( bar ) );
		assertTrue( component.getFieldFactory() instanceof MyFieldFactory );
	}

	@Test
	public void renderShouldWorkWhenRowIsNull() {
		Component component = createComponent( new Marshal().keys( bar ) );
		assertEquals( 0, component.render( null ).length() );
	}

	@Test(expected = RuntimeException.class)
	public void renderFailWhenNonExistingItemIsAdressed() {
		Component component = createComponent( Arrays.asList( new String[] { "NonexistingColumnId" } ) );
		component.render( new Marshal().toMap( bar ) );
	}

	private Map<String, String> formatValues( Component component, List<String> columnIds, Map<String, ?> valuesToExpect ) {
		Map<String, String> formatedValues = new HashMap<String, String>();
		for (String colId : columnIds) {
			Item item = component.getItems().get( new ItemId( colId ) );
			String formated = component.format( item, valuesToExpect.get( colId ) );
			formatedValues.put( colId, formated );
		}
		return formatedValues;
	}

	private Component createComponent( List<String> columnIds ) {
		Component component = new Component( new ComponentId( "renderTest" ), context, getColumnIdsInDesiredOrder( columnIds ) );
		component.setFieldFactory( new MyFieldFactory( context ) );
		Map<ItemId, Field> fields = component.assessFields( bar.getClass() );
		for (ItemId columnId : fields.keySet()) {
			addItemToComponent( component, fields, columnId );
		}
		return component;
	}

	private String[] getColumnIdsInDesiredOrder( List<String> columnIds ) {
		return columnIds.toArray( new String[columnIds.size()] );
	}

	private void addItemToComponent( Component component, Map<ItemId, Field> fields, ItemId columnId ) {
		Field field = fields.get( columnId );
		Item item = context.newItem( columnId, field, IntrinsicValues.TemplateIdFormInputText );
		component.getItems().putIfMissing( item );
	}

	private Date newSampleDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set( 2010, 3, 14, 0, 0, 0 );
		Date date = calendar.getTime();
		return date;
	}

	private Date newSampleDateTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set( 2010, 3, 14, 15, 30, 59 );
		Date date = calendar.getTime();
		return date;
	}

}
