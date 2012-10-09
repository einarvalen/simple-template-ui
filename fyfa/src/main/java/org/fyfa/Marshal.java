package org.fyfa;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Does marshaling and un-marshaling
 *
 * If run with security manager on, -Djava.security.manager, grant this class access to
 *         java.lang.reflect.AccessibleObject
 *
 *@author EinarValen@gmail.com */
public class Marshal {

	public List<Map<String, ?>> toMapList( List<?> objects ) {
		List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
		for (Object object : objects) {
			list.add( toMap( object ) );
		}
		return list;
	}

	public List<String> keys( List<Map<String, ?>> rows ) {
		List<String> list = new ArrayList<String>();
		Map<String, ?> row = rows.get( 0 );
		if (row != null) list.addAll( row.keySet() );
		return list;
	}

	public List<String> keys( Map<String, ?> row ) {
		List<String> list = new ArrayList<String>();
		if (row != null) list.addAll( row.keySet() );
		return list;
	}

	public List<String> keys( Object object ) {
		List<String> list = new ArrayList<String>();
		if (object != null) list.addAll( toMap( object ).keySet() );
		return list;
	}

	public List<String> keys( Class<?> clazz ) {
		List<String> list = new ArrayList<String>();
		for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
			if (skip( field )) continue;
			String key = field.getName();
			list.add( key );
		}
		java.util.Collections.sort( list );
		return list;
	}

	public Map<String, ?> toMap( Object object ) {
		Map<String, Object> row = new HashMap<String, Object>();
		for (java.lang.reflect.Field field : object.getClass().getDeclaredFields()) {
			if (skip( field )) continue;
			String key = field.getName();
			Object value = getValue( object, key );
			row.put( key, value );
		}
		return row;
	}

	private boolean skip( java.lang.reflect.Field field ) {
		return (Modifier.isStatic( field.getModifiers() ));
	}

	public Object getValue( Object object, String fieldName ) {
		if (object == null) {
			throw new RuntimeException( String.format( "Failed to retrieve value for field %s on object of class null", fieldName ) );
		}
		if (fieldName == null) {
			throw new RuntimeException( String.format( "Failed to retrieve value for field null on object of class %s", object.getClass().getName() ) );
		}
		Object value = null;
		try {
			java.lang.reflect.Field field = object.getClass().getDeclaredField( fieldName.trim() );
			field.setAccessible( true );
			value = field.get( object );
		} catch (Exception e) {
			throw new RuntimeException( String.format( "Failed to retrieve value for field %s on object of class %s", fieldName, object.getClass().getName() ), e );
		}
		return value;
	}

	public String upshiftFirstChar( String word ) {
		if (word == null) return null;
		if (word.length() < 1) return word;
		return word.substring( 0, 1 ).toUpperCase() + word.substring( 1 );
	}

	public Map<String, Class<?>> getTypes( Map<String, ?> row ) {
		Map<String, Class<?>> types = new HashMap<String, Class<?>>();
		for (String key : row.keySet()) {
			Object obj = row.get( key );
			if (obj == null) {
				types.put( key, String.class );
			} else {
				types.put( key, obj.getClass() );
			}
		}
		return types;
	}

	public Map<String, Class<?>> getTypes( Class<?> clazz ) {
		Map<String, Class<?>> types = new HashMap<String, Class<?>>();
		for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
			types.put( field.getName(), field.getType() );
		}
		return types;
	}

	public <T> void set( T domainObject, String fieldName, Object value ) throws Exception {
		java.lang.reflect.Field field2 = domainObject.getClass().getDeclaredField( fieldName );
		field2.setAccessible( true );
		field2.set( domainObject, value );
	}

}
