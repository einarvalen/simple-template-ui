package org.fyfa.fieldtypes;

import java.text.ParseException;

import org.fyfa.ids.FieldTypeId;


/** Parsing and rendering of float data fields.
*
* @author EinarValen@gmail.com */
public class FieldTypeFloat extends FieldTypeNumeric<Float> {
	public static FieldTypeId ID = new FieldTypeId( "Float" );

	public FieldTypeFloat() {
		this( ID );
	}

	public FieldTypeFloat( FieldTypeId fieldTypeId ) {
		super( fieldTypeId );
	}

	@Override
	public Float parse( java.lang.String value ) {
		if (value == null || value.trim().length() == 0) return null;
		try {
			Number number = newDecimalFormat().parse( value );
			return number.floatValue();
		} catch (ParseException e) {
			throw new RuntimeException( "NUMBER_PARSE_PROBLEM", e );
		}
	}

	@Override
	public String getHint() {
		return this.format( Float.valueOf( 1234.567F ) );
	}

}
