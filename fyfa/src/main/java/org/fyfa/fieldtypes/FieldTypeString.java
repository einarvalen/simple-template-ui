package org.fyfa.fieldtypes;

import org.fyfa.FieldType;
import org.fyfa.ids.FieldTypeId;

/** Parsing and rendering of String type data fields.
*
* @author EinarValen@gmail.com */
public class FieldTypeString implements FieldType<String> {
	public static FieldTypeId ID = new FieldTypeId( "String" );
	private final FieldTypeId id;

	public FieldTypeString() {
		this( ID );
	}

	public FieldTypeString( FieldTypeId id ) {
		this.id = id;
	}

	@Override
	public String format( String value ) {
		if (value == null) return "";
		return value.toString();
	}

	@Override
	public String parse( String value ) {
		return value;
	}

	@Override
	public String getHint() {
		return "ABCdefg.. 1234.. !#%^&*()_+=-<>>,.;:";
	}

	@Override
	public FieldTypeId getId() {
		return id;
	}

}
