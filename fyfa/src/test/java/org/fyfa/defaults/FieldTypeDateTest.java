package org.fyfa.defaults;

import static org.junit.Assert.assertEquals;

import org.fyfa.fieldtypes.FieldTypeDate;
import org.junit.Test;

public class FieldTypeDateTest {

	@Test
	public void parseFormat() {
		FieldTypeDate fieldType = new FieldTypeDate();
		final String dValue = "2012-12-31";
		assertEquals( dValue, fieldType.format( fieldType.parse( dValue ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( "" ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( null ) ) );
	}

}
