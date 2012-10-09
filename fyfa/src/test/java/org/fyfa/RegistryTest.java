package org.fyfa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.fyfa.Id;
import org.fyfa.Identifyable;
import org.fyfa.Registry;
import org.junit.Before;
import org.junit.Test;

public class RegistryTest {
	static class MyIdentifyable implements Identifyable<Id> {
		Id id;
		int value;

		public MyIdentifyable( Id id, int value ) {
			this.id = id;
			this.value = value;
		}

		@Override
		public Id getId() {
			return id;
		}
	}

	private final static MyIdentifyable A1 = new MyIdentifyable( new Id( "A" ), 1 );
	private final static MyIdentifyable B2 = new MyIdentifyable( new Id( "B" ), 2 );
	private final static MyIdentifyable C3 = new MyIdentifyable( new Id( "C" ), 3 );
	private final static MyIdentifyable A11 = new MyIdentifyable( new Id( "A" ), 11 );
	private final static MyIdentifyable C13 = new MyIdentifyable( new Id( "C" ), 13 );
	private Registry<Id, MyIdentifyable> registry;

	@Before
	public void setup() {
		registry = new Registry<Id, MyIdentifyable>();
		Assert.assertEquals( 0, registry.getMap().size() );
	}

	@Test
	public void put() {
		registry.putIfMissing( A1 );
		verifyRegistryContains( new MyIdentifyable[] { A1 } );
		registry.putIfMissing( A11 );
		verifyRegistryContains( new MyIdentifyable[] { A1 } );
		registry.put( A11 );
		verifyRegistryContains( new MyIdentifyable[] { A11 } );
	}

	@Test
	public void putAll() {
		List<MyIdentifyable> list = createListOfIdentifyables( new MyIdentifyable[] { A1, B2 } );
		registry.putAll( list );
		verifyRegistryContains( new MyIdentifyable[] { A1, B2 } );
		registry.putAllIfMissing( list );
		verifyRegistryContains( new MyIdentifyable[] { A1, B2 } );
		registry.putAllIfMissing( createListOfIdentifyables( new MyIdentifyable[] { A1, C3 } ) );
		verifyRegistryContains( new MyIdentifyable[] { A1, B2, C3 } );
		registry.putAll( createListOfIdentifyables( new MyIdentifyable[] { A11, C13 } ) );
		verifyRegistryContains( new MyIdentifyable[] { A11, B2, C13 } );
	}

	private void verifyRegistryContains( MyIdentifyable[] myIdentifyables ) {
		Assert.assertEquals( myIdentifyables.length, registry.getMap().size() );
		for (MyIdentifyable myIdentifyable : myIdentifyables) {
			Assert.assertEquals( myIdentifyable.value, registry.get( myIdentifyable.getId() ).value );
		}
		Set<Id> keySet = registry.keySet();
		Assert.assertEquals( myIdentifyables.length, keySet.size() );
		for (MyIdentifyable myIdentifyable : myIdentifyables) {
			Assert.assertTrue( keySet.contains( myIdentifyable.getId() ) );
		}
	}

	private List<MyIdentifyable> createListOfIdentifyables( MyIdentifyable[] myIdentifyables ) {
		List<MyIdentifyable> list = new ArrayList<MyIdentifyable>();
		for (MyIdentifyable myIdentifyable : myIdentifyables) {
			list.add( myIdentifyable );
		}
		return list;
	}

}
