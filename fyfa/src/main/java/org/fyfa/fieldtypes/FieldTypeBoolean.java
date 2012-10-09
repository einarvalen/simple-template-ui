package org.fyfa.fieldtypes;

import org.fyfa.FieldType;
import org.fyfa.ids.FieldTypeId;

/** Parsing and rendering of boolean data fields.
 *
 * @author EinarValen@gmail.com */
public class FieldTypeBoolean implements FieldType<Boolean> {
	public static FieldTypeId ID = new FieldTypeId( "Boolean" );
	private final FieldTypeId id;

	public FieldTypeBoolean( FieldTypeId id ) {
		this.id = id;
	}

	public FieldTypeBoolean() {
		this( ID );
	}

	@Override
	public Boolean parse( java.lang.String value ) {
		if (value == null || value.trim().length() < 1) return null;
		return Boolean.valueOf( value );
	}

	@Override
	public String format( Boolean value ) {
		if (value == null) return "";
		return String.valueOf( value );
	}

	@Override
	public String getHint() {
		return "true/false";
	}

	@Override
	public FieldTypeId getId() {
		return this.id;
	}

}
