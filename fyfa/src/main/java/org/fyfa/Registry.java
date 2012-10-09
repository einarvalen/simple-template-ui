package org.fyfa;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/** Backing class for all repositories in Context
 *
 * @author EinarValen@gmail.com */
public class Registry<I extends Id, T extends Identifyable<I>> {
	private final Map<I, T> map = new ConcurrentHashMap<I, T>();

	public Registry( Map<I, T> map ) {
		this.map.putAll( map );
	}

	public Registry() {}

	public void put( T t ) {
		map.put( t.getId(), t );
	}

	public T get( I id ) {
		T t = this.map.get( id );
		if (t == null) throw new RuntimeException( String.format( "%s is not registerd in registry %s!", id, this.getClass().getName() ) );
		return t;
	}

	/** Insert t if t.getId() cannot be found in the collection */
	public void putIfMissing( T t ) {
		if (null == this.map.get( t.getId() )) {
			this.put( t );
		}
	}

	public void putAll( List<T> list ) {
		for (T t : list) {
			put( t );
		}
	}

	/** Insert all objects in list which ids can't be found in teh collection. */
	public void putAllIfMissing( List<T> list ) {
		for (T t : list) {
			putIfMissing( t );
		}
	}

	public Set<I> keySet() {
		return this.map.keySet();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (I key : this.map.keySet()) {
			T t = this.get( key );
			sb.append( String.format( "{ id='%s' , %s={ %s } }\n", key.toString(), t.getClass().getSimpleName(), t.toString() ) );
		}
		return sb.toString();
	}

	public Map<I, T> getMap() {
		return map;
	}

}
