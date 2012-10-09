package org.fyfa.fieldtypes;

import java.text.ParseException;

import org.fyfa.ids.FieldTypeId;


/** Parsing and rendering of double data fields.
*
* @author EinarValen@gmail.com */
public class FieldTypeDouble extends FieldTypeNumeric<Double> {
	public static FieldTypeId ID = new FieldTypeId( "Double" );

	public FieldTypeDouble() {
		this( ID );
	}

	public FieldTypeDouble( FieldTypeId fieldTypeId ) {
		super( fieldTypeId );
	}

	@Override
	public Double parse( java.lang.String value ) {
		if (value == null || value.trim().length() == 0) return null;
		try {
			Number number = newDecimalFormat().parse( value );
			return number.doubleValue();
		} catch (ParseException e) {
			throw new RuntimeException( "NUMBER_PARSE_PROBLEM", e );
		}
	}

	@Override
	public String getHint() {
		return this.format( Double.valueOf( 1234.56789 ) );
	}

}
