package org.fyfa.fieldtypes;

import java.util.Date;

import junit.framework.Assert;

import org.fyfa.ids.FieldTypeId;
import org.junit.Before;
import org.junit.Test;

public class FieldTypeDateTimeTest {
	private FieldTypeDateTime fieldType;
	private final String sValue = "1995-12-31 18:30";

	@Before
	public void setup() {
		this.fieldType = new FieldTypeDateTime();
	}

	@Test
	public void format() {
		Assert.assertEquals( 16, fieldType.format( new Date() ).length() );
	}

	@Test
	public void parseThenFormat() {
		Assert.assertEquals( sValue, fieldType.format( fieldType.parse( sValue ) ) );
	}

	@Test(expected = RuntimeException.class)
	public void parseError() {
		fieldType.parse( "12/31/93" );
	}

	@Test
	public void hint() {
		Assert.assertNotNull( fieldType.getHint() );
	}

	@Test
	public void getId() {
		final FieldTypeId fieldTypeId = new FieldTypeId( "MyId" );
		fieldType = new FieldTypeDateTime( fieldTypeId );
		Assert.assertEquals( fieldTypeId, fieldType.getId() );
	}
}
