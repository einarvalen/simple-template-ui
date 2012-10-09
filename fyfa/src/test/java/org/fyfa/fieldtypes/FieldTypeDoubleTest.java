package org.fyfa.fieldtypes;

import java.util.Locale;

import junit.framework.Assert;

import org.fyfa.fieldtypes.FieldTypeDouble;
import org.fyfa.ids.FieldTypeId;
import org.junit.Before;
import org.junit.Test;

public class FieldTypeDoubleTest {
	private FieldTypeDouble fieldType;
	private final double value = 1234567.89;
	private final String sValue = "1,234,567.89";

	@Before
	public void setup() {
		Locale.setDefault( Locale.US );
		this.fieldType = new FieldTypeDouble();
	}

	@Test
	public void parse() {
		Assert.assertEquals( new Double( value ), fieldType.parse( Double.toString( value ) ) );
		Assert.assertEquals( new Double( value ), fieldType.parse( fieldType.format( value ) ) );
	}

	@Test
	public void parseMany() {
		String[] sValues = { ".1", "0", "-1", "0.1", "-.1", "-0.1", "0.123456789", ".0001" };
		for (String str : sValues) {
			fieldType.parse( str );
		}
	}

	@Test
	public void format() {
		Assert.assertEquals( sValue, fieldType.format( value ) );
		Assert.assertEquals( sValue, fieldType.format( fieldType.parse( sValue ) ) );
	}

	@Test
	public void hint() {
		Assert.assertNotNull( fieldType.getHint() );
	}

	@Test(expected = RuntimeException.class)
	public void parseError() {
		fieldType.parse( "qwe" );
	}

	@Test
	public void getId() {
		final FieldTypeId fieldTypeId = new FieldTypeId( "MyId" );
		fieldType = new FieldTypeDouble( fieldTypeId );
		Assert.assertEquals( fieldTypeId, fieldType.getId() );
	}
}
