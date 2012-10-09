package org.fyfa.fieldtypes;

import junit.framework.Assert;

import org.fyfa.fieldtypes.FieldTypeString;
import org.fyfa.ids.FieldTypeId;
import org.junit.Before;
import org.junit.Test;

public class FieldTypeStringTest {
	private FieldTypeString fieldType;
	private final String value = "abc123 åøæØÆÅ@*&";

	@Before
	public void setup() {
		this.fieldType = new FieldTypeString();
	}

	@Test
	public void parse() {
		Assert.assertEquals( value, fieldType.parse( value ) );
		Assert.assertEquals( value, fieldType.parse( fieldType.format( value ) ) );
	}

	@Test
	public void format() {
		Assert.assertEquals( value, fieldType.format( value ) );
		Assert.assertEquals( value, fieldType.format( fieldType.parse( value ) ) );
	}

	@Test
	public void hint() {
		Assert.assertNotNull( fieldType.getHint() );
	}

	@Test
	public void getId() {
		final FieldTypeId fieldTypeId = new FieldTypeId( "MyId" );
		fieldType = new FieldTypeString( fieldTypeId );
		Assert.assertEquals( fieldTypeId, fieldType.getId() );
	}
}
