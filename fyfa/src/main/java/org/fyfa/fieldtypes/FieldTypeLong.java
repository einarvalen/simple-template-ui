package org.fyfa.fieldtypes;

import java.text.ParseException;

import org.fyfa.ids.FieldTypeId;


/** Parsing and rendering of type long data fields.
*
* @author EinarValen@gmail.com */
public class FieldTypeLong extends FieldTypeNumeric<Long> {
	public static FieldTypeId ID = new FieldTypeId( "Long" );

	public FieldTypeLong() {
		this( ID );
	}

	public FieldTypeLong( FieldTypeId fieldTypeId ) {
		super( fieldTypeId );
	}

	@Override
	public Long parse( java.lang.String value ) {
		if (value == null || value.trim().length() == 0) return null;
		try {
			Number number = newDecimalFormat().parse( value );
			return number.longValue();
		} catch (ParseException e) {
			throw new RuntimeException( "NUMBER_PARSE_PROBLEM", e );
		}
	}

	@Override
	public String getHint() {
		return this.format( Long.valueOf( 1234567 ) );
	}

}
