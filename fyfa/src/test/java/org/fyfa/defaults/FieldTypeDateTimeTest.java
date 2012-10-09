package org.fyfa.defaults;

import static org.junit.Assert.assertEquals;

import org.fyfa.fieldtypes.FieldTypeDateTime;
import org.junit.Test;

public class FieldTypeDateTimeTest {

	@Test
	public void parseFormat() {
		FieldTypeDateTime fieldType = new FieldTypeDateTime();
		final String dValue = "2012-12-31 15:30";
		assertEquals( dValue, fieldType.format( fieldType.parse( dValue ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( "" ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( null ) ) );
	}

}
