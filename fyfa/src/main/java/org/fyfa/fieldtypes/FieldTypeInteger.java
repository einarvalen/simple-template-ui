package org.fyfa.fieldtypes;

import java.text.ParseException;

import org.fyfa.ids.FieldTypeId;


/** Parsing and rendering of integer data fields.
*
* @author EinarValen@gmail.com */
public class FieldTypeInteger extends FieldTypeNumeric<Integer> {
	public static FieldTypeId ID = new FieldTypeId( "Integer" );

	public FieldTypeInteger() {
		this( ID );
	}

	public FieldTypeInteger( FieldTypeId fieldTypeId ) {
		super( fieldTypeId );
	}

	@Override
	public Integer parse( java.lang.String value ) {
		if (value == null || value.trim().length() == 0) return null;
		try {
			Number number = newDecimalFormat().parse( value );
			return number.intValue();
		} catch (ParseException e) {
			throw new RuntimeException( "NUMBER_PARSE_PROBLEM", e );
		}
	}

	@Override
	public String getHint() {
		return this.format( Integer.valueOf( 1234 ) );
	}

}
