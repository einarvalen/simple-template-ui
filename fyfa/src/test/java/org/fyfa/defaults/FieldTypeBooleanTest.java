package org.fyfa.defaults;

import static org.junit.Assert.assertEquals;

import org.fyfa.fieldtypes.FieldTypeBoolean;
import org.junit.Test;

public class FieldTypeBooleanTest {

	@Test
	public void paseFormat() {
		FieldTypeBoolean fieldType = new FieldTypeBoolean();
		assertEquals( "true", fieldType.format( fieldType.parse( "true" ) ) );
		assertEquals( "false", fieldType.format( fieldType.parse( "false" ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( "" ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( null ) ) );
	}

}
