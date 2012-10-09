package org.fyfa.repositories;

import static org.junit.Assert.assertEquals;

import org.fyfa.Field;
import org.fyfa.ids.FieldId;
import org.fyfa.ids.FieldTypeId;
import org.fyfa.repositories.Fields;
import org.junit.Test;

public class FieldsTest {

	@Test
	public void tst() {
		Fields fields = new Fields();
		Field fieldA = new Field( new FieldId( "MyComponentId_idA" ), new FieldTypeId( "TypeIdA" ), "LabelA", 11, "DescriptionA" );
		Field fieldB = new Field( new FieldId( "MyComponentId_idB" ), new FieldTypeId( "TypeIdB" ), "LabelB", 12, "DescriptionB" );
		fields.put( fieldA );
		assertEquals( 1, fields.getMap().size() );
		fields.putIfMissing( fieldB );
		assertEquals( 2, fields.getMap().size() );
		fields.putIfMissing( fieldB );
		assertEquals( 2, fields.getMap().size() );
		assertEquals( fieldA, fields.get( fieldA.getId() ) );
	}
}
