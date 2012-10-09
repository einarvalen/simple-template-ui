package org.fyfa.defaults;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormatSymbols;
import java.util.Locale;


import org.fyfa.fieldtypes.FieldTypeFloat;
import org.junit.Before;
import org.junit.Test;

public class FieldTypeFloatTest {

	@Before
	public void setup (){
		Locale.setDefault( Locale.US );
	}

	@Test
	public void defaultFormat() {
		FieldTypeFloat fieldType = new FieldTypeFloat();
		for (String value : new String[] { "123.345", "123", "0.1", "1,234.567", "-1,234.567" }) {
			assertEquals( value, fieldType.format( fieldType.parse( value ) ) );
		}
		float d = -12345.678F;
		String str = fieldType.format( d );
		assertEquals( "-12,345.678", str );
		float number = fieldType.parse( "-12345.678" );
		assertEquals( "" + d, "" + number );
		float d2 = fieldType.parse( "-12345.678" );
		assertEquals( "" + d, "" + d2 );
		assertEquals( "", fieldType.format( fieldType.parse( "" ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( null ) ) );
	}

	@Test
	public void norwegianFormat() {
		FieldTypeFloat fieldType = new FieldTypeFloat();
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
		decimalFormatSymbols.setDecimalSeparator( ',' );
		decimalFormatSymbols.setGroupingSeparator( '.' );
		fieldType.setDecimalFormatSymbols( decimalFormatSymbols );
		for (String value : new String[] { "123,345", "123", "0,1", "1.234,567", "-1.234,567" }) {
			assertEquals( value, fieldType.format( fieldType.parse( value ) ) );
		}
		float d = -12345.678F;
		String str = fieldType.format( d );
		assertEquals( "-12.345,678", str );
		float number = fieldType.parse( "-12345,678" );
		assertEquals( "" + d, "" + number );
		float d2 = fieldType.parse( "-12345,678" );
		assertEquals( "" + d, "" + d2 );
		assertEquals( "", fieldType.format( fieldType.parse( "" ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( null ) ) );
	}

}
