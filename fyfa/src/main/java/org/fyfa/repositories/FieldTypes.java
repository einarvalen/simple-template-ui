package org.fyfa.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.fyfa.FieldType;
import org.fyfa.Marshal;
import org.fyfa.Registry;
import org.fyfa.fieldtypes.FieldTypeBoolean;
import org.fyfa.fieldtypes.FieldTypeDate;
import org.fyfa.fieldtypes.FieldTypeDateTime;
import org.fyfa.fieldtypes.FieldTypeDouble;
import org.fyfa.fieldtypes.FieldTypeFloat;
import org.fyfa.fieldtypes.FieldTypeInteger;
import org.fyfa.fieldtypes.FieldTypeLong;
import org.fyfa.fieldtypes.FieldTypeString;
import org.fyfa.ids.FieldTypeId;
import org.fyfa.ids.ItemId;


/** A collection of FieldType objects.
 *
 * @author EinarValen@gmail.com */
public class FieldTypes extends Registry<FieldTypeId, FieldType<?>> {

	public FieldTypes() {
		this( new HashMap<FieldTypeId, FieldType<?>>() );
	}

	public FieldTypes( Map<FieldTypeId, FieldType<?>> map ) {
		super( map );
		this.putIfMissing( new FieldTypeDouble() );
		this.putIfMissing( new FieldTypeFloat() );
		this.putIfMissing( new FieldTypeInteger() );
		this.putIfMissing( new FieldTypeLong() );
		this.putIfMissing( new FieldTypeDate() );
		this.putIfMissing( new FieldTypeDateTime() );
		this.putIfMissing( new FieldTypeBoolean() );
		this.putIfMissing( new FieldTypeString() );
	}

	public static boolean isDouble( Class<?> clazz ) {
		return Double.class.isAssignableFrom( clazz ) || double.class.isAssignableFrom( clazz );
	}

	public static boolean isFloat( Class<?> clazz ) {
		return Float.class.isAssignableFrom( clazz ) || float.class.isAssignableFrom( clazz );
	}

	public static boolean isInteger( Class<?> clazz ) {
		return Integer.class.isAssignableFrom( clazz ) || int.class.isAssignableFrom( clazz );
	}

	public static boolean isLong( Class<?> clazz ) {
		return Long.class.isAssignableFrom( clazz ) || long.class.isAssignableFrom( clazz );
	}

	public static boolean isBoolean( Class<?> clazz ) {
		return Boolean.class.isAssignableFrom( clazz ) || boolean.class.isAssignableFrom( clazz );
	}

	public static boolean isDate( Class<?> clazz ) {
		return Date.class.isAssignableFrom( clazz );
	}

	public static boolean isDateTime(Class<?> clazz) {
		return Date.class.isAssignableFrom( clazz );
	}

	public static FieldTypeId assessTypeId( Class<?> clazz ) {
		FieldTypeId fieldTypeId = FieldTypeString.ID;
		if (clazz == null) return fieldTypeId;
		if (FieldTypes.isBoolean( clazz )) {
			fieldTypeId = FieldTypeBoolean.ID;
		} else if (FieldTypes.isInteger( clazz )) {
			fieldTypeId = FieldTypeInteger.ID;
		} else if (FieldTypes.isLong( clazz )) {
			fieldTypeId = FieldTypeLong.ID;
		} else if (FieldTypes.isFloat( clazz )) {
			fieldTypeId = FieldTypeFloat.ID;
		} else if (FieldTypes.isDouble( clazz )) {
			fieldTypeId = FieldTypeDouble.ID;
		} else if (FieldTypes.isDate( clazz )) {
			fieldTypeId = FieldTypeDate.ID;
		} else if (FieldTypes.isDateTime( clazz )) {
			fieldTypeId = FieldTypeDateTime.ID;
		}
		return fieldTypeId;
	}

	public static Map<ItemId, FieldTypeId> assessTypeIds( Class<?> clazz ) {
		Map<ItemId, FieldTypeId> map = new HashMap<ItemId, FieldTypeId>();
		Map<String, Class<?>> types = new Marshal().getTypes( clazz );
		for (String fieldname : types.keySet()) {
			map.put( new ItemId( fieldname ), assessTypeId( types.get( fieldname ) ) );
		}
		return map;
	}

	public static Map<ItemId, FieldTypeId> assessTypeIds( Map<String, ?> row ) {
		Map<ItemId, FieldTypeId> map = new HashMap<ItemId, FieldTypeId>();
		Map<String, Class<?>> types = new Marshal().getTypes( row );
		for (String fieldname : types.keySet()) {
			map.put( new ItemId( fieldname ), assessTypeId( types.get( fieldname ) ) );
		}
		return map;
	}

}
