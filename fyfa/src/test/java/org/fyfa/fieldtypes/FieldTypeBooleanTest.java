package org.fyfa.fieldtypes;

import junit.framework.Assert;

import org.fyfa.fieldtypes.FieldTypeBoolean;
import org.fyfa.ids.FieldTypeId;
import org.junit.Before;
import org.junit.Test;

public class FieldTypeBooleanTest {
	private FieldTypeBoolean fieldType;
	private final String sValue = "true";
	private final boolean value = true;

	@Before
	public void setup() {
		this.fieldType = new FieldTypeBoolean();
	}

	@Test
	public void parse() {
		Assert.assertEquals( new Boolean( value ), fieldType.parse( sValue ) );
		Assert.assertEquals( new Boolean( value ), fieldType.parse( fieldType.format( value ) ) );
	}

	@Test
	public void parseTrue() {
		String[] sValues = { "true", "TRUE", "True" };
		for (String str : sValues) {
			Assert.assertTrue( fieldType.parse( str ) );
		}
	}

	@Test
	public void parseFalse() {
		String[] sValues = { "false", "FALSE", "False" };
		for (String str : sValues) {
			Assert.assertFalse( fieldType.parse( str ) );
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

	@Test
	public void getId() {
		final FieldTypeId fieldTypeId = new FieldTypeId( "MyId" );
		fieldType = new FieldTypeBoolean( fieldTypeId );
		Assert.assertEquals( fieldTypeId, fieldType.getId() );
	}
}
