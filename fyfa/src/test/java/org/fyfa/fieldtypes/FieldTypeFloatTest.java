package org.fyfa.fieldtypes;

import java.util.Locale;

import junit.framework.Assert;

import org.fyfa.fieldtypes.FieldTypeFloat;
import org.fyfa.ids.FieldTypeId;
import org.junit.Before;
import org.junit.Test;

public class FieldTypeFloatTest {
	private FieldTypeFloat fieldType;
	private final float value = (float)4567.8;
	private final String sValue = "4,567.8";

	@Before
	public void setup() {
		Locale.setDefault( Locale.US );
		this.fieldType = new FieldTypeFloat();
	}

	@Test
	public void parse() {
		Assert.assertEquals( new Float( value ), fieldType.parse( Float.toString( value ) ) );
		Assert.assertEquals( new Float( value ), fieldType.parse( fieldType.format( value ) ) );
	}

	@Test
	public void parseMany() {
		String[] sValues = { ".1", "0", "-1", "0.1", "-.1", "-0.1", "0.123456789", ".0001" };
		for (String str : sValues) {
			fieldType.parse( str );
		}
	}

	@Test(expected = RuntimeException.class)
	public void parseError() {
		fieldType.parse( "qwe" );
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

	@Test
	public void getId() {
		final FieldTypeId fieldTypeId = new FieldTypeId( "MyId" );
		fieldType = new FieldTypeFloat( fieldTypeId );
		Assert.assertEquals( fieldTypeId, fieldType.getId() );
	}
}
