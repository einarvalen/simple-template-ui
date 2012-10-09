package org.fyfa.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;


import org.fyfa.Bar;
import org.fyfa.Marshal;
import org.fyfa.fieldtypes.FieldTypeBoolean;
import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.fieldtypes.FieldTypeDateTime;
import org.fyfa.fieldtypes.FieldTypeDouble;
import org.fyfa.fieldtypes.FieldTypeFloat;
import org.fyfa.fieldtypes.FieldTypeInteger;
import org.fyfa.fieldtypes.FieldTypeLong;
import org.fyfa.fieldtypes.FieldTypeString;
import org.fyfa.ids.FieldTypeId;
import org.fyfa.ids.ItemId;
import org.fyfa.repositories.FieldTypes;
import org.junit.Test;

public class FieldTypesTest {

	@Test
	public void load() {
		FieldTypes fieldTypes = new FieldTypes();
		assertEquals( 8, fieldTypes.getMap().size() );
		assertTrue( fieldTypes.get( FieldTypeLong.ID ) instanceof FieldTypeLong );
		assertTrue( fieldTypes.get( FieldTypeInteger.ID ) instanceof FieldTypeInteger );
		assertTrue( fieldTypes.get( FieldTypeFloat.ID ) instanceof FieldTypeFloat );
		assertTrue( fieldTypes.get( FieldTypeDouble.ID ) instanceof FieldTypeDouble );
		assertTrue( fieldTypes.get( FieldTypeDate.ID ) instanceof FieldTypeDate );
		assertTrue( fieldTypes.get( FieldTypeDateTime.ID ) instanceof FieldTypeDateTime );
		assertTrue( fieldTypes.get( FieldTypeString.ID ) instanceof FieldTypeString );
		assertTrue( fieldTypes.get( FieldTypeBoolean.ID ) instanceof FieldTypeBoolean );
	}

	@Test
	public void types() {
		Map<ItemId, FieldTypeId> typeIds = FieldTypes.assessTypeIds( Bar.class );
		assertEquals( FieldTypeInteger.ID.toString(), typeIds.get( new FieldTypeId( "iI" ) ).toString() );
		assertEquals( FieldTypeLong.ID, typeIds.get( new FieldTypeId( "iL" ) ) );
		assertEquals( FieldTypeFloat.ID, typeIds.get( new FieldTypeId( "iF" ) ) );
		assertEquals( FieldTypeDouble.ID, typeIds.get( new FieldTypeId( "iD" ) ) );
		assertEquals( FieldTypeDate.ID, typeIds.get( new FieldTypeId( "date" ) ) );
		assertEquals( FieldTypeDate.ID, typeIds.get( new FieldTypeId( "myDateTime" ) ) );
		assertEquals( FieldTypeString.ID, typeIds.get( new FieldTypeId( "iS" ) ) );
		assertEquals( FieldTypeBoolean.ID, typeIds.get( new FieldTypeId( "iB" ) ) );
	}

	@Test
	public void types2() {
		Map<ItemId, FieldTypeId> typeIds = FieldTypes.assessTypeIds( new Marshal().toMap( new Bar( 1, 1L, 1.0F, 1.0, false, new Date(), new Date(), "S" ) ) );
		assertEquals( FieldTypeInteger.ID, typeIds.get( new FieldTypeId( "iI" ) ) );
		assertEquals( FieldTypeLong.ID, typeIds.get( new FieldTypeId( "iL" ) ) );
		assertEquals( FieldTypeFloat.ID, typeIds.get( new FieldTypeId( "iF" ) ) );
		assertEquals( FieldTypeDouble.ID, typeIds.get( new FieldTypeId( "iD" ) ) );
		assertEquals( FieldTypeDate.ID, typeIds.get( new FieldTypeId( "date" ) ) );
		assertEquals( FieldTypeDate.ID, typeIds.get( new FieldTypeId( "myDateTime" ) ) );
		assertEquals( FieldTypeString.ID, typeIds.get( new FieldTypeId( "iS" ) ) );
		assertEquals( FieldTypeBoolean.ID, typeIds.get( new FieldTypeId( "iB" ) ) );
	}

	@Test
	public void is() {
		assertTrue( Double.class.isAssignableFrom( Double.class ) );
		assertTrue( double.class.isAssignableFrom( double.class ) );
		assertTrue( FieldTypes.isDouble( double.class ) );
		assertTrue( FieldTypes.isDouble( Double.class ) );
		assertTrue( FieldTypes.isFloat( float.class ) );
		assertTrue( FieldTypes.isFloat( Float.class ) );
		assertTrue( FieldTypes.isLong( long.class ) );
		assertTrue( FieldTypes.isLong( Long.class ) );
		assertTrue( FieldTypes.isInteger( int.class ) );
		assertTrue( FieldTypes.isInteger( Integer.class ) );
		assertTrue( FieldTypes.isDate( Date.class ) );
		assertTrue( FieldTypes.isBoolean( Boolean.class ) );
		assertTrue( FieldTypes.isBoolean( boolean.class ) );
	}

}
