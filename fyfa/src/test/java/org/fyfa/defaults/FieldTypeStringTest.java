package org.fyfa.defaults;

import static org.junit.Assert.assertEquals;

import org.fyfa.fieldtypes.FieldTypeString;
import org.junit.Test;

public class FieldTypeStringTest {

	@Test
	public void paseFormat() {
		FieldTypeString fieldType = new FieldTypeString();
		final String value = "qweq";
		String string = fieldType.format( fieldType.parse( value ) );
		assertEquals( value, string );
	}

}
