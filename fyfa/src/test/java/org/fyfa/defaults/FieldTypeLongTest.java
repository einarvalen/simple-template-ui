package org.fyfa.defaults;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormatSymbols;
import java.util.Locale;


import org.fyfa.fieldtypes.FieldTypeLong;
import org.junit.Before;
import org.junit.Test;

public class FieldTypeLongTest {

	@Before
	public void setup() {
		Locale.setDefault( Locale.US );
	}

	@Test
	public void defaultFormat() {
		FieldTypeLong fieldType = new FieldTypeLong();
		for (String value : new String[] { "123,345", "123", "1", "1,234,567", "-1,234,567" }) {
			assertEquals( value, fieldType.format( fieldType.parse( value ) ) );
		}
		long d = -12345678;
		String str = fieldType.format( d );
		assertEquals( "-12,345,678", str );
		long number = fieldType.parse( "-12345,678" );
		assertEquals( "" + d, "" + number );
		long d2 = fieldType.parse( "-12345678" );
		assertEquals( "" + d, "" + d2 );
		long i = -1234;
		long i2 = fieldType.parse( "-1234" );
		assertEquals( "" + i, "" + i2 );
		assertEquals( "", fieldType.format( fieldType.parse( "" ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( null ) ) );
	}

	@Test
	public void norwegianFormat() {
		FieldTypeLong fieldType = new FieldTypeLong();
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
		decimalFormatSymbols.setDecimalSeparator( ',' );
		decimalFormatSymbols.setGroupingSeparator( '.' );
		fieldType.setDecimalFormatSymbols( decimalFormatSymbols );
		for (String value : new String[] { "123.345", "123", "1", "1.234.567", "-1.234.567" }) {
			assertEquals( value, fieldType.format( fieldType.parse( value ) ) );
		}
		long d = -12345678;
		String str = fieldType.format( d );
		assertEquals( "-12.345.678", str );
		long number = fieldType.parse( "-12345678" );
		assertEquals( "" + d, "" + number );
		long d2 = fieldType.parse( "-12345678" );
		assertEquals( "" + d, "" + d2 );
		long i = -1234;
		long i2 = fieldType.parse( "-1234" );
		assertEquals( "" + i, "" + i2 );
		assertEquals( "", fieldType.format( fieldType.parse( "" ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( null ) ) );
	}

}
