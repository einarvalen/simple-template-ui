package org.fyfa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;


import org.fyfa.Component;
import org.fyfa.Context;
import org.fyfa.Field;
import org.fyfa.FieldFactory;
import org.fyfa.Marshal;
import org.fyfa.annotations.FieldDef;
import org.fyfa.annotations.ItemDefFormNew;
import org.fyfa.components.CompFoo;
import org.fyfa.fieldtypes.FieldTypeBoolean;
import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.fieldtypes.FieldTypeLong;
import org.fyfa.fieldtypes.FieldTypeString;
import org.fyfa.ids.ComponentId;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.FieldTypeId;
import org.fyfa.ids.ItemId;
import org.junit.Test;

public class FieldFactoryTest {
	private final static Marshal marshal = new Marshal();
	final ComponentId componentId = new ComponentId( "MyId" );
	private static final String ALDER = "Alder";
	private static final String NAVN = "Navn";
	private static final String YOUR_FULL_NAME = "Your full name";
	private static final String THIS_YEAR_LESS_THE_YEAR_YOU_WERE_BORN = "This year less the year you were born";
	private static final String MyFieldTypeId = "MyFieldTypeId";

	static class InternallyDefinedDto {
		@FieldDef(label = NAVN, description = YOUR_FULL_NAME, maxLength = 60)
		String name;

		@FieldDef(description = THIS_YEAR_LESS_THE_YEAR_YOU_WERE_BORN, label = ALDER)
		@ItemDefFormNew(selectionId = AgeSelection.ID)
		long age;

		@FieldDef(fieldTypeId = "MyFieldTypeId")
		boolean married;
	}

	@Test
	public void fieldAssessmentmAnnotatedDto() {
		Context context = new Context();
		Map<ItemId, Field> fields = assessFieldsForAnnotatedDto( context );
		for (String columnId : marshal.keys( CompFoo.class )) {
			verifyFieldAnotations( fields, columnId );
		}
		verifySensibleFieldTypes( fields );
	}

	@Test
	public void fieldAssessmentSimpleDto() {
		Map<ItemId, Field> fields = assessFieldsFromSimpleDto( componentId );
		verifyAllFieldsArePresent( fields );
		verifySensibleFieldTypes( fields );
	}

	@Test
	public void composeLabel() {
		String columnId = "firstNameNotLastName";
		String label = getLabelGiven( columnId );
		verifyComposedLabelHasSpaceBetweenWords( columnId, label );
	}

	@Test
	public void fieldAssessmentAnnotatedInternalDto() {
		Map<ItemId, Field> fields = getFieldsForInternallyDefinedDto( new InternallyDefinedDto() );
		verifyFieldAnnotationsForInternallyDefinedDto( fields );
	}

	private void verifyComposedLabelHasSpaceBetweenWords( String columnId, String label ) {
		assertEquals( columnId.length() + 4, label.length() );
		assertTrue( label.indexOf( "First" ) == 0 );
		assertTrue( label.indexOf( " Name" ) > -1 );
		assertTrue( label.indexOf( " Not " ) > -1 );
		assertTrue( label.indexOf( " Last " ) > -1 );
		//System.out.println( label );
	}

	private String getLabelGiven( String str ) {
		Context context = new Context();
		FieldFactory fieldFactory = new FieldFactory( context );
		String label = fieldFactory.composeLabel( new ItemId( str ) );
		return label;
	}

	private void verifyFieldAnnotationsForInternallyDefinedDto( Map<ItemId, Field> fields ) {
		Field field = fields.get( new ItemId( "name" ) );
		//System.out.println( "Field=" + field.toString() );
		assertEquals( NAVN, field.getLabel() );
		assertTrue( field.getDescription().indexOf( YOUR_FULL_NAME ) > -1 );
		assertEquals( 60, field.getMaxLength() );
		assertEquals( componentId.toString() + "_name", field.getId().toString() );
		assertEquals( FieldTypeString.ID, field.getFieldTypeId() );
		field = fields.get( new ItemId( "married" ) );
		//System.out.println( "Field=" + field.toString() );
		assertEquals( "Married", field.getLabel() );
		assertTrue( field.getDescription().indexOf( "Married" ) > -1 );
		assertEquals( 50, field.getMaxLength() );
		assertEquals( componentId.toString() + "_married", field.getId().toString() );
		assertEquals( new FieldTypeId( MyFieldTypeId ), field.getFieldTypeId() );
		field = fields.get( new ItemId( "age" ) );
		//System.out.println( "Field=" + field.toString() );
		assertEquals( ALDER, field.getLabel() );
		assertTrue( field.getDescription().indexOf( THIS_YEAR_LESS_THE_YEAR_YOU_WERE_BORN ) > -1 );
		assertEquals( 50, field.getMaxLength() );
		assertEquals( componentId.toString() + "_age", field.getId().toString() );
		assertEquals( FieldTypeLong.ID, field.getFieldTypeId() );
	}

	private Map<ItemId, Field> getFieldsForInternallyDefinedDto( InternallyDefinedDto dto ) {
		Context context = new Context();
		context.registerSelecton( new AgeSelection() );
		List<String> columnIds = marshal.keys( dto );
		Component component = new Component( componentId, context, columnIds.toArray( new String[columnIds.size()] ) );
		Map<ItemId, Field> fields = component.assessFields( dto.getClass() );
		return fields;
	}

	private void verifyFieldAnotations( Map<ItemId, Field> fields, String columnId ) {
		Field field = fields.get( new ItemId( columnId ) );
		assertEquals( new FieldId( componentId + "_" + columnId ), field.getId() );
		if (columnId.equals( "name" )) {
			assertEquals( NAVN, field.getLabel() );
			assertEquals( 60, field.getMaxLength() );
			assertTrue( field.getDescription().indexOf( YOUR_FULL_NAME ) > -1 );
		} else if (columnId.equals( "age" )) {
			assertEquals( ALDER, field.getLabel() );
			assertTrue( field.getDescription().indexOf( THIS_YEAR_LESS_THE_YEAR_YOU_WERE_BORN ) > -1 );
		} else {
			assertEquals( columnId, marshal.upshiftFirstChar( columnId ), field.getLabel() );
		}
	}

	private Map<ItemId, Field> assessFieldsForAnnotatedDto( Context context ) {
		FieldFactory fieldFactory = new FieldFactory( context );
		Map<ItemId, Field> fields = fieldFactory.assessFields( CompFoo.class, componentId );
		return fields;
	}

	private void verifySensibleFieldTypes( Map<ItemId, Field> fields ) {
		assertEquals( FieldTypeString.ID, fields.get( new ItemId( "name" ) ).getFieldTypeId() );
		assertEquals( FieldTypeLong.ID, fields.get( new ItemId( "age" ) ).getFieldTypeId() );
		assertEquals( FieldTypeBoolean.ID, fields.get( new ItemId( "married" ) ).getFieldTypeId() );
		assertEquals( FieldTypeDate.ID, fields.get( new ItemId( "date" ) ).getFieldTypeId() );
	}

	private void verifyAllFieldsArePresent( Map<ItemId, Field> fields ) {
		List<String> keys = marshal.keys( Foo.class );
		for (String colId : keys) {
			String fieldId = componentId + "_" + colId;
			Field field = fields.get( new ItemId( colId ) );
			assertEquals( fieldId, field.getId().toString() );
			assertEquals( marshal.upshiftFirstChar( colId ), field.getLabel() );
		}
	}

	private Map<ItemId, Field> assessFieldsFromSimpleDto( final ComponentId componentId ) {
		FieldFactory fieldFactory = new FieldFactory( new Context() );
		Map<ItemId, Field> fields = fieldFactory.assessFields( marshal.toMap( new Foo() ), componentId );
		return fields;
	}

}
