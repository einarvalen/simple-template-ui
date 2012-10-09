package org.fyfa.defaults;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormatSymbols;
import java.util.Locale;


import org.fyfa.fieldtypes.FieldTypeDouble;
import org.junit.Before;
import org.junit.Test;

public class FieldTypeDoubleTest {

	@Before
	public void setup (){
		Locale.setDefault( Locale.US );
	}

	@Test
	public void defaultFormat() {
		FieldTypeDouble fieldType = new FieldTypeDouble();
		for (String value : new String[] { "123.345", "123", "0.1", "1,234.567", "-1,234.567" }) {
			System.out.println("Format " + fieldType.format( fieldType.parse( value )));
			assertEquals( value, fieldType.format( fieldType.parse( value ) ) );
		}
		double d = -12345.678;
		String str = fieldType.format( d );
		assertEquals( "-12,345.678", str );
		double number = fieldType.parse( "-12345.678" );
		assertEquals( "" + d, "" + number );
		double d2 = fieldType.parse( "-12345.678" );
		assertEquals( "" + d, "" + d2 );
		assertEquals( "", fieldType.format( fieldType.parse( "" ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( null ) ) );
		assertEquals( "1,234.568", fieldType.getHint() );
	}

	@Test
	public void norwegianFormat() {
		FieldTypeDouble fieldType = new FieldTypeDouble();
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
		decimalFormatSymbols.setDecimalSeparator( ',' );
		decimalFormatSymbols.setGroupingSeparator( '.' );
		fieldType.setDecimalFormatSymbols( decimalFormatSymbols );
		for (String value : new String[] { "123,345", "123", "0,1", "1.234,567", "-1.234,567" }) {
			assertEquals( value, fieldType.format( fieldType.parse( value ) ) );
		}
		double d = -12345.678;
		String str = fieldType.format( d );
		assertEquals( "-12.345,678", str );
		double number = fieldType.parse( "-12345,678" );
		assertEquals( "" + d, "" + number );
		double d2 = fieldType.parse( "-12345,678" );
		assertEquals( "" + d, "" + d2 );
		assertEquals( "", fieldType.format( fieldType.parse( "" ) ) );
		assertEquals( "", fieldType.format( fieldType.parse( null ) ) );
		assertEquals( "1.234,568", fieldType.getHint() );
	}

}
