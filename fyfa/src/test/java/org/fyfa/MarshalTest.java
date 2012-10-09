package org.fyfa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.fyfa.Marshal;
import org.fyfa.repositories.FieldTypes;
import org.junit.Test;

public class MarshalTest {
	Marshal rows = new Marshal();

	@Test
	public void toMapList() {
		final String name = "name";
		final Date date = new Date();
		final boolean married = true;
		final int limit = 10;
		List<Foo> foos = new ArrayList<Foo>();
		for (int i = 0; i < limit; i++) {
			Foo foo = new Foo( name + i, i, married, date );
			foos.add( foo );
		}
		List<Map<String, ?>> fooRows = rows.toMapList( foos );
		for (int i = 0; i < limit; i++) {
			Map<String, ?> row = fooRows.get( i );
			assertEquals( married, row.get( "married" ) );
			assertEquals( date, row.get( "date" ) );
			assertEquals( (long)i, row.get( "age" ) );
			assertEquals( name + i, row.get( "name" ) );
		}
	}

	@Test
	public void keys() {
		final int limit = 10;
		List<Foo> foos = new ArrayList<Foo>();
		for (int i = 0; i < limit; i++) {
			foos.add( new Foo() );
		}
		List<Map<String, ?>> fooRows = rows.toMapList( foos );
		final List<String> columns = Arrays.asList( new String[] { "married", "name", "age", "date" } );
		Set<String> keySet = new HashSet<String>();
		keySet.addAll( rows.keys( fooRows ) );
		assertTrue( keySet.containsAll( columns ) );
		for (Map<String, ?> row : fooRows) {
			keySet.clear();
			keySet.addAll( rows.keys( row ) );
			assertTrue( keySet.containsAll( columns ) );
		}
	}

	@Test
	public void upshiftFirstChar() {
		assertEquals( "T", rows.upshiftFirstChar( "t" ) );
		assertEquals( "Tq", rows.upshiftFirstChar( "tq" ) );
		assertEquals( "Tqwe", rows.upshiftFirstChar( "tqwe" ) );
		assertEquals( "1", rows.upshiftFirstChar( "1" ) );
		assertEquals( "Tq", rows.upshiftFirstChar( "Tq" ) );
		assertEquals( "", rows.upshiftFirstChar( "" ) );
		assertEquals( null, rows.upshiftFirstChar( null ) );
	}

	@Test
	public void getTypes() {
		Map<String, Class<?>> types = rows.getTypes( rows.toMap( new Foo( "name", 23, true, new Date() ) ) );
		assertTrue( FieldTypes.isLong( types.get( "age" ) ) );
		assertTrue( FieldTypes.isBoolean( types.get( "married" ) ) );
		assertTrue( FieldTypes.isDate( types.get( "date" ) ) );
		assertEquals( String.class, types.get( "name" ) );
	}

	@Test
	public void getTypesClass() {
		Map<String, Class<?>> types = rows.getTypes( Foo.class );
		assertEquals( long.class, types.get( "age" ) );
		assertEquals( boolean.class, types.get( "married" ) );
		assertEquals( Date.class, types.get( "date" ) );
		assertEquals( String.class, types.get( "name" ) );
	}

}
