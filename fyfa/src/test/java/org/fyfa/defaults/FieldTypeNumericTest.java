package org.fyfa.defaults;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class FieldTypeNumericTest {
	private DecimalFormat format;

	@Before
	public void setup() {
		Locale.setDefault( Locale.US );
		format = new DecimalFormat();
	}

	@Test
	public void defaultFormating() throws ParseException {
		final String[] given = new String[] { "-12345.678", "123456" };
		final String[] expect = new String[] { "-12,345.678", "123,456" };
		for (int i = 0; i < given.length; i++) {
			Number parse = format.parse( given[i] );
			Assert.assertEquals( expect[i], format.format( parse ) );
		}
	}

	@Test
	public void nlsFormating() throws ParseException {
		final String[] given = new String[] { "-12345,678", "123456" };
		final String[] expect = new String[] { "-12.345,678", "123.456" };
		format.setDecimalFormatSymbols( newDecimalFormatSymbols() );
		for (int i = 0; i < given.length; i++) {
			Number parse = format.parse( given[i] );
			Assert.assertEquals( expect[i], format.format( parse ) );
		}
	}

	private DecimalFormatSymbols newDecimalFormatSymbols() {
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
		decimalFormatSymbols.setDecimalSeparator( ',' );
		decimalFormatSymbols.setGroupingSeparator( '.' );
		return decimalFormatSymbols;
	}
}
