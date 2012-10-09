package org.fyfa.fieldtypes;

import java.util.Locale;

import junit.framework.Assert;

import org.fyfa.fieldtypes.FieldTypeLong;
import org.fyfa.ids.FieldTypeId;
import org.junit.Before;
import org.junit.Test;

public class FieldTypeLongTest {
	private FieldTypeLong fieldType;
	private final long value = 1234567890L;
	private final String sValue = "1,234,567,890";

	@Before
	public void setup() {
		Locale.setDefault( Locale.US );
		this.fieldType = new FieldTypeLong();
	}

	@Test
	public void parse() {
		Assert.assertEquals( new Long( value ), fieldType.parse( Long.toString( value ) ) );
		Assert.assertEquals( new Long( value ), fieldType.parse( fieldType.format( value ) ) );
	}

	@Test
	public void parseMany() {
		String[] sValues = { "1", "0", "-1", "123,456,789" };
		for (String str : sValues) {
			Assert.assertEquals( str, fieldType.format( fieldType.parse( str ) ) );
		}
	}

	@Test
	public void format() {
		Assert.assertEquals( sValue, fieldType.format( value ) );
		Assert.assertEquals( sValue, fieldType.format( fieldType.parse( sValue ) ) );
	}

	@Test(expected = RuntimeException.class)
	public void parseError() {
		fieldType.parse( "qwe" );
	}

	@Test
	public void hint() {
		Assert.assertNotNull( fieldType.getHint() );
	}

	@Test
	public void getId() {
		final FieldTypeId fieldTypeId = new FieldTypeId( "MyId" );
		fieldType = new FieldTypeLong( fieldTypeId );
		Assert.assertEquals( fieldTypeId, fieldType.getId() );
	}
}
