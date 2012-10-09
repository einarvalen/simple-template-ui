package org.fyfa.ids;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.Assert;

import org.fyfa.Id;
import org.junit.Test;

public class IdTest {

	@Test
	public void equals() {
		Id id_1 = new Id( "A" );
		Id id_2 = new Id( "A" );
		Assert.assertEquals( id_1.hashCode(), id_2.hashCode() );
		Assert.assertTrue( id_1.compareTo( id_2 ) == 0 );
		Assert.assertTrue( id_1.equals( id_1 ) );
	}

	@Test
	public void map() {
		Id id_1 = new Id( "A" );
		Id id_2 = new Id( "A" );
		Id id_3 = new Id( "B" );
		Map<Id, Id> map = new ConcurrentHashMap<Id, Id>();
		map.put( id_1, id_1 );
		map.put( id_2, id_2 );
		map.put( id_3, id_3 );
		Assert.assertEquals( 2, map.size() );
		Assert.assertEquals( id_1, map.get( id_1 ) );
		Assert.assertEquals( id_1, map.get( id_2 ) );
		Assert.assertEquals( id_3, map.get( id_3 ) );
		Assert.assertEquals( id_3, map.get( new Id( "B" ) ) );
		Assert.assertEquals( new Id( "B" ), map.get( new Id( "B" ) ) );
		Assert.assertNull( map.get( new Id( "O" ) ) );
	}
}
